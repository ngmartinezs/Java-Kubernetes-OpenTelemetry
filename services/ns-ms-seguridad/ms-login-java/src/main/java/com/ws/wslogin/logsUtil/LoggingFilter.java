package com.ws.wslogin.logsUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.wslogin.config.Config;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;

import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;


import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;    
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Type;

/**
 * Clase que contiene la logica para el manejo de los logs de la aplicacion o de los servicios web, 
 * intercepta las peticiones y respuestas
 * LoggingFilter
 * 
 * @version 1.0
 * @since 2023-07-31
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Autowired
    Config config;

    @Autowired
    OpenTelemetryUtil openTelemetryUtil;

    /**
     * Metodo que intercepta las peticiones y respuestas, este metodo es llamado por el framework de Springboot
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Obtenemos los datos de la peticion de los headers
        String reqId = request.getHeader(ConstanLogs.XREQID);
        String chanel = request.getHeader(ConstanLogs.XCHANEL);
        String idenSerialNum = request.getHeader(ConstanLogs.XIDENSERIALNUM);
        String govIssuIdenType = request.getHeader(ConstanLogs.XGOVISSUEIDENTYPE);
        String company = request.getHeader(ConstanLogs.XCOMPANY);
        String ipAdress = request.getHeader(ConstanLogs.XIPADRESS);

        LocalDateTime dateInput = null;
        LocalDateTime dateOutPut = null;
        String codeError = "";        
        long startTime = 0;
        long timeTaken = 0;
        String requestBody = "";
        String responseBody = "";
        String trace = "";
        Span span = null;

        //Creamos los objetos para el manejo de la peticion y respuesta
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        //Obtenemos el tiempo de inicio de la peticion
        startTime = System.currentTimeMillis();
        dateInput = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        //Obtenemos el objeto para el manejo de los logs mediante OpenTelemetry
        String nombreServicio = config.getComponentId()+ request.getRequestURI().replace("/", "-");
        String nombreServicioCorto = config.getComponentId()+"-"+ request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
        OpenTelemetrySdk openTelemetryInternal= openTelemetryUtil.getOpenTelemetry(nombreServicio);
        Tracer tracer = openTelemetryInternal.getTracer(nombreServicio, "1.0.1");
        
        try {
            
            //Creamos el span para el manejo de los logs
            span = tracer.spanBuilder(nombreServicio).startSpan();
            span.setAttribute("service.name",nombreServicio);
            
            //Se trasfiere el flujo al controllador 
            filterChain.doFilter(requestWrapper, responseWrapper);
            
            //Obtenemos el codigo de respuesta de la peticion
            codeError= Integer.valueOf(responseWrapper.getStatus()).toString();
            timeTaken = System.currentTimeMillis() - startTime;
            requestBody = readRequesBody(requestWrapper);
            responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
            dateOutPut = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        } catch (Exception pException) {
            timeTaken = System.currentTimeMillis() - startTime;
            responseBody = responseBody.isEmpty()
                    ? getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding())
                    : responseBody;
            trace = "{trace:" + ExceptionUtils.getStackTrace(pException) + "}";
            
            openTelemetryUtil.getLogRecordBuilder()
            .setAttribute(AttributeKey.stringKey(ConstanLogs.TRACER_ERROR),trace.toString());
            codeError= "500";
            dateOutPut = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        }
        finally{

            //Agregamos los atributos al objeto de logs
            Meter meter = openTelemetryUtil.getOpenTelemetry(nombreServicio).meterBuilder("instrumentation-library-name")
            .setInstrumentationVersion("1.0.0")
            .build();

            //Agregamos los atributos al objeto para generar metricas de tiempo de respuesta    
            LongCounter requestsCounter = meter.counterBuilder(getNombreServicio(ConstanLogs.NAME_METRIC_COUNTER_REQUEST+"-"+nombreServicioCorto))
            .setDescription(ConstanLogs.NAME_METRIC_COUNTER_REQUEST)
            .setUnit("1").build();

            Attributes attributes = null;
            if(codeError.equals("500"))
            {
                attributes = Attributes.of(AttributeKey.stringKey("Status"),"Error");
                requestsCounter.add(1, Attributes.of(AttributeKey.stringKey("Status"),"Error"));
            }
            else
            {
                attributes = Attributes.of(AttributeKey.stringKey("Status"),"Exitoso");
                requestsCounter.add(1, Attributes.of(AttributeKey.stringKey("Status"),"Exitoso"));
            }
            
            
            DoubleHistogram doubleHistogram = meter.histogramBuilder(getNombreServicio(ConstanLogs.NAME_METRIC_TIME_WS+"-"+nombreServicioCorto))
            .setDescription(nombreServicio)
            .setUnit("ms").build();
            doubleHistogram.record(timeTaken, attributes);
            
            Map<String, String> messageInt = new HashMap<>();
            Map<String, Map<String, String>> message = new HashMap<>();
            messageInt.put(ConstanLogs.NOMBRE_SERVICIO, nombreServicio);
            messageInt.put(ConstanLogs.REQID, reqId);
            messageInt.put(ConstanLogs.CANAL, chanel);
            messageInt.put(ConstanLogs.TIPO_ID_CLIENTE, govIssuIdenType);
            messageInt.put(ConstanLogs.NUM_ID_CLIENTE, idenSerialNum);
            messageInt.put(ConstanLogs.COMPANIA, company);
            messageInt.put(ConstanLogs.IP_ADRESS, ipAdress);
            messageInt.put(ConstanLogs.TIPO_METODO, request.getMethod());
            messageInt.put(ConstanLogs.URI,request.getRequestURI());        
            messageInt.put(ConstanLogs.TIME_INPUT,dateInput.toString());
            messageInt.put(ConstanLogs.TIME_OUTPUT,dateOutPut.toString());
            messageInt.put(ConstanLogs.TIME_EXECUTION, Long.valueOf(timeTaken).toString());
            messageInt.put(ConstanLogs.REQUEST_BODY,requestBody);
            messageInt.put(ConstanLogs.RESPONSE_BODY,responseBody);
            messageInt.put(ConstanLogs.CODE_ERROR,codeError);
            

            openTelemetryUtil.emitirLogs(messageInt);

            //Cerramos el span
            if(span != null)
            {
                span.end(Instant.now());
            }    

           openTelemetryUtil.closeLogRecordBuilder();
        }
        responseWrapper.copyBodyToResponse();
    }


    /**
     * Metodo que obtiene el contenido de la peticion
     * @param contentAsByteArray
     * @param characterEncoding
     * @return
     */
    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Metodo que obtiene el contenido de la peticion
     * @param req
     * @return
     * @throws BadRequestException
     */
    private String readRequesBody(ContentCachingRequestWrapper req) throws BadRequestException {

        String bodyParams = "";

        if (req.getMethod().equals("GET")) {
            bodyParams = this.getBodyParamForMethod(req);
        }

        if (req.getMethod().equals("POST")) {
            bodyParams = this.getBodyForMethod(req);
        }

        if (req.getMethod().equals("PUT")) {
            bodyParams = this.getBodyForMethod(req);
        }

        if (req.getMethod().equals("DELETE")) {
            bodyParams = this.getBodyParamForMethod(req);
        }

        return bodyParams;
    }

    /**
     * Metodo que obtiene el contenido de la peticion
     * @param req
     * @return
     */
    private String getBodyForMethod(ContentCachingRequestWrapper req) {
        try {
             Gson gson = new Gson();
            Type type = new TypeToken<Map>(){}.getType();
            
            Map<String, String> jsonRequest = new ObjectMapper().readValue(req.getContentAsByteArray(), Map.class);
            
            return gson.toJson(jsonRequest, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que obtiene el contenido de la peticion
     * @param req
     * @return
     */
    private String getBodyParamForMethod(ContentCachingRequestWrapper req) {
        Map<String, String> param = new HashMap<String, String>();
        String parameter;
        try {
            Enumeration<String> list = req.getParameterNames();
            Iterator<String> iterator = list.asIterator();

            while (iterator.hasNext()) {
                parameter = iterator.next();
                param.put(parameter, req.getParameter(parameter));
            }

            return param.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getNombreServicio(String nombreServicio) {
        return nombreServicio.length() > 62 ? nombreServicio.substring(0, 62) : nombreServicio;
    }

}

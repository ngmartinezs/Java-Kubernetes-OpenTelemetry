package com.ws.wslogin.logsUtil;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.logs.LogRecordBuilder;
import io.opentelemetry.api.logs.Severity;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

/**
 * OpenTelemetryUtil
 * 
 * Clase que permite la configuracion de OpenTelemetry
 * 
 * @version 1.0.0 20/08/2021
 * @since 1.0.0 20/08/2021
 * @see OpenTelemetryUtil
 * @autor @ngmartinezs
 */
@Component
public class OpenTelemetryUtil {

    private OpenTelemetrySdk openTelemetry;
    private LogRecordBuilder logRecordBuilder;

    @Autowired
    ConfigOpenTelemetry configOpenTelemetry;

    public OpenTelemetryUtil() {
    }
        

    public LogRecordBuilder getLogRecordBuilder() {

        if( logRecordBuilder == null)
        {
            if( openTelemetry != null)
                logRecordBuilder = openTelemetry.getLogsBridge().get("custom-log-appender").logRecordBuilder();
            else
                logRecordBuilder = getOpenTelemetry("").getLogsBridge().get("custom-log-appender").logRecordBuilder();
        }
        
        return logRecordBuilder;
    }

    public OpenTelemetrySdk getOpenTelemetry(String componentId) {
       
        if( openTelemetry == null )
        {
        
            Resource resource = Resource.getDefault()
                    .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, componentId)));

            SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                        .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder()
                        .setEndpoint(configOpenTelemetry.getEndpointOtlp())
                        .build()).build())
                        .setResource(resource)
                        .build();

            SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                        .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder()
                        .setEndpoint(configOpenTelemetry.getEndpointOtlp())
                        .build()).build())
                        .setResource(resource)
                        .build();
            
            SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
                        .addLogRecordProcessor(
                                BatchLogRecordProcessor.builder(OtlpGrpcLogRecordExporter.builder()
                                .setEndpoint(configOpenTelemetry.getEndpointOtlp())
                                .build()).build())
                        .setResource(resource)
                        .build();

            openTelemetry = OpenTelemetrySdk.builder()
                        .setTracerProvider(sdkTracerProvider)
                        .setMeterProvider(sdkMeterProvider)
                        .setLoggerProvider(sdkLoggerProvider)
                        .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance())).build();
        }            

        return openTelemetry;

    }

    public void emitirLogs(Map<String, String> paramLogs ) {
       
            ObjectMapper mapper = new ObjectMapper();

            /*paramLogs.keySet().forEach(key -> {
                getLogRecordBuilder()
                .setAttribute(AttributeKey.stringKey(key),paramLogs.get(key));
            });*/
            
            
            try{
                String log= mapper.writeValueAsString(paramLogs);
                getLogRecordBuilder()
                    .setSeverity(Severity.INFO)
                    .setBody(log)
                    .emit();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
    }

    public void closeLogRecordBuilder() {
        if (logRecordBuilder != null) {
            logRecordBuilder = null;
        }
    }

   
}

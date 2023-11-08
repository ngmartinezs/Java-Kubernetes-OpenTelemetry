package com.ws.wslogin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ws.wslogin.domain.dto.ResponseDTO;
import com.ws.wslogin.domain.dto.ResponseRegistrarDTO;
import com.ws.wslogin.domain.service.DatosPrincipalesService;
import com.ws.wslogin.domain.dto.HeadersDTO;
import com.ws.wslogin.domain.dto.RequestInactivarDTO;
import com.ws.wslogin.domain.dto.RequestRegistrarDatosDTO;
import com.ws.wslogin.domain.dto.ResponseConsultaDTO;
import com.ws.wslogin.exception.WsException;
import com.ws.wslogin.logsUtil.OpenTelemetryUtil;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/datos")
public class DatosPrincipalesController {

    @Autowired
    DatosPrincipalesService datosPrincipalesService;

    @Autowired
    OpenTelemetryUtil openTelemetryUtil;

    @Operation(summary = "Metodo que permite realizar la autenticacion de un usuari")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string"), description = "header x-RqUID"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string"), description = "header x-Channel"),
            @Parameter(in = ParameterIn.HEADER, name = "X-IdenSerialNum", schema = @Schema(type = "string"), description = "header X-IdenSerialNum"),
            @Parameter(in = ParameterIn.HEADER, name = "X-GovIssueIdenType", schema = @Schema(type = "string"), description = "header X-GovIssueIdenType"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Company", schema = @Schema(type = "string"), description = "header X-Company"),
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", schema = @Schema(type = "string"), description = "header Authorization"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "203", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion No Exitosa"),
            @ApiResponse(responseCode = "210", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Parametros Errados"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "No Autorizado"),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Error en el servicio")
    })
    @PostMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRegistrarDTO> value(@RequestHeader(value = "X-RqUID", required = true) String xRqUID,
                                                            @RequestHeader(value = "X-Channel", required = true) String xChannel,
                                                            @RequestHeader(value = "X-IdenSerialNum", required = true) String xIdenSerialNum,
                                                            @RequestHeader(value = "X-GovIssueIdenType", required = true) String xGovIssueIdenType,
                                                            @RequestHeader(value = "X-Company", required = true) String xCompany,
                                                            @RequestHeader(value = "X-IpAdress", required = true) String xIpAdress,
                                                            @RequestBody RequestRegistrarDatosDTO requestRegistrarDatosDTO) 
    {
        ResponseRegistrarDTO lResponseRegistrarDTO = null;
        HeadersDTO lHeadersDTO = null;
        Span span = null;
        try {
            
            String nombreServicio = "ns-ms-personas-ms-datosPrincipales-java-DatosPrincipalesController";
            OpenTelemetrySdk openTelemetryInternal= openTelemetryUtil.getOpenTelemetry(nombreServicio);
            Tracer tracer = openTelemetryInternal.getTracer(nombreServicio, "1.0.1");

             //Creamos el span para el manejo de los logs
            span = tracer.spanBuilder(nombreServicio).setParent(openTelemetryInternal.getSdkTracerProvider().get).startSpan();
            span.setAttribute("service.name",nombreServicio);

            System.out.println("\n Controller => requestRegistrarDatosDTO: " + requestRegistrarDatosDTO.toString());

            lHeadersDTO = HeadersDTO.builder().xRqUID(xRqUID)
                    .xChannel(xChannel)
                    .xIdenSerialNum(xIdenSerialNum)
                    .xGovIssueIdenType(xGovIssueIdenType)
                    .xCompany(xCompany)
                    .xIpAdress(xIpAdress)
                    .build();

            requestRegistrarDatosDTO.setHeaders(lHeadersDTO);
            lResponseRegistrarDTO = datosPrincipalesService.registrarDatos(requestRegistrarDatosDTO);

            if (lResponseRegistrarDTO.getResponse().getCodeResponse().equals("00")) {

                return ResponseEntity.ok(lResponseRegistrarDTO);
            } else {
                return ResponseEntity.status(203).body(lResponseRegistrarDTO);
            }

        } catch (Exception pException) {

            span.recordException(pException);

            ResponseDTO lResponseDTO = ResponseDTO.builder().codeResponse("10")
                    .descCode("Error en el proceso de registro de datos de cliente.")
                    .build();

            lResponseRegistrarDTO = ResponseRegistrarDTO.builder().response(lResponseDTO).build();

            return ResponseEntity.status(500).body(lResponseRegistrarDTO);
        }
        finally
        {
            span.end(Instant.now());
        }

    }

    @Operation(summary = "Metodo que permite realizar la consulta de datos de una persona")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string"), description = "header x-RqUID"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string"), description = "header x-Channel"),
            @Parameter(in = ParameterIn.HEADER, name = "X-IdenSerialNum", schema = @Schema(type = "string"), description = "header X-IdenSerialNum"),
            @Parameter(in = ParameterIn.HEADER, name = "X-GovIssueIdenType", schema = @Schema(type = "string"), description = "header X-GovIssueIdenType"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Company", schema = @Schema(type = "string"), description = "header X-Company"),
            @Parameter(in = ParameterIn.HEADER, name = "X-IpAdress", schema = @Schema(type = "string"), description = "header X-IpAdress"),
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", schema = @Schema(type = "string"), description = "header Authorization"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "203", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion No Exitosa"),
            @ApiResponse(responseCode = "210", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Parametros Errados"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "No Autorizado"),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Error en el servicio")
    })
    @GetMapping(value = "/consultar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseConsultaDTO> getEstado(
                                                            @RequestHeader(value = "X-RqUID", required = true) String xRqUID,
                                                            @RequestHeader(value = "X-Channel", required = true) String xChannel,
                                                            @RequestHeader(value = "X-IdenSerialNum", required = true) String xIdenSerialNum,
                                                            @RequestHeader(value = "X-GovIssueIdenType", required = true) String xGovIssueIdenType,
                                                            @RequestHeader(value = "X-Company", required = true) String xCompany,
                                                            @RequestHeader(value = "X-IpAdress", required = true) String xIpAdress,
                                                            @RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        HeadersDTO lHeadersDTO = null;
        ResponseConsultaDTO lResponseConsultaDTO = null;
        try {

            lHeadersDTO = HeadersDTO.builder().xRqUID(xRqUID)
                          .xChannel(xChannel)
                          .xIdenSerialNum(xIdenSerialNum)
                          .xGovIssueIdenType(xGovIssueIdenType)
                          .xCompany(xCompany)
                          .xIpAdress(xIpAdress)
                          .build();

            lResponseConsultaDTO = datosPrincipalesService.consultaDatos(lHeadersDTO, tipoDocumento, numeroDocumento);

            if (lResponseConsultaDTO.getResponse().getCodeResponse().equals("00")) {
                return ResponseEntity.ok(lResponseConsultaDTO);
            } else {
                return ResponseEntity.status(203).body(lResponseConsultaDTO);
            }
        } catch (Exception pException) {
            ResponseDTO lResponseDTO = ResponseDTO.builder().codeResponse("10")
                    .descCode("Error la consulta de datos de cliente.")
                    .build();

            lResponseConsultaDTO = ResponseConsultaDTO.builder().response(lResponseDTO).build();
            return ResponseEntity.status(500).body(lResponseConsultaDTO);
        }
    }

    @Operation(summary = "Metodo que permite realizar la validacion del Estado  de un usuario")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "X-RqUID", schema = @Schema(type = "string"), description = "header x-RqUID"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Channel", schema = @Schema(type = "string"), description = "header x-Channel"),
            @Parameter(in = ParameterIn.HEADER, name = "X-IdenSerialNum", schema = @Schema(type = "string"), description = "header X-IdenSerialNum"),
            @Parameter(in = ParameterIn.HEADER, name = "X-GovIssueIdenType", schema = @Schema(type = "string"), description = "header X-GovIssueIdenType"),
            @Parameter(in = ParameterIn.HEADER, name = "X-Company", schema = @Schema(type = "string"), description = "header X-Company"),
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", schema = @Schema(type = "string"), description = "header Authorization"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "203", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion No Exitosa"),
            @ApiResponse(responseCode = "210", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class), mediaType = "application/json")
            }, description = "Autenticacion Exitosa"),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Parametros Errados"),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "No Autorizado"),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = WsException.class), mediaType = "application/json")
            }, description = "Error en el servicio")
    })
    @PutMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> inactivar(  @RequestHeader(value = "X-RqUID", required = true) String xRqUID,
                                                   @RequestHeader(value = "X-Channel", required = true) String xChannel,
                                                   @RequestHeader(value = "X-IdenSerialNum", required = true) String xIdenSerialNum,
                                                   @RequestHeader(value = "X-GovIssueIdenType", required = true) String xGovIssueIdenType,
                                                   @RequestHeader(value = "X-Company", required = true) String xCompany,
                                                   @RequestHeader(value = "X-IpAdress", required = true) String xIpAdress,
                                                   @RequestBody RequestInactivarDTO requestInactivarDTO) {
        ResponseDTO lResponseDTO = null;
        HeadersDTO lHeadersDTO = null;
        try {
            lHeadersDTO = HeadersDTO.builder().xRqUID(xRqUID)
                    .xChannel(xChannel)
                    .xIdenSerialNum(xIdenSerialNum)
                    .xGovIssueIdenType(xGovIssueIdenType)
                    .xCompany(xCompany)
                    .xIpAdress(xIpAdress)
                    .build();
            
            lResponseDTO = datosPrincipalesService.inactivarDatos(lHeadersDTO, requestInactivarDTO);

            if (lResponseDTO.getCodeResponse().equals("00")) {
                return ResponseEntity.ok(lResponseDTO);
            } else {
                return ResponseEntity.status(203).body(lResponseDTO);
            }
        } catch (Exception pException) {
            lResponseDTO = ResponseDTO.builder().codeResponse("10")
                    .descCode("Error en el proceso de inactivacion de datos de cliente.")
                    .build();
            return ResponseEntity.status(500).body(lResponseDTO);
        }
    }

}

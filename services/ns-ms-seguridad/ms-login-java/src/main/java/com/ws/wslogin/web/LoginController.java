package com.ws.wslogin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ws.wslogin.domain.dto.ResponseDTO;
import com.ws.wslogin.domain.service.LoginService;
import com.ws.wslogin.domain.dto.RequestDTO;
import com.ws.wslogin.exception.WsException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value="/authentication")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "Metodo que permite realizar la autenticacion de un usuari")
    @Parameters({
        @Parameter(in = ParameterIn.HEADER, name="X-RqUID", schema = @Schema(type="string"), description = "header x-RqUID" ),
        @Parameter(in = ParameterIn.HEADER, name="X-Channel", schema = @Schema(type="string"), description = "header x-Channel" ),
        @Parameter(in = ParameterIn.HEADER, name="X-IdenSerialNum", schema = @Schema(type="string"), description = "header X-IdenSerialNum" ),
        @Parameter(in = ParameterIn.HEADER, name="X-GovIssueIdenType", schema = @Schema(type="string"), description = "header X-GovIssueIdenType" ),
        @Parameter(in = ParameterIn.HEADER, name="X-Company", schema = @Schema(type="string"), description = "header X-Company" ),
        @Parameter(in = ParameterIn.HEADER, name="Authorization", schema = @Schema(type="string"), description = "header Authorization" ),
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion Exitosa"),
        @ApiResponse(responseCode = "203", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion No Exitosa"),
        @ApiResponse(responseCode = "210", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion Exitosa"),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "Parametros Errados"),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "No Autorizado"),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "Error en el servicio")
    })
    @PostMapping(value = "/valide",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> value(@RequestBody RequestDTO requestDTO )
    {
        ResponseDTO lResponseDTO = null;
        try{
            lResponseDTO = loginService.validateUser(requestDTO.getTipoDocumento(), requestDTO.getNumeroDocumento(), requestDTO.getClave());

            if(lResponseDTO.getCodeResponse().equals("00"))
            {
                //LogMng.writeLog(logDTO);
                return ResponseEntity.ok(lResponseDTO);
            }
            else
            {
                //LogMng.writeLog(logDTO);
                return ResponseEntity.status(203).body(lResponseDTO);
            }
                
        }
        catch(Exception pException)
        {
            
            lResponseDTO = ResponseDTO.builder().codeResponse("10")
                                        .descCode("Error en el proceso de  autenticacion.")
                                        .build();
            return ResponseEntity.status(500).body(lResponseDTO);
        }
        
    }

    @Operation(summary = "Metodo que permite realizar la validacion del Estado  de un usuario")
    @Parameters({
        @Parameter(in = ParameterIn.HEADER, name="X-RqUID", schema = @Schema(type="string"), description = "header x-RqUID" ),
        @Parameter(in = ParameterIn.HEADER, name="X-Channel", schema = @Schema(type="string"), description = "header x-Channel" ),
        @Parameter(in = ParameterIn.HEADER, name="X-IdenSerialNum", schema = @Schema(type="string"), description = "header X-IdenSerialNum" ),
        @Parameter(in = ParameterIn.HEADER, name="X-GovIssueIdenType", schema = @Schema(type="string"), description = "header X-GovIssueIdenType" ),
        @Parameter(in = ParameterIn.HEADER, name="X-Company", schema = @Schema(type="string"), description = "header X-Company" ),
        @Parameter(in = ParameterIn.HEADER, name="Authorization", schema = @Schema(type="string"), description = "header Authorization" ),
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion Exitosa"),
        @ApiResponse(responseCode = "203", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion No Exitosa"),
        @ApiResponse(responseCode = "210", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Autenticacion Exitosa"),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "Parametros Errados"),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "No Autorizado"),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema(implementation = WsException.class),mediaType = "application/json")
        }, description = "Error en el servicio")
    })
    @GetMapping(value = "/estado", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getEstado(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento )
    {
         ResponseDTO lResponseDTO = null;
        try{
            lResponseDTO = loginService.getEstado(tipoDocumento,numeroDocumento);

            if(lResponseDTO.getCodeResponse().equals("00"))
            {
                return ResponseEntity.ok(lResponseDTO);
            }
            else
            {
                return ResponseEntity.status(203).body(lResponseDTO);
            }
        }
        catch(Exception pException)
        {
            lResponseDTO = ResponseDTO.builder().codeResponse("10")
                                        .descCode("Error en el proceso de  autenticacion.")
                                        .build();
            return ResponseEntity.status(500).body(lResponseDTO);
        }
    }
    
}

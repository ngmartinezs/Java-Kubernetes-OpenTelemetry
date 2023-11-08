package com.ws.wsLogsElastic.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws.wsLogsElastic.domain.dto.RequestDTO;
import com.ws.wsLogsElastic.domain.dto.ResponseDTO;
import com.ws.wsLogsElastic.domain.service.LoginService;
import com.ws.wsLogsElastic.exception.WsException;

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
@RequestMapping(value="/mslogs")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Operation(summary = "Metodo que permite realizar la autenticacion de un usuari")
    @Parameters({
        @Parameter(in = ParameterIn.HEADER, name="X-RqUID", schema = @Schema(type="string"), description = "header x-RqUID" ),
        @Parameter(in = ParameterIn.HEADER, name="X-Channel", schema = @Schema(type="string"), description = "header x-Channel" ),
        @Parameter(in = ParameterIn.HEADER, name="Authorization", schema = @Schema(type="string"), description = "header Authorization" ),
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema= @Schema(implementation = ResponseDTO.class),mediaType = "application/json")
        }, description = "Registro Exitosa"),
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
    @PostMapping(value = "/registrar",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> value(@RequestBody RequestDTO requestDTO )
    {
        ResponseDTO lResponseDTO = null;
        try
        {
            lResponseDTO = loginService.createLog(requestDTO);

            if(lResponseDTO.getCodeResponse().equals("0"))
            {
                //LogMng.writeLog(logDTO);
                return ResponseEntity.ok(lResponseDTO);
            }
            else
            {
                //LogMng.writeLog(logDTO);
                return ResponseEntity.status(500).body(lResponseDTO);
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

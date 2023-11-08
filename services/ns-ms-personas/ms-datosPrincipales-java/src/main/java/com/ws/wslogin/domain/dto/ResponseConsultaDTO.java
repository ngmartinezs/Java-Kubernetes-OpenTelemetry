package com.ws.wslogin.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseConsultaDTO {
    
    private ResponseDTO response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DatosPersonaDTO datosPersona;   

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DatosContactoDTO datosContacto;
}

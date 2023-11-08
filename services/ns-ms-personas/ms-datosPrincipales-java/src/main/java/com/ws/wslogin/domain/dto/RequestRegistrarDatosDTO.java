package com.ws.wslogin.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestRegistrarDatosDTO {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private HeadersDTO headers;


    private DatosPersonaDTO datosPersona;

    private DatosContactoDTO datosContacto;
}

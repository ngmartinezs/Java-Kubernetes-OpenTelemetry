package com.ws.wslogin.domain.dto;

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
public class ResponseRegistrarDTO {

    private ResponseDTO response;

    private String tipoDocumento;
    private String numeroDocumento;
    private String idGenerado;
    
}

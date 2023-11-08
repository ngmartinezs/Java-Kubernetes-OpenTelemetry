package com.ws.wslogin.domain.dto;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String tipoDocumento;
    private String numeroDocumento;
    private String clave;
}

package com.ws.wslogin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RequestInactivarDTO
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestInactivarDTO {
    
    private String tipoDocumento;
    private String numeroDocumento;
    private String motivo;
}

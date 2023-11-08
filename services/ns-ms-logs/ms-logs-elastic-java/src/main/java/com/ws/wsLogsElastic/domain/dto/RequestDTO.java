package com.ws.wsLogsElastic.domain.dto;

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
    private String reqId;
    private String tipoIdCliente;
    private String numeroIdCliente;
    private String requestBody;
    private String responseBody;
}

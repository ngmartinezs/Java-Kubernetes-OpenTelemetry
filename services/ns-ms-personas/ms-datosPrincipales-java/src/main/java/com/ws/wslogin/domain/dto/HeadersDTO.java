package com.ws.wslogin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * HeadersDTO
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeadersDTO {
    
    private String xRqUID;
    private String xChannel;
    private String xIdenSerialNum;
    private String xGovIssueIdenType;
    private String xCompany;
    private String xIpAdress;
}

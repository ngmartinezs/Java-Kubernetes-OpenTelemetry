package com.ws.wsLogsElastic.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ResponseDTO {
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codeResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String descCode;

}

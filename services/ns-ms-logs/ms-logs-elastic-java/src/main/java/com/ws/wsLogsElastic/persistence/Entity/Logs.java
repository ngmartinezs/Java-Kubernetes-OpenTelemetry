package com.ws.wsLogsElastic.persistence.Entity;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Document(indexName = "logs")
public class Logs {
    @JsonProperty("@timestamp")
    private String timestamp;

    private String reqId;
    
    private MessageBean message;
}

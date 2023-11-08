package com.ws.wsLogsElastic.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean implements java.io.Serializable {
    
    private String typeDocPerson;
    private String numeDocIdPerson;
    private String request;
    private String response;
    private String codeError;

}

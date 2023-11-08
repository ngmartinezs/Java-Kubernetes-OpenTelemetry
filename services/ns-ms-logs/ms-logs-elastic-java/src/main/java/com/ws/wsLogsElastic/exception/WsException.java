package com.ws.wsLogsElastic.exception;

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
public class WsException {
    private String codeError;
    private String desError;
    private String stackTrace;
}

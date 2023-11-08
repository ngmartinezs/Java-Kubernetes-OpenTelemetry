package com.ws.wslogin.logsUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.logs.LogRecordBuilder;


@Component
public class CustonExceptionAPI extends Exception {
    
    @Autowired
    OpenTelemetryUtil openTelemetryUtil;

    private static final long serialVersionUID = 1L;

    public CustonExceptionAPI () {
        super();
    }

    public CustonExceptionAPI (String message) {
        super(message);
    }

    public CustonExceptionAPI (String message, Exception exception) {
        super(message,exception);
    }

    public CustonExceptionAPI getCustonExceptionAPI(String message, Exception exception) {
        //super(message,exception);
        try {
            
            if( exception instanceof CustonExceptionAPI == false)
            {
                LogRecordBuilder logRecordBuilder = openTelemetryUtil.getLogRecordBuilder();
                logRecordBuilder.setAttribute(AttributeKey.stringKey(ConstanLogs.TRACER_ERROR), "{trace:" + ExceptionUtils.getStackTrace(this) + "}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CustonExceptionAPI ( message, exception);
    }
}

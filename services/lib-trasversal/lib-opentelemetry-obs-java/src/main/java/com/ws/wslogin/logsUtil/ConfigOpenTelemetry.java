package com.ws.wslogin.logsUtil;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

@lombok.Getter
@lombok.Setter
@Component
public class ConfigOpenTelemetry {


    public ConfigOpenTelemetry()
    {
        System.out.println("ConfigOpenTelemetry Constructor");
    }

    @Value("${otel.exporter.otlp.endpoint}")
    public String endpointOtlp;

    @Value("${otel.exporter.otlp.certificate}")
    public String certificateOtlp;

    public String toString()
    {
        return "endpointOtlp: " + endpointOtlp + " certificateOtlp: " + certificateOtlp;
    }
    
}

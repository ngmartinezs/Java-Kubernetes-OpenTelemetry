package com.ws.wslogin.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@lombok.Getter
@lombok.NoArgsConstructor
@lombok.Setter
@Configuration
public class Config {

    @Value("${name.component}")
    private String componentId;

    @Value("${otel.exporter.otlp.endpoint}")
    private String endpointOtlp;
}

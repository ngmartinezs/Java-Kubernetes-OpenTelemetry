package com.ws.wsLogsElastic.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@lombok.Getter
@lombok.NoArgsConstructor
@lombok.Setter
@Configuration
public class Config {

    @Value("${name.component}")
    private String componentId;

    
}

package com.ws.wslogin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                    .components(new Components())
                    .info(new Info().title("Servicio wsLogin")
                    .description("Servcio que permite simular la autenticacion de un usuario")
                    .contact(new Contact().name("ngmartinezs@gmail.com").url("ngmartinezs@gmail.com"))
                    );
    }
}

package com.example.id_validate.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI idValidateAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("PAN & Aadhaar Validator API")
                .version("1.0.0")
                .description("API for validating PAN and Aadhaar numbers with MongoDB persistence."));
    }
}

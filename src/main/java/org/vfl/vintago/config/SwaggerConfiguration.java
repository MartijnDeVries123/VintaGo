package org.vfl.vintago.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI().info(new Info().title("Vintage for life API").version("1.0"));
    }
}
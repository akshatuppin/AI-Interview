package com.interview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    

    @Bean
    public OpenAPI customerAPI(){

        return new OpenAPI()
                .info(
                    new Info()
                        .title("AI Interview Platform API")
                        .version("1.0")
                        .description("Backend APIs for AI Mock Interview Platform")
                        .contact(
                            new Contact()
                                .name("Akshat Uppin")
                                .email("uppinakshat26@gmail.com")
                        )      
                )
                
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))

                .components(
                    new Components()
                        .addSecuritySchemes(
                            "Bearer Authentication",
                                new SecurityScheme()
                                    .name("Bearer Authentication")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                            )
                );

    }
}

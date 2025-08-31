package com.aamirtechie.URLShortener.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI urlShortenerOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("URL Shortener API")
                        .description("API documentation for URL Shortener Service")
                        .version("v1.0.0").contact(new Contact().name("Aamir Techie")
                                .email("aamir@example.com").url("https://aamirtechie.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("urlshortener-public").packagesToScan("com.aamirtechie.URLShortener.controller").build();
    }
}

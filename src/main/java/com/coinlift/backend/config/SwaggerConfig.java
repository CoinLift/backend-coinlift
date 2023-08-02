package com.coinlift.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("CoinLift Backend API")
                        .description("Backend API for CoinLift application.")
                        .version("1.0")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Vladyslav")
                                .url("https://github.com/vladsw764")
                                .email("vladsw764@icloud.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT License")
//                                .url("https://opensource.org/licenses/MIT")
                                .url("https://github.com/CoinLift/backend-coinlift/blob/main/LICENSE")));
    }
}
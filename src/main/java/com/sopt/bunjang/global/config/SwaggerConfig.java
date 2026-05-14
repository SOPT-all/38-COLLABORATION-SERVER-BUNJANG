package com.sopt.bunjang.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bunjangOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Bunjang API")
                        .description("SOPT 합동세미나")
                        .version("v1.0.0"));
    }
}

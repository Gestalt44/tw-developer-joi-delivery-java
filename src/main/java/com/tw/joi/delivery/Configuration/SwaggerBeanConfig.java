package com.tw.joi.delivery.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerBeanConfig {

    @Bean
    public OpenAPI joiDeliveryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JOI Delivery API")
                        .description("Hyperlocal delivery platform documentation")
                        .version("v1.0"));
    }
}





package cl.ecomarket.user.config;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Ecomarket User Service ")
                        .version("1.0.0")
                        .description("API documentación para ecomarket service user"));
    }
}

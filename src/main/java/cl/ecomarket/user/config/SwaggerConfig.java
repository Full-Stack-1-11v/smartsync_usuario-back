package cl.ecomarket.user.config;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para la documentación de la API.
 * Define la información principal que se muestra en la interfaz Swagger UI.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Configura la instancia principal de OpenAPI para la documentación.
     * @return OpenAPI configurado con título, versión y descripción.
     */
    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Ecomarket User Service ")
                        .version("1.0.0")
                        .description("API documentación para ecomarket service user"));
    }
}

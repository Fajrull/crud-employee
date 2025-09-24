package Crud_Tes.fajrul.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CRUD API",
                version = "1.0.0",
                contact = @Contact(
                        name = "fajrulCRUD",
                        url = "https://crud-tes.com"
                )
        )
)
public class OpenApiConfiguration {
}

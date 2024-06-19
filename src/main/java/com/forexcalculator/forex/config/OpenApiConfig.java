package com.forexcalculator.forex.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
@OpenAPIDefinition(

        info = @Info(
                contact = @Contact(
                        name = "Forex Calculator",
                        email = "ForexCalculator.com",
                        url ="https//ForexCalculator.com"
                ),
                description = "API for Forex Calculator platform",
                title = "Forex Calculator API",
                version = "1.0.0",
                license = @License(
                        name = "license name",
                        url = "https://someurl.com"
                )

        ),
        servers ={
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8085"
                ),

        },
        security= {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

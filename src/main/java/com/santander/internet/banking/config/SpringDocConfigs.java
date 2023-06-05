package com.santander.internet.banking.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Internet banking", version = "1.0",
description = "REST API para treino",
contact = @Contact(name = "Daniel M Oliveira")),
security = {@SecurityRequirement(name = "bearerToken")}
)
@SecuritySchemes({
@SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, 
                scheme = "bearer", bearerFormat = "JWT")
})
public class SpringDocConfigs {
	// configuracao funcionando para spring boot v 3.1.0  
	// http://localhost:8080/swagger-ui/index.html
}
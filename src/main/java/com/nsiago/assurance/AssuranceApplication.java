package com.nsiago.assurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "NSIAGO'ASSUR API",
        version = "1.0",
        description = "API pour la gestion des assurances automobiles NSIAGO'ASSUR"
    )
)
public class AssuranceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssuranceApplication.class, args);
    }
} 
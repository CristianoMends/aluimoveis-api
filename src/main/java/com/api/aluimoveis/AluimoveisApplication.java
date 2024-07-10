package com.api.aluimoveis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class AluimoveisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluimoveisApplication.class, args);
	}

}

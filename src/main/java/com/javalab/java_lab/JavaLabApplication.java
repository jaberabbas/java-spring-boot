package com.javalab.java_lab;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
	title = "Employee | Department Java Spring Boot Lab",
	description = "A test project to get used to Spring Boot",
	version = "1.0.0"), tags = {@Tag(name = "Employee Api contoller", description = "This is the global CRUD controller for the Employee model")})
public class JavaLabApplication {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(JavaLabApplication.class);

	public static void main(String[] args) {
		log.info("Starting Java Lab Application");
		SpringApplication.run(JavaLabApplication.class, args);
		log.info("Java Lab Application Started");
	}

	// If we're reading this, this is the newest version

}

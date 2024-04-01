package com.hritvik.CarbonCellAssignment;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@SecurityScheme(in = SecuritySchemeIn.HEADER, name = "BearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class CarbonCellAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarbonCellAssignmentApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

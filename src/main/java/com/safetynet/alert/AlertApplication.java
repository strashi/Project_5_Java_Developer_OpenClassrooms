package com.safetynet.alert;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "SafeAlert Projet", version = "2.5", description = "All services"))
@SpringBootApplication
public class AlertApplication{
		
	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	HttpTraceRepository httpTraceRepository() {
	  return new InMemoryHttpTraceRepository();
	}
		
}

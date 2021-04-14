package com.shohidulhaque.api_rest_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRestServiceApplication {
	static Logger Logger = LoggerFactory.getLogger(ApiRestServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ApiRestServiceApplication.class, args);
	}

}

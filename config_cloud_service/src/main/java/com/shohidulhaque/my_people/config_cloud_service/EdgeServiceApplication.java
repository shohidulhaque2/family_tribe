package com.shohidulhaque.my_people.config_cloud_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EdgeServiceApplication {

	static Logger Logger = LoggerFactory.getLogger(EdgeServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}
}

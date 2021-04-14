package com.shohidulhaque.my_people.common_model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ApiRestServerApplication {

	static Logger Logger = LoggerFactory.getLogger(ApiRestServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiRestServerApplication.class, args);
	}

	@Component
	private static class PrintArguments implements CommandLineRunner  {

		@Override
		public void run(String... args) throws Exception {
			Logger
					.info("{} is running with arguments {}", SpringApplication.class.getName(), args);
		}
	}

}

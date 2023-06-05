package com.santander.internet.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan(basePackages = "com.santander.internet.banking.config")

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

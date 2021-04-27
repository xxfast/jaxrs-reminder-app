package com.byond.reminderslistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class RemindersListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemindersListServiceApplication.class, args);
	}
}

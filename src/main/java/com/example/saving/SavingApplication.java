package com.example.saving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SavingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingApplication.class, args);
	}

}

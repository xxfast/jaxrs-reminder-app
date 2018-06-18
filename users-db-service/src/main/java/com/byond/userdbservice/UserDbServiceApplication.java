package com.byond.userdbservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.byond.userdbservice.repository.UsersRepository;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=UsersRepository.class)
public class UserDbServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(UserDbServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(UserDbServiceApplication.class, args);
		
		log.info("Application \"User Database Service\" running...");
	}
}

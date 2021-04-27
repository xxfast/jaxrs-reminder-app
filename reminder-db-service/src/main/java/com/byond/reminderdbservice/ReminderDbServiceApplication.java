package com.byond.reminderdbservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.byond.reminderdbservice.repository.RemindersRepository;


import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=RemindersRepository.class)
public class ReminderDbServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(ReminderDbServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReminderDbServiceApplication.class, args);

		log.info("Application \"Reminder Database Service\" running...");

	}


}

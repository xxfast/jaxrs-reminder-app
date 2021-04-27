package com.byond;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class ReminderOgmMongoServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(ReminderOgmMongoServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ReminderOgmMongoServiceApplication.class, args);
		log.info("Reminders Service through OGM/JPA MongoDB");
	}
}

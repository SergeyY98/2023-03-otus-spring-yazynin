package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TravelBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelBotApplication.class, args);
	}
}

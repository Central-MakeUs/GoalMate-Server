package com.goalmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GoalmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalmateApplication.class, args);
	}

}

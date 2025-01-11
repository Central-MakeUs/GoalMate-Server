package com.goalmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing // for BaseEntity
@EnableTransactionManagement // transaction
@SpringBootApplication
public class GoalmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalmateApplication.class, args);
	}

}

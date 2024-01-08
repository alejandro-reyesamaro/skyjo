package com.skyjo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={
	"com.skyjo.api",
	"com.skyjo.api.controllers.strategies",
	"com.skyjo.core",
	"com.skyjo.application",
	"com.skyjo.infrastructure",
	"com.skyjo.infrastructure.repository",
})
@EnableTransactionManagement
@EntityScan("com.skyjo.infrastructure.repository")
@EnableJpaRepositories("com.skyjo.infrastructure.repository")
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

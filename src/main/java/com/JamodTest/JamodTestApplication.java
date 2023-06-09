package com.JamodTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JamodTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JamodTestApplication.class, args);

	}

}

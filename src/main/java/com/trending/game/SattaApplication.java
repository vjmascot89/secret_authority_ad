package com.trending.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SattaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SattaApplication.class, args);
	}
}

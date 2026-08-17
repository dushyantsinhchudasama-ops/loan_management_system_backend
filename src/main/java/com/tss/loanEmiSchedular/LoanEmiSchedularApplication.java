package com.tss.loanEmiSchedular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanEmiSchedularApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanEmiSchedularApplication.class, args);
	}

}

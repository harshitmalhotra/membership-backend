package com.firstclub.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FirstclubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstclubApplication.class, args);
	}

}

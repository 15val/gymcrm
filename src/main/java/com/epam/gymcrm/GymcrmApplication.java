package com.epam.gymcrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
@EnableJms
public class GymcrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymcrmApplication.class, args);
	}

}

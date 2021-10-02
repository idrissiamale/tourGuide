package com.mtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mtracker")
public class MicroserviceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceTrackerApplication.class, args);
	}

}

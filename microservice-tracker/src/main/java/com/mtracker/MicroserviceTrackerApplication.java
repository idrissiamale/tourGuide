package com.mtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mtracker")
@EnableDiscoveryClient
public class MicroserviceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceTrackerApplication.class, args);
	}

}

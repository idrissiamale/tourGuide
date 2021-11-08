package com.mattraction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mattraction")
@EnableDiscoveryClient
public class MicroserviceAttractionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceAttractionApplication.class, args);
	}
}

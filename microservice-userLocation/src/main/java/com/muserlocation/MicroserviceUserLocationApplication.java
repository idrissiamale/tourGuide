package com.muserlocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.muserlocation")
@EnableDiscoveryClient
public class MicroserviceUserLocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUserLocationApplication.class, args);
	}

}

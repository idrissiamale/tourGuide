package com.muserlocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.muserlocation")
public class MicroserviceUserLocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUserLocationApplication.class, args);
	}

}

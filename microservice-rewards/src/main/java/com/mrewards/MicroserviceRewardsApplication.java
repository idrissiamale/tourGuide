package com.mrewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mrewards")
public class MicroserviceRewardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRewardsApplication.class, args);
	}

}

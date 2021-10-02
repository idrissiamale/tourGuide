package com.mtrippricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceTripPricerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceTripPricerApplication.class, args);
	}

}

package com.lavdrim.eureka_dummy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaDummyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDummyServiceApplication.class, args);
	}

}

package com.john.servicemonitorhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class ServiceMonitorhystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceMonitorhystrixApplication.class, args);
	}
}

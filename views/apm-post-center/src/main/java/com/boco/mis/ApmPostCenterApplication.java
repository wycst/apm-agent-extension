package com.boco.mis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.boco.mis"})
public class ApmPostCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApmPostCenterApplication.class, args);
	}
}

package com.bansal.demorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.bansal.*.*" })
public class DemorestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemorestApplication.class, args);
	}

}

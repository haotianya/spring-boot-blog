package com.haotian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class BlogPrototypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogPrototypeApplication.class, args);
	}

}


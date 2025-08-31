package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot application entry point.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "currentAuditorProvider")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

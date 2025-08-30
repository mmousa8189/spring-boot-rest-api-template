package com.example.config;

import com.example.model.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

/**
 * Configuration for initializing sample data.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;

    /**
     * Initialize sample data for development environment.
     *
     * @return CommandLineRunner
     */
    @Bean
    @Profile("dev")
    public CommandLineRunner initData() {
        return args -> {
            log.info("Initializing sample data with {} arguments...", args.length);
            
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@example.com")
                        .password("password123")
                        .build();
                
                userRepository.save(admin);
                
                User john = User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .password("password123")
                        .build();
                
                userRepository.save(john);
                
                log.info("Sample data initialized successfully!");
            } else {
                log.info("Sample data already exists. Skipping initialization.");
            }
        };
    }
}

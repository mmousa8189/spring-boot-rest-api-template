package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

@Configuration

public class AuditConfig {

    private static final String ANONYMOUS_USER = "ANONYMOUSUSER";
    private static final String SYSTEM_DEFAULT = "SYSTEM";

    @Bean
    public AuditorAware<String> currentAuditorProvider() {

        return () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of(SYSTEM_DEFAULT);
            }
            if (!authentication.getName().equals(ANONYMOUS_USER)) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    return Optional.of(((UserDetails) principal).getUsername());
                }
                return Optional.of(authentication.getName());
            } else {
                if (authentication.getName().equals(ANONYMOUS_USER)) {
                    return Optional.of(ANONYMOUS_USER);
                }
                return Optional.of(SYSTEM_DEFAULT);
            }
        };
    }

}

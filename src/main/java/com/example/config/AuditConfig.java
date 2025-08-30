package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.attribute.UserPrincipal;
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
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                return Optional.of(String.valueOf(""));
            } else {
                if (authentication.getName().equals(ANONYMOUS_USER)) {
                    return Optional.of(ANONYMOUS_USER);
                }
                return Optional.of(SYSTEM_DEFAULT);
            }
        };
    }

}

package com.example.service.impl;

import com.example.model.entity.ApiAuditLog;
import com.example.repository.nosql.ApiAuditLogRepository;
import com.example.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final ApiAuditLogRepository auditLogRepository;

    @Override
    public ApiAuditLog saveAuditLog(String action, String clientIp, String requestPayload, 
                                  String responsePayload, String status) {
        return saveDetailedAuditLog(action, clientIp, requestPayload, responsePayload, null, null, status);
    }

    @Override
    public ApiAuditLog saveDetailedAuditLog(String action, String clientIp, String requestPayload, 
                                         String responsePayload, String httpMethod, 
                                         String endpoint, String status) {
        
        String username = getCurrentUsername();
        
        ApiAuditLog auditLog = ApiAuditLog.builder()
                .action(action)
                .clientIp(clientIp)
                .requestPayload(requestPayload)
                .responsePayload(responsePayload)
                .httpMethod(httpMethod)
                .endpoint(endpoint)
                .status(status)
                .createdBy(username)
                .createdAt(LocalDateTime.now())
                .build();
        
        return auditLogRepository.save(auditLog);
    }
    
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "anonymous";
        }
        return authentication.getName();
    }
}

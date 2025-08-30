package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "api_audit_logs")
public class ApiAuditLog {
    
    @Id
    private String id;
    
    private String action;
    
    private String requestPayload;
    
    private String responsePayload;
    
    private String httpMethod;
    
    private String endpoint;
    
    private String clientIp;
    
    private String status;
    
    private String createdBy;
    
    private LocalDateTime createdAt;
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

package com.example.repository.nosql;

import com.example.model.entity.ApiAuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApiAuditLogRepository extends MongoRepository<ApiAuditLog, String> {
    
    List<ApiAuditLog> findByAction(String action);
    
    List<ApiAuditLog> findByEndpoint(String endpoint);
    
    List<ApiAuditLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    List<ApiAuditLog> findByHttpMethod(String httpMethod);
    
    List<ApiAuditLog> findByStatus(String status);
}

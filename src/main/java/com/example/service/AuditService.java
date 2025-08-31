package com.example.service;

import com.example.model.entity.ApiAuditLog;

public interface AuditService {
    
    /**
     * Save an audit log entry
     * 
     * @param action The action being performed
     * @param clientIp The client IP address
     * @param requestPayload The request payload
     * @param responsePayload The response payload
     * @param status The status of the operation (SUCCESS/FAILED)
     * @return The saved ApiAuditLog
     */
    ApiAuditLog saveAuditLog(String action, String clientIp, String requestPayload, 
                            String responsePayload, String status);
    
    /**
     * Save a detailed audit log entry
     * 
     * @param action The action being performed
     * @param clientIp The client IP address
     * @param requestPayload The request payload
     * @param responsePayload The response payload
     * @param httpMethod The HTTP method (GET, POST, PUT, DELETE, etc.)
     * @param endpoint The API endpoint
     * @param status The status of the operation (SUCCESS/FAILED)
     * @return The saved ApiAuditLog
     */
    ApiAuditLog saveDetailedAuditLog(String action, String clientIp, String requestPayload, 
                                   String responsePayload, String httpMethod, 
                                   String endpoint, String status);
}

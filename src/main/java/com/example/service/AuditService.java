package com.example.service;

public interface AuditService {

    public void log(String username, String action, String entityName, String entityId,
    String oldValue, String newValue, String sourceIp);
    public void log(String username, String action, String entityName, String entityId,
    String oldValue, String newValue);

}

package com.example.controller;

import com.example.model.entity.ApiAuditLog;
import com.example.repository.nosql.ApiAuditLogRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final ApiAuditLogRepository auditLogRepository;

    @GetMapping
    public ResponseEntity<Page<ApiAuditLog>> getAllAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return ResponseEntity.ok(auditLogRepository.findAll(pageable));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<ApiAuditLog>> getAuditLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(auditLogRepository.findByAction(action));
    }

    @GetMapping("/endpoint/{endpoint}")
    public ResponseEntity<List<ApiAuditLog>> getAuditLogsByEndpoint(@PathVariable String endpoint) {
        return ResponseEntity.ok(auditLogRepository.findByEndpoint(endpoint));
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<ApiAuditLog>> getAuditLogsByHttpMethod(@PathVariable String method) {
        return ResponseEntity.ok(auditLogRepository.findByHttpMethod(method));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApiAuditLog>> getAuditLogsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(auditLogRepository.findByStatus(status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ApiAuditLog>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(auditLogRepository.findByCreatedAtBetween(start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiAuditLog> getAuditLogById(@PathVariable String id) {
        return auditLogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAuditStats() {
        long totalCount = auditLogRepository.count();
        long successCount = auditLogRepository.findByStatus("SUCCESS").size();
        long failedCount = auditLogRepository.findByStatus("FAILED").size();
        
        return ResponseEntity.ok(Map.of(
                "totalCount", totalCount,
                "successCount", successCount,
                "failedCount", failedCount,
                "successRate", totalCount > 0 ? (double) successCount / totalCount * 100 : 0
        ));
    }
}

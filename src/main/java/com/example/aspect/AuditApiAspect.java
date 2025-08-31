package com.example.aspect;

import com.example.annotation.AuditableApi;
import com.example.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditApiAspect {

    private final ObjectMapper objectMapper;
    private final AuditService auditService;

    @Around("@annotation(com.example.annotation.AuditableApi)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // Get the AuditableApi annotation
        AuditableApi auditableApi = method.getAnnotation(AuditableApi.class);
        String action = auditableApi.action();
        
        // Get HTTP request details
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String httpMethod = request.getMethod();
        String endpoint = request.getRequestURI();
        String clientIp = request.getRemoteAddr();
        
        // Get request payload
        Object[] args = joinPoint.getArgs();
        String requestPayload = serializeRequestPayload(args);

        try {
            // Proceed with actual method execution
            Object result = joinPoint.proceed();

            // Log successful response
            String responsePayload = serializeResponsePayload(result);

            // Save audit log
            auditService.saveDetailedAuditLog(
                action,
                clientIp,
                requestPayload,
                responsePayload,
                httpMethod,
                endpoint,
                "SUCCESS"
            );

            return result;

        } catch (Exception ex) {
            // Log exception as response
            auditService.saveDetailedAuditLog(
                action,
                clientIp,
                requestPayload,
                ex.getMessage(),  // Or serialize exception details
                httpMethod,
                endpoint,
                "FAILED"
            );

            throw ex; // rethrow so GlobalExceptionHandler can handle it
        }
    }
    
    /**
     * Serialize request payload to string
     * 
     * @param args Method arguments
     * @return Serialized string representation
     */
    private String serializeRequestPayload(Object[] args) {
        try {
            return objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            return Arrays.toString(args);
        }
    }
    
    /**
     * Serialize response payload to string
     * 
     * @param result Method result
     * @return Serialized string representation
     */
    private String serializeResponsePayload(Object result) {
        if (result == null) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return result.toString();
        }
    }
}

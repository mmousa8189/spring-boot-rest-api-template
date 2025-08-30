package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.service.AuditService;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EntityAuditListener {

       private static AuditService auditService;

    @Autowired
    public void init(AuditService service) {
        auditService = service; // inject static reference
    }


 @PrePersist
    public void prePersist(Object entity) {
        String username = getCurrentUser();
        auditService.log(username, "CREATE", entity.getClass().getSimpleName(),
                getEntityId(entity), null, entity.toString());
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        String username = getCurrentUser();
        // For simplicity, saving only new value. For real case, fetch old entity from DB.
        auditService.log(username, "UPDATE", entity.getClass().getSimpleName(),
                getEntityId(entity), null, entity.toString());
    }

    @PreRemove
    public void preRemove(Object entity) {
        String username = getCurrentUser();
        auditService.log(username, "DELETE", entity.getClass().getSimpleName(),
                getEntityId(entity), entity.toString(), null);
    }

    private String getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : "SYSTEM";
    }

    private String getEntityId(Object entity) {
        try {
            return entity.getClass().getMethod("getId").invoke(entity).toString();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}


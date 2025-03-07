package com.wk6.assignment4.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuditUtil {
    public static Map<String, Object> createAuditData(String action, String entityType, String entityId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "system";

        Map<String, Object> auditData = new HashMap<>();
        auditData.put("action", action);
        auditData.put("entityType", entityType);
        auditData.put("entityId", entityId);
        auditData.put("performedBy", username);
        auditData.put("timestamp", LocalDateTime.now());
        
        return auditData;
    }
}
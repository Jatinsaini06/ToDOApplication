package com.example.nexdew.service.impl;

import org.springframework.stereotype.Component;

@Component
public class PermissionMappingService {

    public String getRequiredPermission(String path, String method) {
        if (method.equals("GET")) return "VIEW_" + getResourceName(path);
        if (method.equals("POST")) return "CREATE_" + getResourceName(path);
        if (method.equals("PUT") || method.equals("PATCH")) return "UPDATE_" + getResourceName(path);
        if (method.equals("DELETE")) return "DELETE_" + getResourceName(path);
        return null;
    }

    private String getResourceName(String path) {
        String[] parts = path.split("/");
        if (parts.length > 2) {
            return parts[2].toUpperCase().replaceAll("S$", ""); // remove plural
        }
        return "RESOURCE";
    }
}

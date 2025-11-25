package com.example.nexdew.service;


import com.example.nexdew.dto.request.Role.RoleRequest;
import com.example.nexdew.entities.Role;

import java.util.List;

public interface RoleService {

     Role createRole(String roleName, List<String> permissionNames);
    public List<Role> getAllRoles();
}

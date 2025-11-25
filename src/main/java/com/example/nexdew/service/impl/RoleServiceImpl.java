package com.example.nexdew.service.impl;


import com.example.nexdew.entities.Permission;
import com.example.nexdew.entities.Role;
import com.example.nexdew.repositpory.PermissionRepository;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl  implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public Role createRole(String roleName, List<String> permissionNames) {

        Role role = new Role();
        role.setRoleName(roleName);

        Set<Permission> permissions = new HashSet<>();
        for (String permName : permissionNames) {
            Permission permission = permissionRepository.findByPermissionName(permName)
                    .orElseGet(() -> {
                        Permission p = new Permission();
                        p.setPermissionName(permName);
                        return permissionRepository.save(p);
                    });
            permissions.add(permission);
        }

        role.setPermissions(permissions);
        return roleRepository.save(role);
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

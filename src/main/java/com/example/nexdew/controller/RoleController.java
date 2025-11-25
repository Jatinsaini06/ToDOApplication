package com.example.nexdew.controller;

import com.example.nexdew.dto.request.Role.RoleRequest;
import com.example.nexdew.entities.Role;
import com.example.nexdew.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleRequest request) {
        Role role = roleService.createRole(request.getRoleName(), request.getPermissions());
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}



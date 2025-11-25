package com.example.nexdew.dto.request.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
 public  class RoleRequest {

    private String roleName;
    private List<String> permissions;
    private String newRoleName;

}
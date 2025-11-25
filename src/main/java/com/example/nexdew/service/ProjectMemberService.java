package com.example.nexdew.service;


import com.example.nexdew.dto.request.Role.RoleRequest;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.entities.ProjectMember;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ProjectMemberService {


    @Transactional
    List<ProjectMember> getMembersByProject(TokenHandle tokenHandle);

    @Transactional
    String removeMember(TokenHandle tokenHandle);

    @Transactional
    String updateMemberRole(TokenHandle userId, RoleRequest newRoleName);
}

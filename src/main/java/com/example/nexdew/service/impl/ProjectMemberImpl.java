package com.example.nexdew.service.impl;


import com.example.nexdew.Exception.BusinessException;
import com.example.nexdew.dto.request.Role.RoleRequest;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.entities.ProjectMember;
import com.example.nexdew.entities.Role;
import com.example.nexdew.entities.Users;
import com.example.nexdew.repositpory.ProjectMemRepository;
import com.example.nexdew.repositpory.ProjectRepository;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.repositpory.UserRepository;
import com.example.nexdew.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectMemberImpl implements ProjectMemberService {

    private final ProjectMemRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public List<ProjectMember> getMembersByProject(TokenHandle tokenHandle) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        List<ProjectMember> memberships = projectMemberRepository.findByUsers(user);

        List<UUID> projectIds = memberships.stream()
                .map(m -> m.getProject().getProjectId())
                .toList();

        return projectMemberRepository.findByProject_ProjectIdIn(projectIds);
    }

    @Transactional
    @Override
    public String removeMember(TokenHandle tokenHandle) {

        Users currentUser = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        ProjectMember member = projectMemberRepository.findByUsers(currentUser)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("You are not part of any project"));

        projectMemberRepository.delete(member);
        return "You have been removed from project: " + member.getProject().getProjectName();
    }


    @Transactional
    @Override
    public String updateMemberRole(TokenHandle tokenHandle, RoleRequest roleRequest) {

        Users currentUser = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        ProjectMember member = projectMemberRepository.findByUsers(currentUser)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Project member record not found"));

        Role newRole = roleRepository.findByRoleName(roleRequest.getNewRoleName())
                .orElseThrow(() -> new BusinessException("Role not found: " + roleRequest.getRoleName()));

        member.setRole(newRole);
        projectMemberRepository.save(member);

        return "Your role has been updated to " + newRole.getRoleName()
                + " for project " + member.getProject().getProjectName();
    }
}
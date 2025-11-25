package com.example.nexdew.service.impl;

import com.example.nexdew.Exception.BusinessException;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projectMember.ProjectMemRequest;
import com.example.nexdew.entities.Project;
import com.example.nexdew.entities.ProjectInvitation;
import com.example.nexdew.entities.Role;
import com.example.nexdew.entities.Users;
import com.example.nexdew.repositpory.ProjectInvitationRepository;
import com.example.nexdew.repositpory.ProjectRepository;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.repositpory.UserRepository;
import com.example.nexdew.service.EmailService;
import com.example.nexdew.service.ProjectInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectInvitationServiceImpl implements ProjectInvitationService {

    private final ProjectRepository projectRepository;
    private final ProjectInvitationRepository invitationRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String sendInvitation(TokenHandle tokenHandle, ProjectMemRequest request) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));


        Project project = projectRepository.findByProjectNameAndUserId(request.getProjectName(), user.getUserId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (invitationRepository.existsByProjectAndEmail(project, request.getEmail())) {
            throw new RuntimeException("Invitation already exists for this email and project");
        }

        String token = UUID.randomUUID().toString();

        ProjectInvitation invite = new ProjectInvitation();
        invite.setEmail(request.getEmail());

        Role role = roleRepository.findByRoleName(request.getRole())
                .orElseThrow(() ->
                new BusinessException("Role Not Found"));
        invite.setRole(role);

        invite.setProject(project);
        invite.setToken(token);
        invitationRepository.save(invite);

        String link = "http://localhost:4200/dashboard/acceptProject?token=" + token;
        emailService.sendInviteEmail(request.getEmail(), project.getProjectName(), link);

        return "Invitation sent to " + request.getEmail();
    }

    @Override
    public ProjectInvitation findByToken(String token) {
        return invitationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired invitation token"));
    }

    @Transactional
    @Override
    public void markAccepted(ProjectInvitation invite) {
        invite.setAccepted(true);
        invitationRepository.save(invite);
    }

}

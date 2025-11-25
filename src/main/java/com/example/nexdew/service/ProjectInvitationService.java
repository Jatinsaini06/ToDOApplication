package com.example.nexdew.service;


import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projectMember.ProjectMemRequest;
import com.example.nexdew.entities.ProjectInvitation;

public interface ProjectInvitationService {

    String sendInvitation(TokenHandle tokenHandle,ProjectMemRequest request);
    ProjectInvitation findByToken(String token);
    void markAccepted(ProjectInvitation invite);
}

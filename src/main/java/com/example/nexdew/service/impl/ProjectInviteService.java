package com.example.nexdew.service.impl;

import com.example.nexdew.entities.ProjectInvitation;
import com.example.nexdew.entities.ProjectMember;
import com.example.nexdew.entities.Users;

import com.example.nexdew.repositpory.ProjectMemRepository;
import com.example.nexdew.repositpory.UserRepository;
import com.example.nexdew.service.ProjectInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectInviteService {

    private final ProjectInvitationService invitationService;
    private final UserRepository userRepository;
    private final ProjectMemRepository memberRepository;

    @Transactional
    public String acceptInvitation(String token, UUID userId) {

        ProjectInvitation invite = invitationService.findByToken(token);

        if (invite.isAccepted()) {
            return "Invitation already accepted";
        }
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (memberRepository.existsByProjectAndUsers(invite.getProject(), user)) {
            invitationService.markAccepted(invite);
            return "You are already a member of this project";
        }
        ProjectMember member = new ProjectMember();
        member.setProject(invite.getProject());
        member.setUsers(user);
        member.setRole(invite.getRole());
        memberRepository.save(member);

        invitationService.markAccepted(invite);

        return "Invitation accepted and you were added to the project";
    }
}

package com.example.nexdew.controller;

import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projectMember.ProjectMemRequest;
import com.example.nexdew.service.ProjectInvitationService;
import com.example.nexdew.service.impl.ProjectInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class ProjectInvitationController {

    private final ProjectInvitationService invitationService;
    private final ProjectInviteService projectInviteService;

    @PostMapping("/send")
    public ResponseEntity<?> sendInvite(
            @AuthenticationPrincipal TokenHandle tokenHandle,
            @RequestBody ProjectMemRequest request){
        String msg = invitationService.sendInvitation(tokenHandle,request);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvite(@RequestParam("token") String token,
                                               @AuthenticationPrincipal TokenHandle tokenHandle) {
        if (tokenHandle == null) {
            return ResponseEntity.status(401).body("Login required to accept invitation");
        }

        String response = projectInviteService.acceptInvitation(token, tokenHandle.getUserID());
        return ResponseEntity.ok(response);
    }

}

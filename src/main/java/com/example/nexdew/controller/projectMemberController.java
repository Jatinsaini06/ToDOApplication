package com.example.nexdew.controller;


import com.example.nexdew.dto.request.Role.RoleRequest;
import com.example.nexdew.dto.request.TokenHandle;

import com.example.nexdew.entities.ProjectMember;
import com.example.nexdew.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/projectMemberController")
@RequiredArgsConstructor
public class projectMemberController {


    private final ProjectMemberService projectMemberService;

    @GetMapping("/get")
    @Transactional
    public ResponseEntity<List<ProjectMember>> getMembers(@AuthenticationPrincipal TokenHandle tokenHandle) {
        List<ProjectMember> members = projectMemberService.getMembersByProject(tokenHandle);
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/remove")
    @Transactional
    public ResponseEntity<String> removeMember(@AuthenticationPrincipal TokenHandle tokenHandle) {
        String response = projectMemberService.removeMember(tokenHandle);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-role")
    @Transactional
    public ResponseEntity<String> updateMemberRole(
            @AuthenticationPrincipal TokenHandle tokenHandle,
            @RequestBody RoleRequest roleRequest
    ) {
        String response = projectMemberService.updateMemberRole(tokenHandle, roleRequest);
        return ResponseEntity.ok(response);
    }
}

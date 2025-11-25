package com.example.nexdew.controller;


import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projects.CreateProject;
import com.example.nexdew.dto.request.projects.DeleteProject;
import com.example.nexdew.dto.request.projects.ProjectUpdate;
import com.example.nexdew.dto.request.user.UserRequest;
import com.example.nexdew.dto.response.ProjectResponse;
import com.example.nexdew.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class projectController {

private final ProjectService projectService;

@PostMapping("/createProject")
public ResponseEntity<Map<String, String>> createUser(@AuthenticationPrincipal TokenHandle tokenHandle, @RequestBody CreateProject createProject) {
    String project = projectService.CreateProject(tokenHandle, createProject);
    Map<String, String> response = new HashMap<>();
    response.put("message", "Project Created successful");
    return ResponseEntity.ok(response);
}

    @GetMapping("/GetProject")
    public ResponseEntity<List<ProjectResponse>> getUser(@AuthenticationPrincipal TokenHandle userRequest) {
        List<ProjectResponse> project = projectService.getProject(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/UpdateProject")
    public ResponseEntity<Map<String, String>> ResetPassword(@AuthenticationPrincipal TokenHandle tokenHandle ,@RequestBody ProjectUpdate projectUpdate) {

        String updateProject = projectService.updateProject(tokenHandle, projectUpdate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset Successful");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteProject(@AuthenticationPrincipal TokenHandle tokenHandle , @RequestBody DeleteProject deleteProj) {
        String deleteProject = projectService.DeleteUserBYId(tokenHandle, deleteProj  );
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Project Deleted Successful");
        return ResponseEntity.ok(response);
    }

}

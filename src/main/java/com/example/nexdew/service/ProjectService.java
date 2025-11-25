package com.example.nexdew.service;

import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projects.CreateProject;
import com.example.nexdew.dto.request.projects.DeleteProject;
import com.example.nexdew.dto.request.projects.ProjectUpdate;
import com.example.nexdew.dto.response.ProjectResponse;

import java.util.List;


public interface ProjectService {

    String CreateProject(TokenHandle tokenHandle,CreateProject createProject);
    List<ProjectResponse> getProject (TokenHandle tokenHandle);
    String updateProject (TokenHandle tokenHandle, ProjectUpdate projectUpdate);
     String DeleteUserBYId(TokenHandle tokenHandle, DeleteProject deleteProject);
}

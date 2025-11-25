package com.example.nexdew.service.impl;

import com.example.nexdew.Exception.BusinessException;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.projects.CreateProject;
import com.example.nexdew.dto.request.projects.DeleteProject;
import com.example.nexdew.dto.request.projects.ProjectUpdate;
import com.example.nexdew.dto.response.ProjectResponse;
import com.example.nexdew.entities.Project;
import com.example.nexdew.entities.Role;
import com.example.nexdew.entities.Users;
import com.example.nexdew.repositpory.ProjectRepository;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.repositpory.UserRepository;
import com.example.nexdew.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public String CreateProject(TokenHandle tokenHandle, CreateProject createProject) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        Project project = modelMapper.map(createProject, Project.class);
        project.setCreatedAt(LocalDateTime.now());
        project.setCreatedBy("SYSTEM");
        project.setUsers(user);

        Role adminRole = roleRepository.findByRoleName("PROJECT_ADMIN").orElseThrow(() ->
                new BusinessException("Role Not Found"));
        project.setRole(adminRole);

        projectRepository.save(project);
        return "Project created successfully";
    }

    @Override
    public List<ProjectResponse> getProject(TokenHandle tokenHandle) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        List<Project> userProjects = user.getProjects();

        return userProjects.stream()
                .map(project -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String updateProject(TokenHandle tokenHandle, ProjectUpdate projectUpdate) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        Project project = projectRepository.findByProjectNameAndUserId(projectUpdate.getOldProjectName(), user.getUserId())
                .orElseThrow(()-> new BusinessException("Not Found"));

        project.setProjectName(projectUpdate.getNewProjectName());
        project.setModifiedAt(LocalDateTime.now());
        project.setModifiedBy("SYSTEM");

        projectRepository.save(project);

        return "Project updated successfully";
    }



    @Override
    @Transactional
    public String DeleteUserBYId(TokenHandle tokenHandle, DeleteProject deleteProject) {

        Users user = userRepository.findById(tokenHandle.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!passwordEncoder.matches(deleteProject.getPassword(), user.getUserPassword())) {
            throw  new BusinessException("Old password is incorrect!");
        }

        Project project = projectRepository.findByProjectNameAndUserId(deleteProject.getProjectName(), user.getUserId())
                .orElseThrow(()-> new BusinessException("Not Found"));
        ;

        if (project == null) {
            throw new BusinessException("Project not found for user: " + user.getUserName());
        }
        projectRepository.deleteByProjectName(project.getProjectName());

        return " projects deleted successfully for user " + user.getUserName();
    }
}

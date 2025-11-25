package com.example.nexdew.repositpory;

import com.example.nexdew.entities.Project;
import com.example.nexdew.entities.ProjectMember;
import com.example.nexdew.entities.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemRepository extends BaseRepository<ProjectMember , UUID>{

    List<ProjectMember> findByProject_ProjectId(UUID projectId);
    Optional<ProjectMember> findByProject_ProjectIdAndUsers_UserId(UUID projectId, UUID userId);
    boolean existsByProjectAndUsers(Project project, Users user);

    List<ProjectMember> findByUsers(Users user);

    List<ProjectMember> findByProject_ProjectIdIn(List<UUID> projectIds);
}


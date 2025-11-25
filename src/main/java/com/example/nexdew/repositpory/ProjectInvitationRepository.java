package com.example.nexdew.repositpory;
import java.util.Optional;

import com.example.nexdew.entities.Project;
import com.example.nexdew.entities.ProjectInvitation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectInvitationRepository extends BaseRepository<ProjectInvitation, UUID> {
    Optional<ProjectInvitation> findByToken(String token);


    boolean existsByProjectAndEmail(Project project, String email);
}

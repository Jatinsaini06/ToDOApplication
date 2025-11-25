package com.example.nexdew.repositpory;

import com.example.nexdew.entities.Project;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends  BaseRepository<Project , UUID>{


    @Query("SELECT p FROM Project p WHERE p.projectName = :oldProjectName AND p.users.userId = :userId")
    Optional<Project>  findByProjectNameAndUserId(String oldProjectName, UUID userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.projectName = :projectName")
    void deleteByProjectName(@Param("projectName") String projectName);

    Optional<Project> findByProjectName(String projectName);

}

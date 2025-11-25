package com.example.nexdew.repositpory;

import com.example.nexdew.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoleRepository extends BaseRepository<Role, UUID>{

    Optional<Role> findByRoleName(String name);

}

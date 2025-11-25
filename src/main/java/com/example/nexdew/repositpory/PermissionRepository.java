package com.example.nexdew.repositpory;


import com.example.nexdew.entities.Permission;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, UUID> {
    Optional<Permission> findByPermissionName(String name);


}

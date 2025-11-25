package com.example.nexdew.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "permission")
@Data

public class Permission {

    @Id
    @Column(name = "permission_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

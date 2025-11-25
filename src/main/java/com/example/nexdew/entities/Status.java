package com.example.nexdew.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "status")
@Data

public class Status {

    @Id
    @Column(name = "status_id", nullable = false)
    private UUID statusId;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @OneToMany(mappedBy = "status")
    private List<Task> tasks = new ArrayList<>();
}

package com.example.nexdew.dto.request.projects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProjectUpdate {

    private String oldProjectName;
    private String newProjectName;

}

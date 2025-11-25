package com.example.nexdew.dto.request.projects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProject {

    private String projectName;
    private String password;
}

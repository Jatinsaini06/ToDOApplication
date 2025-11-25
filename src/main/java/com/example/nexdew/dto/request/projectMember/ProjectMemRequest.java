package com.example.nexdew.dto.request.projectMember;


import com.example.nexdew.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemRequest {

    private String projectName;
    private String email;
    private String role;

}

package com.example.nexdew.dto.request.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdate {
    private String oldPassword;
    private String newPassword;
}

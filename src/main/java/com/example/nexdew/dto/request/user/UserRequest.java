package com.example.nexdew.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String userEmail;
    private String userName;
    private String userPhoneNo;
    private String userPassword;
    private String Role;

    private String businessInformation;
    private String createdBy;

}

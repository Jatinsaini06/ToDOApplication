package com.example.nexdew.service;

import com.example.nexdew.dto.request.user.PasswordUpdate;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.user.UserRequest;
import com.example.nexdew.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    String CreateUser(UserRequest userRequest);

    List<UserResponse> getUser(TokenHandle userRequest);

    String ResetPass(TokenHandle userRequest, PasswordUpdate pass);

    String DeleteUserBYId(TokenHandle tokenHandle ,UserRequest userRequest);

}

package com.example.nexdew.controller;


import com.example.nexdew.dto.request.user.PasswordUpdate;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.user.UserRequest;
import com.example.nexdew.dto.response.UserResponse;
import com.example.nexdew.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserRequest userRequest) {

        String user = userService.CreateUser(userRequest);
        Map<String, String> respone = new HashMap<>();
        respone.put("message", "User Created successful");
        return ResponseEntity.ok(respone);
    }

    @GetMapping("/GetUser")
    public ResponseEntity<List<UserResponse>> getUser(@AuthenticationPrincipal TokenHandle userRequest) {
        List<UserResponse> user = userService.getUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/ResetPassword")
    public ResponseEntity<Map<String, String>> ResetPassword(@AuthenticationPrincipal TokenHandle userRequest ,@RequestBody PasswordUpdate passwp) {

        String user = userService.ResetPass(userRequest, passwp);
        Map<String, String> respone = new HashMap<>();
        respone.put("message", "Password reset Successful");
        return ResponseEntity.ok(respone);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteUser(@AuthenticationPrincipal TokenHandle tokenHandle , @RequestBody UserRequest userRequest1) {
        String deleteUser = userService.DeleteUserBYId(tokenHandle, userRequest1);
        Map<String, String> response = new HashMap<>();
        response.put("Message", "User Deleted Successful");
        return ResponseEntity.ok(response);
    }


}

package com.example.nexdew.service;


import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.entities.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface JwtService {


    String extractUsername(String token);
    List<Map<String, Object>> extractProjectRoles(String token);
    String generateUserToken(TokenHandle user);
    boolean isTokenValid(String token, TokenHandle userDetails);
}
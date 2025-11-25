package com.example.nexdew.controller;

import com.example.nexdew.dto.request.LoginRequest;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.response.LoginResponse;
import com.example.nexdew.service.JwtService;
import com.example.nexdew.service.impl.LoadUserName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenBasedLogin {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoadUserName myUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
            );

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getName());
            TokenHandle tokenHandle = (TokenHandle) userDetails;

            String token = jwtService.generateUserToken(tokenHandle);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}



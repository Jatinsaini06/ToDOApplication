package com.example.nexdew.service.impl;

import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.entities.Permission;
import com.example.nexdew.entities.ProjectMember;
import com.example.nexdew.entities.Role;
import com.example.nexdew.repositpory.ProjectMemRepository;
import com.example.nexdew.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final ProjectMemRepository projectMemRepository;

    private static final String SECRET_KEY = "your-super-secure-secret-key-should-be-very-long";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public List<Map<String, Object>> extractProjectRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("projects", List.class);
    }

    @Override
    public String generateUserToken(TokenHandle user) {

        List<ProjectMember> memberships = projectMemRepository.findByUsers(user.getUsers());

        List<Map<String, Object>> projectDataList = new ArrayList<>();

        for (ProjectMember member : memberships) {
            Role role = member.getRole();

            Set<String> permissions = new HashSet<>();
            if (role.getPermissions() != null) {
                permissions = role.getPermissions()
                        .stream()
                        .map(Permission::getPermissionName)
                        .collect(Collectors.toSet());
            }

            Map<String, Object> projectData = new HashMap<>();
            projectData.put("projectId", member.getProject().getProjectId().toString());
            projectData.put("projectName", member.getProject().getProjectName());
            projectData.put("role", role.getRoleName());
            projectData.put("permissions", permissions);

            projectDataList.add(projectData);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserID().toString());
        claims.put("userName", user.getUsername());
        claims.put("projects", projectDataList);

        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day expiry
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, TokenHandle userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

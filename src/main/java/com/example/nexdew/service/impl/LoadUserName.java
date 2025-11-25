package com.example.nexdew.service.impl;

import com.example.nexdew.Exception.BusinessException;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.entities.Role;
import com.example.nexdew.entities.Users;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.repositpory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadUserName implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public TokenHandle loadUserByUsername(String name) throws UsernameNotFoundException {

            Users user = userRepository.findByUserName(name);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + name);
            }
            String roleName = user.getRole().getRoleName();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));
            return new TokenHandle(user, authorities);
        }

    }

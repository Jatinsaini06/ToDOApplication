package com.example.nexdew.service.impl;

import com.example.nexdew.Exception.BusinessException;
import com.example.nexdew.dto.request.user.PasswordUpdate;
import com.example.nexdew.dto.request.TokenHandle;
import com.example.nexdew.dto.request.user.UserRequest;
import com.example.nexdew.dto.response.UserResponse;
import com.example.nexdew.entities.Role;
import com.example.nexdew.entities.Users;
import com.example.nexdew.repositpory.RoleRepository;
import com.example.nexdew.repositpory.UserRepository;
import com.example.nexdew.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String CreateUser(UserRequest userRequest) {

        if (userRepository.existsByUserEmailAndUserPhoneNo(userRequest.getUserEmail(), userRequest.getUserPhoneNo())) {
            throw  new BusinessException("User email already exits: " + userRequest.getUserEmail());
        }
        Users user = modelMapper.map(userRequest, Users.class);
        user.setUserPassword(passwordEncoder.encode(userRequest.getUserPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("SYSTEM");
        Role role = new Role();
        role.setRoleName("USER");
        user.setRole(role);
        userRepository.save(user);
        return "User created successfully";
    }

    @Override
    public List<UserResponse> getUser(TokenHandle userRequest) {

        Optional<Users> usersList;
            Users user = userRepository.findById(userRequest.getUserID()).orElseThrow(()->
                    new BusinessException("User not found"));

            usersList = userRepository.findById(userRequest.getUserID());
            if (usersList.isEmpty()) {
                throw new BusinessException("No users found");
            }
        return usersList.stream()
                .map(u -> modelMapper.map(u, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String ResetPass(TokenHandle userRequest, PasswordUpdate pass) {

        Users user = userRepository.findById(userRequest.getUserID())
                .orElseThrow(() -> new BusinessException("User not found"));

        if (!passwordEncoder.matches(pass.getOldPassword(),pass.getNewPassword())) {
            throw  new BusinessException("Old and New Password Must No Be Same");
        }

        if (!passwordEncoder.matches(pass.getOldPassword(), user.getUserPassword())) {
            throw  new BusinessException("Old password is incorrect!");
        }

        user.setUserPassword(passwordEncoder.encode(pass.getNewPassword()));
        user.setModifiedAt(LocalDateTime.now());
        user.setModifiedBy("SYSTEM");
        userRepository.save(user);

        return "Password updated successfully";
    }

    @Override
    public String DeleteUserBYId(TokenHandle tokenHandle, UserRequest userRequest) {

        Users user = userRepository.findById(tokenHandle.getUserID()).orElseThrow(()
                -> new BusinessException("User not found"));

        if (!passwordEncoder.matches(userRequest.getUserPassword(), user.getUserPassword())) {
            throw  new BusinessException("Password is incorrect!");
        }
        userRepository.deleteById(tokenHandle.getUserID());
        throw  new BusinessException("User deleted successfully");

    }
}
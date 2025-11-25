package com.example.nexdew.repositpory;


import com.example.nexdew.entities.Users;

import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepository extends BaseRepository<Users, UUID>{

    Users findByUserName(String userName);

    boolean existsByUserEmailAndUserPhoneNo(String userEmail, String userPhoneNo);
}

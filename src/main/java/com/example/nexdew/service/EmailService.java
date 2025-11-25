package com.example.nexdew.service;


import com.example.nexdew.dto.request.projectMember.ProjectMemRequest;

public interface EmailService {

     void sendInviteEmail(String to, String projectName, String link);

}

package com.example.nexdew.service.impl;

import com.example.nexdew.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public void sendInviteEmail(String to, String projectName, String link) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Invitation to join project: " + projectName);
        msg.setText("You've been invited to join project: " + projectName + "\n\nClick to accept:\n" + link
                + "\n\nIf you don't have an account, please sign up first and then use the same link.");
        mailSender.send(msg);
    }
}

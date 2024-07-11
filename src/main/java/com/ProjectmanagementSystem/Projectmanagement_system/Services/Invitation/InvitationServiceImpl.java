package com.ProjectmanagementSystem.Projectmanagement_system.Services.Invitation;

import com.ProjectmanagementSystem.Projectmanagement_system.Repositories.InvitationRepository;
import com.ProjectmanagementSystem.Projectmanagement_system.Services.Email.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ProjectmanagementSystem.Projectmanagement_system.Models.Invitation;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    InvitationRepository invitationRepository;

    @Autowired
    EmailService emailService;


    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {

        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setToken(invitationToken);
        invitation.setProjectId(projectId);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);;
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {
        return null;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        return "";
    }

    @Override
    public void deleteToken() {

    }
}

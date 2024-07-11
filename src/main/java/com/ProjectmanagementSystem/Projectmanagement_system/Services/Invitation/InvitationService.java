package com.ProjectmanagementSystem.Projectmanagement_system.Services.Invitation;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userId);

    public String getTokenByUserMail(String userEmail);

    public void deleteToken();
}

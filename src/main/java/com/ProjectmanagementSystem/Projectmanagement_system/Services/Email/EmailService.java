package com.ProjectmanagementSystem.Projectmanagement_system.Services.Email;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailWithToken(String userEmail, String link) throws MessagingException;
}

package com.ProjectmanagementSystem.Projectmanagement_system.Services.Chat;

import com.ProjectmanagementSystem.Projectmanagement_system.Models.Chat;
import com.ProjectmanagementSystem.Projectmanagement_system.Repositories.ChatRepository;

public class ChatServiceImpl implements ChatService{

    private ChatRepository chatRepository;
    @Override
    public Chat createChat(Chat chat) {
        chatRepository.save(chat);
        return null;
    }
}

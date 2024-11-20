package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.ChatGroup;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    public Chat getChatById(Integer chatId){
        Optional<Chat> chat = chatRepository.findById(chatId);
        return chat.orElse(null);
    }
}

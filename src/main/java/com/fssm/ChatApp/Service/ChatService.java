package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.ChatGroup;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Chat createChat(Chat chat){
        try {
            return chatRepository.save(chat);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to create chat: " + ex.getMessage());
        }
    }
    public Chat updateChat(Chat chat){
        try {
            return chatRepository.save(chat);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update chat: " + ex.getMessage());
        }
    }
    public void deleteChat(Integer chatId){
        try{
            chatRepository.deleteById(chatId);
        }catch(Exception e){
            throw new RuntimeException("Failed to remove chat: " + e.getMessage());
        }
    }
    public Chat getChat(Integer chatId){
        Optional<Chat> chat = chatRepository.findById(chatId);
        return chat.orElse(null);
    }

}

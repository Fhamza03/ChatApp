package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.ChatGroup;
import com.fssm.ChatApp.Repository.ChatRepository;
import com.fssm.ChatApp.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping("/CreateChat")
    public Chat createChat(@RequestBody Chat chat) {
        try{
            return chatService.createChat(chat);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @GetMapping("/getChat/{chatId}")
    public Chat getChat(@PathVariable Integer chatId) {
        try{
            return chatService.getChat(chatId);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PutMapping("/UpdateChat")
    public Chat updateChat(@RequestBody Chat chat) {
        try{
            return chatService.updateChat(chat);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteChat/{chatId}")
    public String deleteChat(@PathVariable Integer chatId) {
        try{
            chatService.deleteChat(chatId);
            return "Chat deleted successfully";
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}

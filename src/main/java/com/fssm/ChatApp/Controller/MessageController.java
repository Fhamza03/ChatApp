package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.Message;
import com.fssm.ChatApp.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/saveMessage/{senderId}/{chatId}")
    public List<Message> saveMessage(@PathVariable Integer senderId,@PathVariable Integer chatId,@RequestBody String messageContent){
        messageContent = messageContent.trim().replaceAll("[\\r\\n]+", "");
        return messageService.saveMessage(senderId,chatId,messageContent);
    }
}

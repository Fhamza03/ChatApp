package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.Message;
import com.fssm.ChatApp.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages/{chatId}")
    public void sendMessage(@Payload Map<String, Object> payload) {
        Integer senderId = (Integer) payload.get("senderId");
        Integer chatId = (Integer) payload.get("chatId");
        String messageContent = (String) payload.get("content");

        if (senderId == null) {
            System.err.println("Sender is null in the message");
            return;
        }
        if (chatId == null) {
            System.err.println("Chat is null in the message");
            return;
        }

        // Save the message
        messageService.saveMessage(senderId, chatId, messageContent);


        // Broadcast the message
        Map<String, Object> messagePayload = Map.of(
                "senderId", senderId,
                "chatId", chatId,
                "content", messageContent
        );
        messagingTemplate.convertAndSend("/topic/messages/" + chatId, messagePayload);

    }

    @GetMapping("/getMessages/{chatId}")
    public List<Message> getMessages(@PathVariable Integer chatId) {
        return messageService.getMessagesForChat(chatId);
    }

}

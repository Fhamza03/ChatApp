package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
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
        String messageTimeString = (String) payload.get("messageTime"); // Receive messageTime as String (ISO 8601 format)

        if (senderId == null) {
            System.err.println("Sender is null in the message");
            return;
        }
        if (chatId == null) {
            System.err.println("Chat is null in the message");
            return;
        }
        if (messageTimeString == null) {
            System.err.println("Message time is null");
            return;
        }

        // Convert the messageTime string to a Date object
        Date messageTime = null;
        try {
            messageTime = Date.from(Instant.parse(messageTimeString)); // Convert ISO 8601 string to Date
        } catch (Exception e) {
            System.err.println("Invalid message time format: " + messageTimeString);
            return; // Handle invalid date format gracefully
        }

        // Save the message with the timestamp received from the front end
        messageService.saveMessage(senderId, chatId, messageContent, messageTime);

        // Broadcast the message with the timestamp to the front-end
        Map<String, Object> messagePayload = Map.of(
                "senderId", senderId,
                "chatId", chatId,
                "content", messageContent,
                "messageTime", messageTime // Include message time in the payload
        );
        messagingTemplate.convertAndSend("/topic/messages/" + chatId, messagePayload);
    }

    @GetMapping("/getMessages/{chatId}")
    public List<Map<String, Object>> getMessages(@PathVariable Integer chatId) {
        return messageService.getMessagesForChat(chatId);
    }
}

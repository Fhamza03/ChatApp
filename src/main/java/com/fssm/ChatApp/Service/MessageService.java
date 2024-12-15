package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.Message;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserChat;
import com.fssm.ChatApp.Repository.MessageRepository;
import com.fssm.ChatApp.Repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserChatRepository userChatRepository;
    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;

    public Message getMessage(Integer messageId){
        try{
            Optional<Message> message = messageRepository.findById(messageId);
            return message.orElse(null);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public List<Map<String, Object>> getMessagesForChat(Integer chatId) {
        // Get the raw result from the repository
        List<Object[]> result = messageRepository.findMessagesWithUsernamesByChatId(chatId);

        // Create a list of maps to store each message's data
        List<Map<String, Object>> messages = new ArrayList<>();

        // Iterate over the result and convert each Object[] into a Map
        for (Object[] row : result) {
            Map<String, Object> messageMap = new HashMap<>();

            // Map the fields from Object[] to Map with field names as keys
            messageMap.put("message_id", row[0]);
            messageMap.put("content", row[1]);
            messageMap.put("message_time", row[2]);
            messageMap.put("chat_id", row[3]);
            messageMap.put("receiver_id", row[4]);
            messageMap.put("sender_id", row[5]);
            messageMap.put("sender_username", row[6]); // sender username
            messageMap.put("receiver_username", row[7]); // receiver username

            // Add the map to the list
            messages.add(messageMap);
        }

        return messages;
    }

    public List<Message> getAllMessagesbetween(Integer senderId, Integer receiverId){
        try {
            List<Message> messages = messageRepository.findBySenderReceiver(senderId,receiverId);
            return messages;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Message> saveMessage(Integer senderId, Integer chatId, String messageContent, Date messageTime) {
        // Step 1: Find the sender
        User sender = userService.getUser(senderId);

        // Step 2: Find the chat by chatId
        Chat chat = chatService.getChat(chatId);
        if (chat == null) {
            throw new RuntimeException("Chat not found");
        }

        // Step 3: Check if the sender is part of the chat
        List<UserChat> userChats = userChatRepository.findByChat(chat);
        boolean isSenderInChat = userChats.stream()
                .anyMatch(userChat -> userChat.getUser().getUserId().equals(senderId));

        if (!isSenderInChat) {
            throw new RuntimeException("Sender is not part of this chat");
        }

        // Step 4: Find all users in the specified chat (excluding the sender)
        List<User> receivers = userChats.stream()
                .filter(userChat -> !userChat.getUser().getUserId().equals(senderId))
                .map(UserChat::getUser)
                .toList();

        // Step 5: Create the messages for each receiver
        List<Message> messages = receivers.stream().map(receiver -> {
            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setContent(messageContent);
            message.setChat(chat);
            message.setMessageTime(messageTime);
            return messageRepository.save(message);
        }).toList();

        // Return the list of saved messages
        return messages;
    }

}

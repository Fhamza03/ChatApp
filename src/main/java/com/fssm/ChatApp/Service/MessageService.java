package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.Message;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserChat;
import com.fssm.ChatApp.Repository.MessageRepository;
import com.fssm.ChatApp.Repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Message> getMessagesForChat(Integer chatId) {
        return messageRepository.findByChat(chatId);
    }

    public List<Message> getAllMessagesbetween(Integer senderId, Integer receiverId){
        try {
            List<Message> messages = messageRepository.findBySenderReceiver(senderId,receiverId);
            return messages;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Message> saveMessage(Integer senderId, Integer chatId, String messageContent) {
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
        Date messageTime = new Date();
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

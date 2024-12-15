package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Service.UserChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class UserChatController {
    @Autowired
    UserChatService userChatService;

    @GetMapping("/getAllChats/{userId}")
    public List<Map<String, Object>> getAllChatsForUser(@PathVariable Integer userId) {
        List<Object[]> results = userChatService.getAllUserChats(userId);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> chatData = new HashMap<>();
            chatData.put("user_id", row[0]);
            chatData.put("friend_id", row[1]);
            chatData.put("chat_id", row[2]);
            chatData.put("chat_type", row[3]);
            chatData.put("friend_username", row[4]); // Add the friend's username
            response.add(chatData);
        }

        return response;
    }

}

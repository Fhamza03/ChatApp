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
    public List<Map<String, Object>> getAllChats(@PathVariable Integer userId) {
        return userChatService.getAllUserChats(userId);  // Call the service method
    }

}

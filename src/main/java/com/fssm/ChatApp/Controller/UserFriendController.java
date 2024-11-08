package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class UserFriendController {
    @Autowired
    UserFriendService userFriendService;

    @GetMapping("/getFriends/{userId}")
    public List<User> getFriends(@PathVariable Integer userId){
        return userFriendService.friendsOf(userId);
    }
}

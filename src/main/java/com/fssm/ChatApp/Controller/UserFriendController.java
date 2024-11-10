package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Service.UserFriendService;
import com.fssm.ChatApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class UserFriendController {
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    UserService userService;
    // This method get all the friends for a specefic user by his id
    @GetMapping("/getFriends/{userId}")
    public List<User> getFriends(@PathVariable Integer userId){
        return userFriendService.friendsOf(userId);
    }
    // This method make Friendship between two users
    @PostMapping("/makeFriendship/{userId}/{friendId}")
    public void makeFriendShip(@PathVariable Integer userId, @PathVariable Integer friendId){
        userFriendService.makeFriendship(userService.getUser(userId),userService.getUser(friendId));
    }

}

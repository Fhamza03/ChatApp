package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Service.UserFriendService;
import com.fssm.ChatApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class UserFriendController {
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    UserService userService;
    // This method get all the friends for a specefic user by his id
    @GetMapping("/getFriends/{userId}")
    public List<User> getFriends(@PathVariable Integer userId) {
        try {
            // Fetch friends list using the service method
            List<User> friendsList = userFriendService.friendsOf(userId);

            // Return the friends list
            return friendsList;
        } catch (Exception e) {
            // Handle any exceptions that may occur and return an appropriate response
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to retrieve friends for user ID " + userId,
                    e
            );
        }
    }


    // This method make Friendship between two users
    @PostMapping("/makeFriendship/{userId}/{friendId}")
    public void makeFriendShip(@PathVariable Integer userId, @PathVariable Integer friendId){
        userFriendService.makeFriendship(userService.getUser(userId),userService.getUser(friendId));
    }

}

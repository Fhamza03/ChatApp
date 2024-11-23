package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.Status;
import com.fssm.ChatApp.Service.ChatService;
import com.fssm.ChatApp.Service.FriendRequestService;
import com.fssm.ChatApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestController {
    @Autowired
    FriendRequestService friendRequestService;
    @Autowired
    UserService userService;

    // This function send a friendship request from a user to an other one
    @PostMapping("/sendRequest/{sender}/{receiver}")
    public String sendRequest(@PathVariable Integer sender,@PathVariable Integer receiver){
        try{
            //After getting the users id (sender and receiver) in the url we save them in our FriendRequest Table
            friendRequestService.sendRequest(userService.getUser(sender),userService.getUser(receiver));
            return "Friend request sent successfully!";
        }catch (Exception e){
            // In case there's an error we throw an exception
            throw new RuntimeException("Failed to send request: "+ e.getMessage());
        }
    }

    // This function takes as a parameter a specific friendrequest id to update his status from PENDING
    // TO ACCEPTED or REJECTED based on the friend action
    @PutMapping("/FriendshipAccepted/{friendRequestId}")
    public String onAcceptFriendRequest(@PathVariable Integer friendRequestId){
        try{
            friendRequestService.onAcceptFriendRequest(friendRequestId);
            return "Friend request accepted!";
        }catch (Exception e){
            throw new RuntimeException("Failed to update status: "+ e.getMessage());
        }
    }
    @PutMapping("/FriendshipRejected/{friendRequestId}")
    public String onRejectFriendRequest(@PathVariable Integer friendRequestId){
        try{
            friendRequestService.onRejectFriendRequest(friendRequestId);
            return "Friend request rejected!";
        }catch (Exception e){
            // In case there's an error we throw an exception
            throw new RuntimeException("Failed to update status: "+ e.getMessage());
        }
    }

}

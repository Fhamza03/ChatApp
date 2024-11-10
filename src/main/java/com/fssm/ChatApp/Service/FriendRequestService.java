package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.FriendRequest;
import com.fssm.ChatApp.Model.Status;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    @Autowired
    FriendRequestRepository friendRequestRepository;

    // This function let a user send a request to an other user so they can be friends and they can have a chat after
    public String sendRequest(User sender,User receiver){
        try{
            // creating new FriendRequest object to save in our database
            FriendRequest friendRequest = new FriendRequest();
            // Set the sender
            friendRequest.setSender(sender);
            // set the receiver
            friendRequest.setReceiver(receiver);
            // For the first time the request status PENDING...
            friendRequest.setStatus(Status.PENDING);
            // save our object in the database
            friendRequestRepository.save(friendRequest);
            return sender.getUserName() + " has sent a friend request to " + receiver.getUserName();
        }catch (Exception e){
            // in case there's an error throw a new Exception
            throw new RuntimeException("Failed to send Request: "+ e.getMessage());
        }
    }

}

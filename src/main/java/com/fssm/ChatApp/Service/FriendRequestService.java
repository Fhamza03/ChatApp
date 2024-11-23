package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.*;
import com.fssm.ChatApp.Repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestService {
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserChatService userChatService;
    @Autowired
    ChatService chatService;
    @Autowired
    NotificationService notificationService;

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

            notificationService.createNotification(sender,receiver,"Notification sent from " +sender.getUserName()+" to "+receiver.getUserName()+" Successfully");

            // save our object in the database
            friendRequestRepository.save(friendRequest);
            return sender.getUserName() + " has sent a friend request to " + receiver.getUserName();
        }catch (Exception e){
            // in case there's an error throw a new Exception
            throw new RuntimeException("Failed to send Request: "+ e.getMessage());
        }
    }

    // Return a FriendRequest row from database by the id
    public FriendRequest getFriendRequest(Integer friendRequestId){
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(friendRequestId);
        return friendRequest.orElse(null);
    }

    public String onAcceptFriendRequest(Integer friendRequestId){
        try{
            FriendRequest friendRequest = getFriendRequest(friendRequestId);
            friendRequest.setStatus(Status.ACCEPTED);
            friendRequestRepository.save(friendRequest);

            Chat chat = new Chat(ChatType.NORMAL);
            chatService.createChat(chat);
            userChatService.LinkTwoUsersWithChat(friendRequest.getSender(),friendRequest.getReceiver(),chat);

            return "The status successfully accepted";
        }catch (Exception e){
            // in case there's an error throw a new Exception
            throw new RuntimeException("Failed to update request Status: "+ e.getMessage());
        }
    }
    public String onRejectFriendRequest(Integer friendRequestId){
        try{
            FriendRequest friendRequest = getFriendRequest(friendRequestId);
            friendRequest.setStatus(Status.REJECTED);
            friendRequestRepository.save(friendRequest);
            return "The friendRequest successfully rejected";
        }catch (Exception e){
            throw new RuntimeException("Failed to update request Status: "+ e.getMessage());
        }
    }


}

package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.FriendRequest;
import com.fssm.ChatApp.Model.Status;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    // Return a FriendRequest row from database by the id
    public FriendRequest getFriendRequest(Integer friendRequestId){
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(friendRequestId);
        return friendRequest.orElse(null);
    }

    // This function takes as a parameter a specific friendrequest id to update his status from PENDING
    // TO ACCEPTED or REJECTED based on the friend action
    public String updateFriendRequestStatus(Integer friendRequestId,Status status){
        try{
            // get the friendRequest to update from database (First status == PENDING)
            FriendRequest friendRequest = getFriendRequest(friendRequestId);

            if(status == Status.ACCEPTED){
                // if the user click on accept to accept the request of his friend we set the status to ACCEPTED
                friendRequest.setStatus(Status.ACCEPTED);
            }else if(status == Status.REJECTED){
                // if the user click on reject to reject the request of his friend we set the status to REJECTED
                friendRequest.setStatus(Status.REJECTED);
            }
            // updating our row in the database
            friendRequestRepository.save(friendRequest);
            return "The status successfully updated !";
        }catch (Exception e){
            // in case there's an error throw a new Exception
            throw new RuntimeException("Failed to update request Status: "+ e.getMessage());
        }
    }

}

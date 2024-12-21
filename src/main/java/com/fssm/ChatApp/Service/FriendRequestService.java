package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.*;
import com.fssm.ChatApp.Repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    UserFriendService userFriendService;

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

            userFriendService.makeFriendship(friendRequest.getSender(),friendRequest.getReceiver());

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

    public FriendRequest getStatusFriendship(Integer senderId, Integer receiverId){
        try {
            return friendRequestRepository.findRequestBySenderAndReceiver(senderId,receiverId);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Map<String, Object>> getFriendRequestsByReceiverId(Integer receiverId) {
        List<Object[]> results = friendRequestRepository.findAllByReceiverId(receiverId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> request = new HashMap<>();
            request.put("friend_request_id", result[0]);
            request.put("status", result[1]);
            request.put("receiver_id", result[2]);
            request.put("sender_id", result[3]);
            request.put("sender_email", result[4]);
            request.put("sender_first_name", result[5]);
            request.put("sender_last_name", result[6]);
            request.put("sender_username", result[7]);

            response.add(request);
        }

        return response;
    }


}

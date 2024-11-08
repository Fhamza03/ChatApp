package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserFriend;
import com.fssm.ChatApp.Repository.UserFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFriendService {
    // This class define the logic of the userFriend and contain methods
    // to make Friendship between two users and get friends list of a user
    @Autowired
    UserFriendRepository userFriendRepository;

    // This function return a list of friends of a user by his id
    public List<User> friendsOf(Integer userId){
        try{
            List<UserFriend> friendships = userFriendRepository.findAllFriendshipsByUserId(userId);
            return friendships.stream()
                    .map(uf -> uf.getUser().getUserId().equals(userId) ? uf.getFriend() : uf.getUser())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Failed to get friends of " + userId + ": "+e.getMessage());
        }
    }
    

}

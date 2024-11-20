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

    //This function make friendship between two users, it inserts them in the UserFriend Table
    public Boolean makeFriendship(User user, User friend){
        try {
            //create new UserFriend object (first the attributs are null)
            UserFriend friendship = new UserFriend();
            // set the User with a user given in the param
            friendship.setUser(user);
            // set friend with his friend in the param
            friendship.setFriend(friend);
            // save Friendship between these two users in the UserFriend table
            userFriendRepository.save(friendship);
            // return true if all good
            return true;
        }catch (Exception e){
            // if there is an error throw an exception
            throw  new RuntimeException("Failed to make friendship between these two users: "+e.getMessage());
        }
    }

}

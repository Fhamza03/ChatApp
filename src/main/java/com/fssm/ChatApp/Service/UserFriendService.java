package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserFriend;
import com.fssm.ChatApp.Repository.UserFriendRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserFriendService {
    // This class define the logic of the userFriend and contain methods
    // to make Friendship between two users and get friends list of a user
    @Autowired
    UserFriendRepository userFriendRepository;

    // This function return a list of friends of a user by his id
    public List<User> friendsOf(Integer userId) {
        try {
            // Fetch the friends list as a list of Tuples
            List<Tuple> result = userFriendRepository.findFriendsByUserId(userId);

            // Map the Tuples to User objects
            List<User> friendsList = result.stream().map(tuple -> {
                User friend = new User();
                friend.setUserId(tuple.get(0, Integer.class));
                friend.setEmail(tuple.get(1, String.class));
                friend.setFirstName(tuple.get(2, String.class));
                friend.setLastName(tuple.get(3, String.class));
                friend.setUserName(tuple.get(4, String.class));
                return friend;
            }).collect(Collectors.toList());

            // Return the list of friends
            return friendsList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get friends of " + userId + ": " + e.getMessage());
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

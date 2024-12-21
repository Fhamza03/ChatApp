package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserService {
    // This class define the logic of the user and contain methods
    // to Create, Read, Update and Delete a User
    @Autowired
    UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";


    // This function store users in my database
    public User CreateUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Failed to create user: " + ex.getMessage());
        }
    }
    // This function get a specefic user by his id from my database
    public User getUser(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    //This function update the user information
    public User updateUser(User user){
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Failed to update user: " + e.getMessage());
        }
    }

    //This function delete a user by his id
    public void deleteUser(Integer userId){
        try{
            userRepository.deleteById(userId);
        }catch(Exception e){
            throw new RuntimeException("Failed to remove user: " + e.getMessage());
        }
    }
    public List<Map<String, Object>> getAllUsers() {
        List<Object[]> users = userRepository.findAllUsersWithLimitedFields();
        List<Map<String, Object>> userDetails = new ArrayList<>();

        for (Object[] user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", user[0]);
            userMap.put("firstName", user[1]);
            userMap.put("lastName", user[2]);
            userMap.put("userName", user[3]);

            userDetails.add(userMap);
        }

        return userDetails;
    }

    public List<Map<String, Object>> findUsersWithStatus(Integer senderId) {
        List<Object[]> results = userRepository.findUsersWithStatus(senderId);

        List<Map<String, Object>> usersWithStatus = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userId", row[0]);
            userMap.put("email", row[1]);
            userMap.put("firstName", row[2]);
            userMap.put("lastName", row[3]);
            userMap.put("username", row[4]);
            userMap.put("friendshipStatus", row[5] != null ? row[5].toString() : null);

            usersWithStatus.add(userMap);
        }

        return usersWithStatus;
    }

}

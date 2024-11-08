package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

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

}

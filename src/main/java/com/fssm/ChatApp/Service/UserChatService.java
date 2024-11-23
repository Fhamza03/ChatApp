package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserChat;
import com.fssm.ChatApp.Repository.UserChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserChatService {
    @Autowired
    UserChatRepository userChatRepository;

    public Boolean LinkTwoUsersWithChat(User user, User friend,Chat chat){
        try {
            UserChat userChat = new UserChat();
            userChat.setUser(user);
            userChat.setChat(chat);
            userChat.setActive(false);
            userChatRepository.save(userChat);

            UserChat friendChat = new UserChat();
            friendChat.setUser(friend);
            friendChat.setChat(chat);
            friendChat.setActive(false);
            userChatRepository.save(friendChat);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to link the users with that chat : " + e.getMessage());
        }
    }

}

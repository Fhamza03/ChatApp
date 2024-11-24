package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.*;
import com.fssm.ChatApp.Repository.UserChatRepository;
import com.fssm.ChatApp.Repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    ChatGroupService chatGroupService;
    @Autowired
    UserService userService;
    @Autowired
    UserChatRepository userChatRepository;

    public UserGroup getUserGroup(Integer userGroupId){
        Optional<UserGroup> userGroup =  userGroupRepository.findById(userGroupId);
        return  userGroup.orElse(null);
    }

    public UserGroup addUserToGroup(Integer groupId,Integer userId){
        try{
            ChatGroup group = chatGroupService.getChatGroup(groupId);
            User user = userService.getUser(userId);
            UserGroup userGroup = new UserGroup();
            userGroup.setGroup(group);
            userGroup.setUser(user);
            userGroup.setRole(Role.PARTICIPANT);
            userGroup.setDateJoined(new Date());
            userGroupRepository.save(userGroup);

            UserChat userChat = new UserChat();
            userChat.setActive(false);
            userChat.setUser(user);
            userChat.setChat(group.getChat());
            userChatRepository.save(userChat);
            return userGroup;
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public String removeUserFromGroup(Integer groupId,Integer userId){
        try{
            UserGroup userToRemove = userGroupRepository.findByUserGroup(userId,groupId);
            userGroupRepository.delete(userToRemove);
            return "User removed from group";
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public UserGroup changeRoleInGroup(Integer userGroupId){
        try{
            UserGroup userGroup = getUserGroup(userGroupId);
            if(userGroup.getRole() == Role.PARTICIPANT){
                userGroup.setRole(Role.ADMIN);
                userGroupRepository.save(userGroup);
            }
            return userGroup;
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}

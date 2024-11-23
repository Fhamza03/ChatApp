package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.UserGroup;
import com.fssm.ChatApp.Service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserGroupController {
    @Autowired
    UserGroupService userGroupService;
    @PostMapping("/addUserToGroup/{groupId}/{userId}")
    public UserGroup addUsertoGroup(@PathVariable Integer groupId,@PathVariable Integer userId){
        try{
            return userGroupService.addUserToGroup(groupId,userId);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @DeleteMapping("/removeUserFromGroup/{groupId}/{userId}")
    public String removeUserFromGroup(@PathVariable Integer groupId,@PathVariable Integer userId){
        try{
            userGroupService.removeUserFromGroup(groupId,userId);
            return "User removed from group";
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PutMapping("/changeRole/{userGroupId}")
    public UserGroup changeRoleInGroup(@PathVariable Integer userGroupId){
        try{
            return userGroupService.changeRoleInGroup(userGroupId);

        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}

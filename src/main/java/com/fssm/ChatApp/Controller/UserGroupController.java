package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.UserGroup;
import com.fssm.ChatApp.Service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/changeRole/{userId}/{groupId}")
    public UserGroup changeRoleInGroup(@PathVariable Integer userId, @PathVariable Integer groupId){
        try{
            return userGroupService.changeRoleInGroup(userId,groupId);

        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/AllGroups/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getGroupsByUserId(@PathVariable Integer userId) {
        List<Map<String, Object>> groups = userGroupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }
}

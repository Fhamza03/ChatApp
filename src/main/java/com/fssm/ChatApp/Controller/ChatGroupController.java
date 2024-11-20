package com.fssm.ChatApp.Controller;
import com.fssm.ChatApp.Model.ChatGroup;
import com.fssm.ChatApp.Service.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


    @RestController
    public class ChatGroupController {

        @Autowired
        private ChatGroupService chatGroupService;

        // Créer un groupe
        @PostMapping("/CreateGroupe")
        public ChatGroup createChatGroup(@RequestBody ChatGroup chatGroup) {
            try{
                return chatGroupService.createChatGroup(chatGroup);

            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }

        // Obtenir un groupe par ID
        @GetMapping("/getGroup/{groupId}")
        public ChatGroup getChatGroup(@PathVariable Integer groupId) {
            return chatGroupService.getChatGroup(groupId);
        }

        // Mettre à jour un groupe
        @PutMapping("/UpdateGroup")
        public ChatGroup updateChatGroup(@RequestBody ChatGroup chatGroup) {
            return chatGroupService.updateChatGroup(chatGroup);
        }

        // Supprimer un groupe
        @DeleteMapping("/deleteGroup/{groupId}")
        public String deleteChatGroup(@PathVariable Integer groupId) {
            chatGroupService.deleteChatGroup(groupId);
            return "Chat group deleted successfully";
        }

    }

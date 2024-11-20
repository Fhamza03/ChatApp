package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.ChatGroup;
import org.springframework.stereotype.Service;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.ChatGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
    public class ChatGroupService {


        @Autowired
        private ChatGroupRepository chatGroupRepository;

        // Créer un groupe de discussion
        public ChatGroup createChatGroup(ChatGroup chatGroup) {
            try {
                return chatGroupRepository.save(chatGroup);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create chat group: " + e.getMessage());
            }
        }

        // Obtenir un groupe de discussion par son ID
        public ChatGroup getChatGroup(Integer groupId) {
            Optional<ChatGroup> chatGroup = chatGroupRepository.findById(groupId);
            return chatGroup.orElse(null);
        }

        // Mettre à jour un groupe de discussion
        public ChatGroup updateChatGroup(ChatGroup chatGroup) {
            try {
                if (!chatGroupRepository.existsById(chatGroup.getGroupId())) {
                    throw new RuntimeException("Chat group not found with ID: " + chatGroup.getGroupId());
                }
                return chatGroupRepository.save(chatGroup);
            } catch (Exception e) {
                throw new RuntimeException("Failed to update chat group: " + e.getMessage());
            }
        }

        // Supprimer un groupe de discussion par son ID
        public void deleteChatGroup(Integer groupId) {
            try {
                if (!chatGroupRepository.existsById(groupId)) {
                    throw new RuntimeException("Chat group not found with ID: " + groupId);
                }
                chatGroupRepository.deleteById(groupId);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete chat group: " + e.getMessage());
            }
        }
    }




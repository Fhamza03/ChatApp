package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Chat;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatRepository extends JpaRepository<UserChat,Integer> {
//    Optional<UserChat> findByUserAndChat(User user, Chat chat);
    List<UserChat> findByChat(Chat chat);
}

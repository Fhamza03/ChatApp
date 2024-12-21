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
    @Query(value = "SELECT u.username, u.first_name, u.last_name, uc.user_id, uc.chat_id " +
            "FROM user_chat uc " +
            "JOIN chat c ON uc.chat_id = c.chat_id " +
            "JOIN user u ON uc.user_id = u.user_id " +
            "WHERE uc.chat_id IN ( " +
            "    SELECT uc.chat_id " +
            "    FROM user_chat uc " +
            "    WHERE uc.user_id = :userId " +
            ") " +
            "AND uc.user_id != :userId " +
            "AND c.chat_type = 'NORMAL'", nativeQuery = true)
    List<Object[]> findChatsByUserId(@Param("userId") Integer userId);
}

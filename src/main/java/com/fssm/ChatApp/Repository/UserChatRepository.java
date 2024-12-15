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
    @Query(value = "SELECT uf.user_id, uf.friend_id, uc.chat_id, c.chat_type, u.username " +
            "FROM user_friend uf " +
            "JOIN user_chat uc ON uf.user_id = uc.user_id " +
            "JOIN chat c ON uc.chat_id = c.chat_id " +
            "JOIN user u ON u.user_id = uf.friend_id " +
            "WHERE (uf.user_id = :userId OR uf.friend_id = :userId) " +
            "AND c.chat_type = 'NORMAL'", nativeQuery = true)
    List<Object[]> findChatsByUserId(@Param("userId") Integer userId);
}

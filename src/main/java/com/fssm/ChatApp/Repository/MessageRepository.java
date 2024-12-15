package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Message;
import com.fssm.ChatApp.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT um FROM Message um WHERE um.sender.userId = :senderId AND um.receiver.userId = :receiverId")
    List<Message> findBySenderReceiver(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId);

    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId")
    List<Message> findByChat(Integer chatId);

    @Query(value = "SELECT m.message_id, m.content, m.message_time, m.chat_id, m.receiver_id, m.sender_id, " +
            "sender.username AS sender_username, receiver.username AS receiver_username " +
            "FROM message m " +
            "JOIN user sender ON sender.user_id = m.sender_id " +
            "JOIN user receiver ON receiver.user_id = m.receiver_id " +
            "WHERE m.chat_id = :chatId", nativeQuery = true)
    List<Object[]> findMessagesWithUsernamesByChatId(Integer chatId);
}



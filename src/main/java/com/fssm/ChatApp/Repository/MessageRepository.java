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

}

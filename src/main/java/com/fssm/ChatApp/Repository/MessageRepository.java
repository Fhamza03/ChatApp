package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}

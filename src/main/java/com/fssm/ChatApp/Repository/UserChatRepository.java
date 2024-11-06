package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRepository extends JpaRepository<UserChat,Integer> {
}

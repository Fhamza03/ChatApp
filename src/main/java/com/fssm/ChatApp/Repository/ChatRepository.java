package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Integer> {
}

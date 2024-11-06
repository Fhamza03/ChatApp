package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGroupRepository extends JpaRepository<ChatGroup,Integer> {
}

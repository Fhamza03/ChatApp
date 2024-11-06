package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}

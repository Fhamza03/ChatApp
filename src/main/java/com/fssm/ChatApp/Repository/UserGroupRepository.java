package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
}

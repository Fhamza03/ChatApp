package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userId = :userId AND ug.group.groupId = :groupId")
    UserGroup findByUserGroup(Integer userId,Integer groupId);
}

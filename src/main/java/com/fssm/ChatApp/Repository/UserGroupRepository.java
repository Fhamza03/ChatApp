package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userId = :userId AND ug.group.groupId = :groupId")
    UserGroup findByUserGroup(Integer userId,Integer groupId);
    @Query("SELECT new map(cg.groupId as groupId, cg.name as name, c.chatId as chatId, ug.role as role, ug.dateJoined as dateJoined) " +
            "FROM UserGroup ug " +
            "JOIN ChatGroup cg ON ug.group.groupId = cg.groupId " +
            "JOIN Chat c ON cg.chat.chatId = c.chatId " +
            "WHERE ug.user.userId = :userId")
    List<Map<String, Object>> findGroupsByUserId(@Param("userId") Integer userId);

    // Corrected query: comparing userId from the User entity to groupId from the ChatGroup entity
    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userId = :userId AND ug.group.groupId = :groupId")
    UserGroup findByUserAndGroup(@Param("userId") Integer userId, @Param("groupId") Integer groupId);


}

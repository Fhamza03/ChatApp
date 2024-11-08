package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.LinkedList;
import java.util.List;

public interface UserFriendRepository extends JpaRepository<UserFriend,Integer> {
    // SQL query that return a list of friends for a user by his id
    @Query("SELECT uf FROM UserFriend uf WHERE uf.user.userId = :userId OR uf.friend.userId = :userId")
    List<UserFriend> findAllFriendshipsByUserId(Integer userId);
}

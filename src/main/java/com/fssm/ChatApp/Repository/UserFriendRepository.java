package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Model.UserFriend;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface UserFriendRepository extends JpaRepository<UserFriend,Integer> {
    // SQL query that return a list of friends for a user by his id
    @Query("SELECT u.userId, u.email, u.firstName, u.lastName, u.userName " +
            "FROM User u " +
            "JOIN UserFriend uf ON (uf.user.userId = u.userId OR uf.friend.userId = u.userId) " +
            "WHERE (uf.user.userId = :userId OR uf.friend.userId = :userId) " +
            "AND u.userId != :userId")
    List<Tuple> findFriendsByUserId(Integer userId);

}

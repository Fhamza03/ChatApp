package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByUserName(String userName);
    @Query("SELECT u.userId, u.firstName, u.lastName, u.userName FROM User u")
    List<Object[]> findAllUsersWithLimitedFields();

    @Query(value = """
        SELECT 
            u.user_id AS userId, 
            u.email AS email, 
            u.first_name AS firstName, 
            u.last_name AS lastName, 
            u.username AS username, 
            uf.status AS friendshipStatus
        FROM User u
        LEFT JOIN Friend_Request uf 
        ON u.user_id = uf.receiver_id AND uf.sender_id = :senderId
        WHERE u.user_id != :senderId
    """, nativeQuery = true)
    List<Object[]> findUsersWithStatus(@Param("senderId") Integer senderId);

    Optional<User> findByVerificationToken(String token);


}

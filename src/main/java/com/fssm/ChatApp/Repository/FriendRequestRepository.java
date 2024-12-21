package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Integer> {
    @Query("SELECT fr FROM FriendRequest fr WHERE fr.sender.id = :senderId AND fr.receiver.id = :receiverId")
    FriendRequest findRequestBySenderAndReceiver(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId);

    @Query("SELECT fr.friendRequestId AS friendRequestId, " +
            "fr.status AS status, " +
            "fr.receiver.userId AS receiverId, " +
            "fr.sender.userId AS senderId, " +
            "fr.sender.email AS senderEmail, " +
            "fr.sender.firstName AS senderFirstName, " +
            "fr.sender.lastName AS senderLastName, " +
            "fr.sender.userName AS senderUsername " +
            "FROM FriendRequest fr WHERE fr.receiver.userId = :receiverId")
    List<Object[]> findAllByReceiverId(@Param("receiverId") Integer receiverId);

}

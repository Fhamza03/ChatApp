package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Integer> {
}

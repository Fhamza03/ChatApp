package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Notification;
import com.fssm.ChatApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findBySender(User sender);

    List<Notification> findByReceiver(User receiver);
}

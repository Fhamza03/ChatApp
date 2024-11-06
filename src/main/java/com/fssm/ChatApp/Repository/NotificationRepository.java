package com.fssm.ChatApp.Repository;

import com.fssm.ChatApp.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}

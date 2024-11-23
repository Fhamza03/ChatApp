package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Model.Notification;
import com.fssm.ChatApp.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/getNotification/{notificationId}")
    public Notification getNotification(@PathVariable Integer notificationId){
        try{
            return notificationService.getNotification(notificationId);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/ReceivingNotifications/{receiverId}")
    public List<Notification> getAllReceivingNotifications(@PathVariable Integer receiverId){
        try{
            return notificationService.getAllReceiverNotifications(receiverId);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/SendingNotifications/{senderId}")
    public List<Notification> getAllSendingNotifications(@PathVariable Integer senderId){
        try{
            return notificationService.getAllSenderNotifications(senderId);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}

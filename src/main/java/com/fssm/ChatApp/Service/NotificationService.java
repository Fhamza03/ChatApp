package com.fssm.ChatApp.Service;

import com.fssm.ChatApp.Model.Notification;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.NotificationRepository;
import com.fssm.ChatApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserService userService;

    public Notification getNotification(Integer notificationId){
        try{
            Optional<Notification> notification = notificationRepository.findById(notificationId);
            return notification.orElse(null);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


    public List<Notification> getAllSenderNotifications(Integer senderId){
        try{
            User sender = userService.getUser(senderId);
            return notificationRepository.findBySender(sender);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    public List<Notification> getAllReceiverNotifications(Integer receiverId){
        try{
            User receiver = userService.getUser(receiverId);
            return notificationRepository.findByReceiver(receiver);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Notification createNotification(User sender,User receiver,String message){
        try{
            Notification notification = new Notification();
            notification.setSender(sender);
            notification.setReceiver(receiver);
            notification.setMessage(message);
            notification.setRead(false);
            notificationRepository.save(notification);
            return notification;
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Boolean updateReadNotification(Integer notificationId){
        try{
            Notification notification = getNotification(notificationId);
            if(notification.getRead() == false){
                notification.setRead(true);
                notificationRepository.save(notification);
            }
            return true;
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}

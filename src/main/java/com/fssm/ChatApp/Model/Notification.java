package com.fssm.ChatApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer notificationId;

    private String message;
    private Boolean isRead;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}

package com.fssm.ChatApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    private String content;

    private Date messageTime;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sender_id")  // specify sender_id column
    private User sender;

    // Receiver relationship - mapped to receiver_id column
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id")  // specify receiver_id column
    private User receiver;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

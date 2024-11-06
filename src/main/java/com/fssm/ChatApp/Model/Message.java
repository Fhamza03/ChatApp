package com.fssm.ChatApp.Model;

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
    @JoinColumn(name = "sender_id")  // specify sender_id column
    private User sender;

    // Receiver relationship - mapped to receiver_id column
    @ManyToOne
    @JoinColumn(name = "receiver_id")  // specify receiver_id column
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

}

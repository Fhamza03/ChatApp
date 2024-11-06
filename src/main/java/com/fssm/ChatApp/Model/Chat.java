package com.fssm.ChatApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Integer chatId;
    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
    @OneToOne(mappedBy = "chat")
    private ChatGroup chatGroup;
}

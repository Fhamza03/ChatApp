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
public class ChatGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups;
    @OneToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
}

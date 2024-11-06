package com.fssm.ChatApp.Model;

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
    private Boolean isRead = false;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}

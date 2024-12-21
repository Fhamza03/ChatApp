package com.fssm.ChatApp.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserFriend> friends;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserGroup> userGroups;
    @Column(name = "username")
    private String userName;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserChat> userChats;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Message> messages;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Notification> sentNotifications;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Notification> receivedNotifications;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore

    private List<FriendRequest> friendRequests;

    @Column(name = "verification_token", unique = true)
    private String verificationToken;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;
//    @Column(name = "enabled", nullable = false)
//    private boolean enabled = false;

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserFriend> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriend> friends) {
        this.friends = friends;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<UserChat> getUserChats() {
        return userChats;
    }

    public void setUserChats(List<UserChat> userChats) {
        this.userChats = userChats;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Notification> getSentNotifications() {
        return sentNotifications;
    }

    public void setSentNotifications(List<Notification> sentNotifications) {
        this.sentNotifications = sentNotifications;
    }

    public List<Notification> getReceivedNotifications() {
        return receivedNotifications;
    }

    public void setReceivedNotifications(List<Notification> receivedNotifications) {
        this.receivedNotifications = receivedNotifications;
    }

    public List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }
}

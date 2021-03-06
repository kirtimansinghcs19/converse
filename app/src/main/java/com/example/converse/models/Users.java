package com.example.converse.models;

public class Users {
    String profilePic, name, mail, password, userId, lastMessage, aboutUs;

    public Users(String profilePic, String name, String mail, String password, String userId, String lastMessage) {
        this.profilePic = profilePic;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public Users(String profilePic, String name, String mail, String password, String userId, String lastMessage, String aboutUs) {
        this.profilePic = profilePic;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.aboutUs = aboutUs;
    }

    public Users() {
    }


    // SignUp Constructor
    public Users(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }
}

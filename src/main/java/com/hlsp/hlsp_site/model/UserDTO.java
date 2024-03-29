package com.hlsp.hlsp_site.model;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDTO implements Serializable {
    private int userID;
    private String email;
    private String firstName;
    private String lastName;
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserDTO(){
        // userName="";
        // firstName="";
    }

    public UserDTO(String email){
        this.email=email;
    }

    public UserDTO(int userId, String email, String firstName, String lastName, String displayName){
        this.userID=userId;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.displayName=displayName;
    }

    public UserDTO userBean(int userId, String email, String firstName, String lastName, String displayName){
        return new UserDTO(userId, email, firstName, lastName, displayName);
    }
}

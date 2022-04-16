package com.example.finalproject;

import java.util.HashMap;

public class User{

    private String username;
    private String password;

    // IssueName -> Status
    private HashMap<String, String> issueHash = new HashMap<String, String>();
    // IssueName -> Description
    private HashMap<String, String> detailHash = new HashMap<String, String>();


    //Create user profile
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /** 
    * Adds an issue to the map of issues after submitting
    *
    * @param issueName name of the issue
    * @param issueStatus status of the issue
    */
    public void addIssue(String issueName, HashMap<String, String> issueHash){
        issueHash.put(issueName, "Pending");
    }

    /**
    * Clears all the input fields and returns to previous scene
    */
    public void cancelIssue(){
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public HashMap<String,String> getIssueHash() {
        return this.issueHash;
    }

    public HashMap<String,String> getDetailHash() {
        return this.detailHash;
    }
}

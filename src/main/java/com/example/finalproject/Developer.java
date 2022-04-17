package com.example.finalproject;

import java.util.*;

public class Developer extends User{

    
    // IssueName -> Status
    protected static HashMap<String, String> issueHash = new HashMap<String, String>();
    // IssueName -> Description
    protected static HashMap<String, String> detailHash = new HashMap<String, String>();
    //Create user profile and retrieve issueList
    public Developer(String username, String password) {
        super(username, password);
    }

    /**
     * Changes the status to assigned
     * 
     * @param issue Chosen issue to be opened
     * */
    public void openIssue(String issue){
        if(issueHash.get(issue).equals("Assigned"))
            issueHash.replace(issue, "Opened");
    }
    /**
     * Changes the status to rejected
     * 
     * @param issue Chosen issue to be rejected
     * */
    public void rejectIssue(String issue){
        if(issueHash.get(issue).equals("Assigned"))
            issueHash.replace(issue, "Rejected");
    }
    /**
     * Changes the status to opened
     * 
     * @param issue Chosen issue to be resolved
     * */
    public void resolveIssue(String issue){
        if(issueHash.get(issue).equals("Opened"))
            issueHash.replace(issue, "Resolved");
    }
}

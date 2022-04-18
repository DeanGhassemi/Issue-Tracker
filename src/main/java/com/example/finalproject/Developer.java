package com.example.finalproject;

import java.util.*;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class Developer extends User{

    
    // IssueName -> Status
    protected static HashMap<String, String> issueHash = new HashMap<String, String>();
    
    //Create user profile and retrieve issueList
    public Developer(String username, String password) {
        super(username, password);
    }
    public Developer() {
        super("developer", "123");
        Manager.userAccounts.put("developer~123", "developer");
    }
    /**
     * Changes the status to assigned
     * 
     * @param issue Chosen issue to be opened
     * */
    public void openIssue(String issue){
        if(issueHash.get(issue).equals("Assigned"))
            issueHash.replace(issue, "Opened");
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be opened! Must be assigned!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    /**
     * Changes the status to rejected
     * 
     * @param issue Chosen issue to be rejected
     * */
    public void rejectIssue(String issue){
        if(issueHash.get(issue).equals("Assigned"))
            issueHash.replace(issue, "Rejected");
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be rejected! Must be assigned!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    /**
     * Changes the status to opened
     * 
     * @param issue Chosen issue to be resolved
     * */
    public void resolveIssue(String issue){
        if(issueHash.get(issue).equals("Opened"))
            issueHash.replace(issue, "Resolved");
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be resolved! Must be assigned first!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
}

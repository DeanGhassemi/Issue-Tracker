package com.example.finalproject;
import java.io.FileWriter;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;


public class Manager extends Developer{

    /* Username ~ Password -> Role 
       Also needed to recreate the users after the program closes.
       To recreate, it works with userObjects to recreate the object
    */
    public static HashMap<String, String> userAccounts = new HashMap<String, String>();
    // Username -> Assigned Issue
    public static HashMap<String, String> devIssues = new HashMap<String, String>();
    // Username -> Object
    public static HashMap<String, User> userObjects = new HashMap<String, User>();

    //Create user profile
    public Manager(String username, String password) {
        super(username, password);
    }
    // First boot up of the program
    public Manager(){
        super("manager", "123");
        userAccounts.put("manager~123", "manager");
    }
    /** Assigns an issue to a dev without a task. If all devs have a task, it
     *  will add the task to a queue so when a developer resolves or rejects an
     *  issue, they will be assigned another task
     * 
     * @param issue issue to be assigned
     * 
     * Possibly never used
     */
    public void assignIssue(String issue){
        /** 
        boolean occupied = true;
        if(issueHash.get(issue).equals("New") ||
        issueHash.get(issue).equals("Rejected")){     
            // Going to be changed. Assigned by drop down menu
            for(String key: devs.keySet()){
               if(devs.get(key).equals("No task")){
                   devs.replace(key, issue);
                   occupied = false;
                   break;
               }
            }
        }
        */
    }
    /** Closes the issue
     * 
     * @param issue name of the issue
     */
    public void closeIssue(String issue){
        String status = issueHash.get(issue);
        if(status.equals("Validate") || status.equals("Rejected")){
            issueHash.replace(issue, "Closed");
        }
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be closed! Must first be validated or rejected");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    /** Validates the issue
     * 
     * @param issue name of the issue
     */
    public void validateIssue(String issue){
        String status = issueHash.get(issue);
        if(status.equals("Resolved")){
            issueHash.replace(issue, "Validate");
        }
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be validated! Must be resolved");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    /** Changes the status of the issue to "Failed".
     * 
     * @param issue name of the issue
     */
    public void failIssue(String issue){
        String status = issueHash.get(issue);
        if(status.equals("Resolved")){
            issueHash.replace(issue, "Fail");
        }
        else{
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Issues");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue cannot be failed! Must be resolved");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }
    }
    /** Creates a new instance of a specific user
     * 
     * @param typeOfUser User, Developer, Manager
     * @param username
     * @param password
     */
    public static boolean addUser(String typeOfUser, String username, String password){
        if(!userAccounts.containsKey(username)){
            if(typeOfUser.toLowerCase().equals("user")){
                userObjects.put(username, new User(username, password));
                userAccounts.put(username + "~" + password, typeOfUser);
            }
            else if(typeOfUser.toLowerCase().equals("developer")){
                userObjects.put(username, new Developer(username, password));
                userAccounts.put(username + "~" + password, typeOfUser);
                devIssues.put(username, "*");
            }
            else if(typeOfUser.toLowerCase().equals("manager")){
                userObjects.put(username, new Manager(username, password));
                userAccounts.put(username + "~" + password, typeOfUser);
            }
            else{
                return false;
            }
            userAccounts.put(username + "~" + password, typeOfUser);
            
            return true;
        }
        return false;
    }
}

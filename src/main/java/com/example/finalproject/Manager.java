package com.example.finalproject;
import java.util.*;


public class Manager extends Developer{

    // Username -> Password
    public static HashMap<String, String> userAccounts = new HashMap<String, String>();
    // Username -> Assigned Issue
    public static HashMap<String, String> devIssues = new HashMap<String, String>();
    // Username -> Object
    public static HashMap<String, User> users= new HashMap<String, User>();

    //Create user profile
    public Manager(String username, String password) {
        super(username, password);
    }
    // First boot up of the program
    public Manager(){
        super("manager", "123");
        userAccounts.put("manager", "123");
        users.put("manager", new Manager("manager", "123"));
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
     * @param issue issue to be closed
     */
    public void closeIssue(String issue){
        if(issueHash.get(issue).equals("Validate") ||
        issueHash.get(issue).equals("Rejected")){
            issueHash.replace(issue, "Closed");
        }
    }
    /**
     * 
     * @param issue
     */
    public void validateIssue(String issue){

    }
    /** Changes the status of the issue to "Failed".
     * 
     * @param issue TBD
     */
    public void failIssue(String issue){

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
                users.put(username, new User(username, password));
            }
            else if(typeOfUser.toLowerCase().equals("developer")){
                users.put(username, new Developer(username, password));

            }
            else if(typeOfUser.toLowerCase().equals("manager")){
                users.put(username, new Manager(username, password));
            }
            else{
                return false;
            }
            userAccounts.put(username, password);
            
            return true;
        }
        return false;


    }
}

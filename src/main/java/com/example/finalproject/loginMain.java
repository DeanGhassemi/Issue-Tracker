package com.example.finalproject;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//Storing data
import java.io.*;

//Adding users and queue for developers
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

public class loginMain extends Application {

    // Needed elements to create the Scene for the login page
    TextField userTextField;
    TextArea taDisplay = new TextArea();
    PasswordField passwordText;
    Button newIssue, issueList, logout;
    GridPane grid = new GridPane();
    Text header;

    //Elements for creating user
    TextField txtUsername = new TextField();
    TextField txtPassword = new TextField();
    TextField txtUserType = new TextField();

    //Elements for adding an Issue
    Label lblNewIssue = new Label("Issue Name:");
    TextField txtNewIssue = new TextField();

    // to know which user is logged in
    String loggedIn;

    //Storing Data
    FileWriter fw = null;

    public void getIssues() throws IOException {
        try{
            FileWriter fw = new FileWriter("issuesList.txt", true);
            fw.append(loggedIn + "," + txtNewIssue.getText() + "~New" + "\n");
            fw.close();
            Manager.userObjects.get(loggedIn).getIssues().put(txtNewIssue.getText(), "New");
            Developer.issueHash.put(txtNewIssue.getText(), "New");
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Added Issue");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue was successfully added!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            txtNewIssue.setText("");
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ERROR");
            a.setHeaderText("Something went wrong");
            a.setContentText("Try again please");
            a.show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    Stage stage = null;

    Scene loginScreen = null;
    Scene managerScreen = null;
    Scene developerScreen = null;
    Scene userScreen = null;

    //Actual Tools
    Scene theIssues = null;
    Scene managerIssuesList = null;
    Scene createUserScene = null;
    Scene assignIssueScene = null;

    @Override
    public void start(Stage primaryStage) {
        Manager admin = new Manager();

        Scanner scan=null;
        // Account information of users.
        try{
            File file = new File("useraccounts.txt");
            scan = new Scanner(file);
            while(scan.hasNext()){
                String nl = scan.nextLine();
                String[] keyval = nl.split(",");
                //username~password
                String info = keyval[0];
                //role
                String role = keyval[1];
                String[] userpass = info.split("~");
                String username = userpass[0];
                String password = userpass[1];
                Manager.addUser(role, username, password);
                Manager.userAccounts.put(username + "~" + password, role);
            }
        }catch(Exception e){
            System.out.println("Can't read file");
        }finally{
            if(scan != null) scan.close();
        }

        /*Developers and their assigned issue. If no issue, fill with
        "No Issue" */
        try{
            File file = new File("developerIssues.txt");
            scan = new Scanner(file);
            while(scan.hasNext()){
                String nl = scan.nextLine();
                String[] keyval = nl.split(",");
                //username
                String key = keyval[0];
                //assigned issue
                String val = keyval[1];
                Manager.devIssues.put(key, val);
            }
        }catch(Exception e){
            System.out.println("Can't read file");
        }finally{
            if(scan != null) scan.close();
        }

        try{
            File file = new File("issuesList.txt");
            scan = new Scanner(file);
            while(scan.hasNext()){
                String nl = scan.nextLine();
                // 
                String[] keyval = nl.split(",");
                //username (Key)
                String username = keyval[0];
                //issue~status
                String issuer = keyval[1];
                // 
                String[] userpass = issuer.split("~");
                String issue = userpass[0];
                String status = userpass[1];
                Developer.issueHash.put(issue, status);
                Manager.userObjects.get(username).getIssues().put(issue, status);
            }
        }catch(Exception e){
            System.out.println("Can't read file");
        }finally{
            if(scan != null) scan.close();
        }
        stage = primaryStage;

        managerScreen = getManagerScreen();
        developerScreen = getDeveloperScreen();
        userScreen = getUserScreen();
        loginScreen = getLoginScreen();

        theIssues = getNewIssue();
        managerIssuesList = getManagerIssuesList();
        createUserScene = getCreateUser();
        assignIssueScene = getAssignIssue();

        primaryStage.setResizable(false);
        stage.setScene(loginScreen);
        primaryStage.show();
    }
    public Scene switchScreens(Scene scene){stage.setScene(scene);
        return scene;
    }
    public Scene getLoginScreen(){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40, 25, 25, 25));

        Text sceneTitle = new Text("IssueTracker Sign-In");
        sceneTitle.setFont(Font.font("Thoma", FontWeight.SEMI_BOLD, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label lblPassword = new Label("Password:");
        grid.add(lblPassword, 0, 2);

        passwordText = new PasswordField();
        grid.add(passwordText, 1, 2);

        Button signIn = new Button("Sign in");
        HBox button = new HBox(10);
        button.setAlignment(Pos.BOTTOM_RIGHT);
        button.getChildren().add(signIn);
        grid.add(button, 1, 4);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);



        signIn.setOnAction(e -> users());

        // Calling other scenes from other classes
        stage.setTitle("Issue Tracker");
        loginScreen = new Scene(grid, 300, 275);

        return loginScreen;
    }
    public Scene getLogout(){
        stage.setTitle("Issue Tracker");
        return loginScreen;
    }

    public Scene users(){
        String username = userTextField.getText();
        String password = passwordText.getText();
        try {
            final Text error = new Text();
            //Checks userAccounts hash to see if it is a valid account
            if (Manager.userAccounts.containsKey(username + "~" + password)) {
                //Checks what kind of account it is
                if(Manager.userAccounts.get(username + "~" + password).equals("manager")){
                    stage.setTitle("Manager Screen");
                    switchScreens(managerScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setText("");
                }
                else if(Manager.userAccounts.get(username + "~" + password).equals("developer")){
                    stage.setTitle("Developer Screen");
                    switchScreens(developerScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setFill(Color.BLUE);
                    error.setText("");
                }
                else if(Manager.userAccounts.get(username + "~" + password).equals("user")){
                    stage.setTitle("User Screen");
                    switchScreens(userScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setText("");
                }
                loggedIn = username;
            }
            else {
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> error.setText(null));
                pause.play();
                grid.add(error, 1, 6);
                error.setFill(Color.FIREBRICK);
                error.setText("Wrong or empty credentials");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return null;
    }

    //Scenes and Methods for Managers

    public Scene getManagerScreen(){
        GridPane pane = new GridPane();
        GridPane fields = new GridPane();
        HBox theButtons = new HBox();
        HBox moreButtons = new HBox();
        HBox lonelyButton = new HBox();

        header = new Text("Manager Control");
        header.setFont(Font.font("Thoma", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        pane.setAlignment(Pos.CENTER);
        fields.setAlignment(Pos.CENTER);
        theButtons.setAlignment(Pos.CENTER);
        moreButtons.setAlignment(Pos.CENTER);
        lonelyButton.setAlignment(Pos.CENTER);

        newIssue = new Button("New Issue");
        newIssue.setPrefWidth(100);
        newIssue.setPrefHeight(30);

        issueList = new Button("Issues List");
        issueList.setPrefWidth(100);
        issueList.setPrefHeight(30);

        Button createUser = new Button("Create User");
        createUser.setPrefHeight(30);
        createUser.setPrefWidth(100);

        Button assignIssue = new Button("Assign Issue");
        createUser.setPrefHeight(30);
        createUser.setPrefWidth(100);

        fields.add(header, 0, 0);

        theButtons.getChildren().addAll(newIssue, issueList, createUser);
        theButtons.setSpacing(25);
        theButtons.setPadding(new Insets(20, 20, 20, 20));

        moreButtons.getChildren().addAll(assignIssue);
        moreButtons.setSpacing(25);
        moreButtons.setPadding(new Insets(20, 20, 20, 20));

        logout = new Button("Logout");
        lonelyButton.getChildren().add(logout);
        lonelyButton.setSpacing(20);
        lonelyButton.setPadding(new Insets(10, 20, 20, 20));

        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.add(fields, 0, 0);
        pane.add(theButtons, 0, 1);
        pane.add(moreButtons, 0, 2);
        pane.add(lonelyButton, 0, 3);

        logout.setOnAction(e -> switchScreens(getLogout()));
        newIssue.setOnAction(e -> switchScreens(theIssues));
        issueList.setOnAction(e -> switchScreens(managerIssuesList));
        createUser.setOnAction(e -> switchScreens(createUserScene));
        assignIssue.setOnAction(e -> switchScreens(assignIssueScene));

        stage.setTitle("Manager Screen");
        managerScreen = new Scene(pane, 400,200);
        return managerScreen;
    }
    public Scene getManagerIssuesList(){
        BorderPane pane= new BorderPane();
        BorderPane input = new BorderPane();
        GridPane fields = new GridPane();
        HBox buttons = new HBox();

        Button submit = new Button("Submit");
        Button returnBtn = new Button("Return");

        ListView<String> lvDisplay = new ListView<String>();
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(3);

        input.setBottom(buttons);
        pane.setLeft(lvDisplay);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(submit, returnBtn);
        pane.setBottom(buttons);

        returnBtn.setOnAction(e -> goBack());

        managerIssuesList = new Scene(pane, 400,200);
        return managerIssuesList;
    }

    /** Show the create user screen
     * 
     * @return create user scene
     */
    public Scene getCreateUser(){
        BorderPane pane= new BorderPane();
        BorderPane input = new BorderPane();
        GridPane fields = new GridPane();
        HBox buttons = new HBox();

        Button btnSubmit = new Button("Submit");
        Button returnBtn = new Button("Return");

        Label lblUserName = new Label("Username: ");
        Label lblPassword = new Label("Password: ");
        Label lblUserType = new Label("User Type: ");

        fields.setAlignment(Pos.CENTER);
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(3);
        fields.add(lblUserName, 0, 0);
        fields.add(lblPassword, 0, 1);
        fields.add(lblUserType, 0, 2);
        fields.add(txtUsername, 1, 0);
        fields.add(txtPassword, 1, 1);
        fields.add(txtUserType, 1, 2);


        input.setBottom(buttons);
        pane.setCenter(fields);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(btnSubmit, returnBtn);
        pane.setBottom(buttons);

        btnSubmit.setOnAction(e -> addUser());

        returnBtn.setOnAction(e -> goBack());

        createUserScene = new Scene(pane, 400,200);
        return createUserScene;
    }
    /** Creates and shows the scene of assignIssue
     * 
     * @return Scene of assignIssue
     */
    public Scene getAssignIssue(){
        BorderPane pane= new BorderPane();
        BorderPane input = new BorderPane();
        GridPane fields = new GridPane();
        HBox buttons = new HBox();

        Button btnSubmit = new Button("Submit");
        Button returnBtn = new Button("Return");

        Label lblDevs = new Label("Available Developers");
        Label lblIssues = new Label("Unassigned Issues");

        ArrayList<String> availableIssues = new ArrayList<String>();
        ArrayList<String> availableDevs = new ArrayList<String>();

        for(String i : Developer.issueHash.keySet()){
            if(Developer.issueHash.get(i).equals("New") || Developer.issueHash.get(i).equals("Rejected")){
                availableIssues.add(i);
            }
        }
        for(String i : Manager.devIssues.keySet()){
            if(Manager.devIssues.get(i).equals("*")){
                availableDevs.add(i);
            }
            try{
                FileWriter fw = new FileWriter("developerIssues.txt");
                fw.append("" + i + "," + Manager.devIssues.get(i) + "\n");
                fw.close();
            }catch(Exception e){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("ERROR");
                a.setHeaderText("Something went wrong");
                a.setContentText("Try again please");
                a.show();
            }
        }


        ObservableList<String> list = FXCollections.observableArrayList(availableDevs);
        ObservableList<String> list2 = FXCollections.observableArrayList(availableIssues);
        //Only show devs who do not have a task to complete
        ComboBox<String> cbDevs = new ComboBox<String>(list);
        //Only show issues that is not assigned to a dev
        ComboBox<String> cbIssues = new ComboBox<String>(list2);
        cbDevs.setItems(list);
        cbIssues.setItems(list2);

        fields.setAlignment(Pos.CENTER);
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(20);

        fields.add(lblDevs, 0, 0);
        fields.add(cbDevs, 0, 1);
        fields.add(lblIssues, 1, 0);
        fields.add(cbIssues, 1, 1);

        input.setBottom(buttons);
        pane.setCenter(fields);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(btnSubmit, returnBtn);
        pane.setBottom(buttons);

        btnSubmit.setOnAction(e -> assignIssue(cbDevs, cbIssues));

        returnBtn.setOnAction(e -> goBack());
        
        assignIssueScene = new Scene(pane, 400,200);
        return assignIssueScene;
    }
    public void assignIssue(ComboBox cbDevs, ComboBox cbIssues){
        String devUsername = (String) cbDevs.getValue();
        String issueName = (String) cbIssues.getValue();
        Manager.devIssues.replace(devUsername, issueName);
        try{
            FileWriter fw = new FileWriter("developerIssues.txt", true);
            fw.append("" + devUsername + "," + issueName + "\n");
            fw.close();
            FileWriter fw2 = new FileWriter("issuesList.txt");
            fw2.append(loggedIn + "," + issueName + "~Assigned" + "\n");
            fw2.close();
            Developer.issueHash.replace(issueName, "Assigned");
            //Creating a dialog
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Assigning Issue");
            ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.setContentText("Issue was successfully assigned!");
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            cbDevs.setValue(null);
            cbIssues.setValue(null);
        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ERROR");
            a.setHeaderText("Something went wrong");
            a.setContentText("Try again please");
            a.show();
        }
    }

    /** Create a new user to the system
     * 
     * @param username user's name
     * @param password user's password
     * @param userType type of user: user, dev or manager
     */
    public void addUser(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String userType = txtUserType.getText();
        if(!Manager.addUser(userType, username, password)){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("ERROR");
            a.setHeaderText("Cannot create user");
            a.setContentText("Username is taken or user type does not exist");
            a.show();
        }
        else{
            try{
                FileWriter fw = new FileWriter("useraccounts.txt", true);
                fw.append("\n" + username + "~" + password + "," + userType);
                fw.close();
                //Creating a dialog
                Dialog<String> dialog = new Dialog<String>();
                dialog.setTitle("Created user");
                ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                dialog.setContentText("User was successfully created!");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
                txtUsername.setText("");
                txtPassword.setText("");
                txtUserType.setText("");
            }catch(Exception e){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("ERROR");
                a.setHeaderText("Something went wrong");
                a.setContentText("Try again please");
                a.show();
            }

        }

    }
    //Scene for Developers

    public Scene getDeveloperScreen(){
        GridPane developerPane = new GridPane();
        GridPane developerField = new GridPane();
        HBox theButtons = new HBox();
        HBox lonelyButton = new HBox();

        developerPane.setAlignment(Pos.CENTER);
        developerField.setAlignment(Pos.CENTER);
        theButtons.setAlignment(Pos.CENTER);
        lonelyButton.setAlignment(Pos.CENTER);

        header = new Text("Developer Control");
        header.setFont(Font.font("Italic", FontWeight.NORMAL, 20));

        newIssue = new Button("New Issue");
        newIssue.setPrefWidth(100);
        newIssue.setPrefHeight(30);

        issueList = new Button("Issues List");
        issueList.setPrefWidth(100);
        issueList.setPrefHeight(30);

        developerField.add(header, 0, 0);

        theButtons.getChildren().addAll(newIssue, issueList);
        theButtons.setSpacing(25);
        theButtons.setPadding(new Insets(20, 20, 20, 20));

        logout = new Button("Logout");
        lonelyButton.getChildren().add(logout);
        lonelyButton.setSpacing(20);
        lonelyButton.setPadding(new Insets(10, 20, 20, 20));

        developerPane.setPadding(new Insets(20, 20, 20, 20));
        developerPane.add(developerField, 0, 0);
        developerPane.add(theButtons, 0, 1);
        developerPane.add(lonelyButton, 0, 2);

        logout.setOnAction(e -> switchScreens(getLogout()));
        newIssue.setOnAction(e -> switchScreens(theIssues));

        stage.setTitle("Developer Screen");
        developerScreen = new Scene(developerPane, 400,200);
        return developerScreen;
    }

    //Scene for users

    public Scene getUserScreen(){
        GridPane userPane = new GridPane();
        GridPane userField = new GridPane();
        HBox theButtons = new HBox();
        HBox lonelyButton = new HBox();

        userPane.setAlignment(Pos.CENTER);
        userField.setAlignment(Pos.CENTER);
        theButtons.setAlignment(Pos.CENTER);
        lonelyButton.setAlignment(Pos.CENTER);

        header = new Text("User Control");
        header.setFont(Font.font("Italic", FontWeight.NORMAL, 20));

        newIssue = new Button("New Issue");
        newIssue.setPrefWidth(100);
        newIssue.setPrefHeight(30);

        issueList = new Button("Issues List");
        issueList.setPrefWidth(100);
        issueList.setPrefHeight(30);

        userField.add(header, 0, 0);

        theButtons.getChildren().addAll(newIssue, issueList);
        theButtons.setSpacing(25);
        theButtons.setPadding(new Insets(20, 20, 20, 20));

        logout = new Button("Logout");
        lonelyButton.getChildren().add(logout);
        lonelyButton.setSpacing(20);
        lonelyButton.setPadding(new Insets(10, 20, 20, 20));

        userPane.setPadding(new Insets(20, 20, 20, 20));
        userPane.add(userField, 0, 0);
        userPane.add(theButtons, 0, 1);
        userPane.add(lonelyButton, 0, 2);

        logout.setOnAction(e -> switchScreens(getLogout()));
        newIssue.setOnAction(e -> switchScreens(theIssues));

        stage.setTitle("User Screen");
        userScreen = new Scene(userPane, 400,200);
        return userScreen;
    }

    public Scene getNewIssue(){
        BorderPane pane= new BorderPane();
        BorderPane input = new BorderPane();
        GridPane fields = new GridPane();
        HBox buttons = new HBox();

        Button submit = new Button("Submit");
        Button returning = new Button("Return");

        fields.setAlignment(Pos.CENTER);
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(3);
        fields.add(lblNewIssue, 0, 0);
        fields.add(txtNewIssue, 1, 0);

        input.setBottom(buttons);
        pane.setCenter(fields);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(submit, returning);
        pane.setBottom(buttons);

        returning.setOnAction(e -> {goBack();taDisplay.setText("");});
        submit.setOnAction(e -> {
            try {
                getIssues();
                Manager.userObjects.get(loggedIn).addIssue(taDisplay.getText());
                taDisplay.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        theIssues = new Scene(pane, 400,200);

        return theIssues;
    }

    public Scene goBack(){
        if(stage.getTitle().equals("Manager Screen")){
            switchScreens(managerScreen);
        }
        if(stage.getTitle().equals("Developer Screen")){
            switchScreens(developerScreen);
        }
        if(stage.getTitle().equals("User Screen")){
            switchScreens(userScreen);
        }

        return null;
    }

}
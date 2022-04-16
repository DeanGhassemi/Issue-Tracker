package com.example.finalproject;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class loginMain extends Application {

    // Needed elements to create the Scene for the login page
    TextField userTextField;
    TextArea taDisplay = new TextArea();
    PasswordField passwordText;
    Button newIssue, issueList, logout;
    GridPane grid = new GridPane();
    Text header;

    //Storing Data
    FileWriter fw = null; BufferedWriter bw = null; PrintWriter pw = null;

    public File getIssues() throws IOException {
        File issues = new File("issuesList.txt");
        try{
            fw = new FileWriter("issuesList.txt", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(taDisplay.getText());
            pw.flush();
        }finally{
            pw.close();
            bw.close();
            fw.close();
        }
        return issues;
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

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        managerScreen = getManagerScreen();
        developerScreen = getDeveloperScreen();
        userScreen = getUserScreen();
        loginScreen = getLoginScreen();

        theIssues = getNewIssue();
        managerIssuesList = getManagerIssuesList();

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

        Text sceneTitle = new Text("IssueTracker by JD");
        sceneTitle.setFont(Font.font("Thoma", FontWeight.SEMI_BOLD, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User:");
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label password = new Label("Password:");
        grid.add(password, 0, 2);

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
        while(true) {
            try {
                final Text error = new Text();
                if (userTextField.getText().equals("Manager") && passwordText.getText().equals(("123"))) {
                    stage.setTitle("Manager Screen");
                    switchScreens(managerScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setText("");
                }
                if (userTextField.getText().equals("User") && passwordText.getText().equals("")) {
                    stage.setTitle("User Screen");
                    switchScreens(userScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setText("");
                }
                if (userTextField.getText().equals("Developer") && passwordText.getText().equals("")) {
                    stage.setTitle("Developer Screen");
                    switchScreens(developerScreen);
                    userTextField.setText("");
                    passwordText.setText("");
                    grid.add(error, 1, 6);
                    error.setFill(Color.BLUE);
                    error.setText("");
                } else {
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
    }

    //Scene for Managers

    public Scene getManagerScreen(){
        GridPane pane = new GridPane();
        GridPane fields = new GridPane();
        HBox theButtons = new HBox();
        HBox lonelyButton = new HBox();

        header = new Text("Manager Control");
        header.setFont(Font.font("Thoma", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        pane.setAlignment(Pos.CENTER);
        fields.setAlignment(Pos.CENTER);
        theButtons.setAlignment(Pos.CENTER);
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

        fields.add(header, 0, 0);

        theButtons.getChildren().addAll(newIssue, issueList, createUser);
        theButtons.setSpacing(25);
        theButtons.setPadding(new Insets(20, 20, 20, 20));

        logout = new Button("Logout");
        lonelyButton.getChildren().add(logout);
        lonelyButton.setSpacing(20);
        lonelyButton.setPadding(new Insets(10, 20, 20, 20));

        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.add(fields, 0, 0);
        pane.add(theButtons, 0, 1);
        pane.add(lonelyButton, 0, 2);

        logout.setOnAction(e -> switchScreens(getLogout()));
        newIssue.setOnAction(e -> switchScreens(theIssues));
        issueList.setOnAction(e -> switchScreens(managerIssuesList));

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

        TextArea taDisplay = new TextArea();
        taDisplay.setPrefWidth(200);
        taDisplay.setPrefHeight(100);
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(3);

        input.setBottom(buttons);
        pane.setCenter(taDisplay);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(submit, returnBtn);
        pane.setBottom(buttons);

        returnBtn.setOnAction(e -> goBack());

        managerIssuesList = new Scene(pane, 400,200);
        return managerIssuesList;
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

        taDisplay.setPrefWidth(200);
        taDisplay.setPrefHeight(100);
        fields.setPadding(new Insets(5));
        fields.setVgap(3);
        fields.setHgap(3);

        input.setBottom(buttons);
        pane.setCenter(taDisplay);
        buttons.setPadding(new Insets(5));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(submit, returning);
        pane.setBottom(buttons);

        returning.setOnAction(e -> {goBack();taDisplay.setText("");});
        submit.setOnAction(e -> {
            try {
                getIssues();
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
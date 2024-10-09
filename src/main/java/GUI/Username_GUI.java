package GUI;

import database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import util.SkillLevel;
import util.User;

public class Username_GUI extends Application {

    private static Map<String, User> database = new HashMap<>();
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Authentication");

    
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(10));
        loginPane.setVgap(8);
        loginPane.setHgap(10);

        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Button nextButton = new Button("Next");

        loginPane.add(userLabel, 0, 0);
        loginPane.add(usernameField, 1, 0);
        loginPane.add(nextButton, 1, 1);

        Scene loginScene = new Scene(loginPane, 300, 150);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        nextButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (Database.isEmpty()) {
              
                showSignupPage(primaryStage, username);
            } else if (Database.hasUser(username)) {
               
                showSigninPage(primaryStage, username);
            } else {
                showAlert("Username not found. Please try again.");
            }
        });
    }

  
    private void showSignupPage(Stage primaryStage, String username) {
        GridPane signupPane = new GridPane();
        signupPane.setPadding(new Insets(10));
        signupPane.setVgap(8);
        signupPane.setHgap(10);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label skillLabel = new Label("Skill Level:");
        ComboBox<String> skillBox = new ComboBox<>();
        skillBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");

        Button signupButton = new Button("Sign Up");

        signupPane.add(firstNameLabel, 0, 0);
        signupPane.add(firstNameField, 1, 0);
        signupPane.add(lastNameLabel, 0, 1);
        signupPane.add(lastNameField, 1, 1);
        signupPane.add(emailLabel, 0, 2);
        signupPane.add(emailField, 1, 2);
        signupPane.add(passwordLabel, 0, 3);
        signupPane.add(passwordField, 1, 3);
        signupPane.add(skillLabel, 0, 4);
        signupPane.add(skillBox, 1, 4);
        signupPane.add(signupButton, 1, 5);

        Scene signupScene = new Scene(signupPane, 400, 250);
        primaryStage.setScene(signupScene);

        signupButton.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            char[] password = passwordField.getText().toCharArray();
            String skillLevel = skillBox.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || skillLevel == null) {
                showAlert("Please fill out all fields.");
            } else {
                boolean isAdmin = Database.isEmpty(); // First user becomes admin
                User newUser = new User(username, email, password, skillLevel, isAdmin);
                Database.addUser(newUser);
                showAlert("Sign up successful! You are now registered.");
                showSigninPage(primaryStage, email); // Redirect to login page
            }
        });
    }

   
    private void showSigninPage(Stage primaryStage, String username) {
        GridPane signinPane = new GridPane();
        signinPane.setPadding(new Insets(10));
        signinPane.setVgap(8);
        signinPane.setHgap(10);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button signinButton = new Button("Sign In");

        signinPane.add(passwordLabel, 0, 0);
        signinPane.add(passwordField, 1, 0);
        signinPane.add(signinButton, 1, 1);

        Scene signinScene = new Scene(signinPane, 300, 150);
        primaryStage.setScene(signinScene);

        signinButton.setOnAction(e -> {
            String password = passwordField.getText();
            User user = Database.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                showAlert("Welcome " + user.getFirstName() + "! You are now logged in.");
               
            } else {
                showAlert("Incorrect password. Please try again.");
            }
        });
    }

  
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

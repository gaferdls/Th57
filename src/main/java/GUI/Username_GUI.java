package GUI;

import database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

        Label userLabel = new Label("Email:");
        TextField emailField = new TextField();
        Button nextButton = new Button("Next");

        loginPane.add(userLabel, 0, 0);
        loginPane.add(emailField, 1, 0);
        loginPane.add(nextButton, 1, 1);

        Scene loginScene = new Scene(loginPane, 300, 150);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        nextButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            if (Database.hasUser(email)) {
               
                showSigninPage(primaryStage, email);
            } else {
                showSignupPage(primaryStage, email);
            }
        });
    }

  
    private void showSignupPage(Stage primaryStage, String email) {
        GridPane signupPane = new GridPane();
        signupPane.setPadding(new Insets(10));
        signupPane.setVgap(8);
        signupPane.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label preferredNameLabel = new Label("Preferred Name:");
        TextField preferredNameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label passwordAgainLabel = new Label("Re-enter Password:");
        PasswordField passwordAgainField = new PasswordField();
        Label skillLabel = new Label("Skill Level:");
        ComboBox<String> skillBox = new ComboBox<>();
        skillBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");

        Button signupButton = new Button("Sign Up");

        signupPane.add(usernameLabel, 0, 0);
        signupPane.add(usernameField, 1, 0);

        signupPane.add(firstNameLabel, 0, 1);
        signupPane.add(firstNameField, 1, 1);
        signupPane.add(middleNameLabel, 0, 2);
        signupPane.add(middleNameField, 1, 2);
        signupPane.add(lastNameLabel, 0, 3);
        signupPane.add(lastNameField, 1, 3);
        signupPane.add(preferredNameLabel, 0, 4);
        signupPane.add(preferredNameField, 1, 4);

        signupPane.add(passwordLabel, 0, 5);
        signupPane.add(passwordField, 1, 5);
        signupPane.add(passwordAgainLabel, 0, 6);
        signupPane.add(passwordAgainField, 1, 6);
        signupPane.add(skillLabel, 0, 8);
        signupPane.add(skillBox, 1, 8);
        signupPane.add(signupButton, 1, 9);

        Scene signupScene = new Scene(signupPane, 400, 400);
        primaryStage.setScene(signupScene);

        signupButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getText().trim().toCharArray();
            char[] passwordAgain = passwordAgainField.getText().trim().toCharArray();
            String skillLevel = skillBox.getValue();

            String firstName = firstNameField.getText().trim();
            String middleName = middleNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String preferredName = preferredNameField.getText().trim();

            if (username.isEmpty() || password.length == 0 || passwordAgain.length == 0 || skillLevel == null) {
                showAlert("Please fill out all fields.");
            } else if (!Arrays.toString(password).equals(Arrays.toString(passwordAgain))) {
                showAlert("Passwords do not match!");
            }

            else {
                boolean isAdmin = Database.isEmpty(); // First user becomes admin
                System.out.println("empty: " + isAdmin);
                User newUser = new User(username, firstName, middleName, lastName, preferredName, email, password, false, null, skillLevel, isAdmin, false, false);
                Database.addUser(newUser);
                showAlert("Sign up successful! You are now registered.");
                showSigninPage(primaryStage, email); // Redirect to login page
            }
        });
    }

   
    private void showSigninPage(Stage primaryStage, String email) {
        GridPane signinPane = new GridPane();
        signinPane.setPadding(new Insets(10));
        signinPane.setVgap(8);
        signinPane.setHgap(10);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button signinButton = new Button("Sign In");
        Button cancelButton = new Button("Cancel");

        signinPane.add(passwordLabel, 0, 0);
        signinPane.add(passwordField, 1, 0);
        signinPane.add(signinButton, 1, 1);
        signinPane.add(cancelButton, 0, 3);

        Scene signinScene = new Scene(signinPane, 300, 150);
        primaryStage.setScene(signinScene);

        signinButton.setOnAction(e -> {
            char[] password = passwordField.getText().toCharArray();
            User user = Database.findUserByEmail(email);
            if (user != null && Arrays.equals(user.getPassword(), password)) {
                showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                showRolePage(primaryStage, user);
            } else {
                showAlert("Incorrect password. Please try again.");
            }
        });

        cancelButton.setOnAction(e -> {
            start(primaryStage);
        });
    }

  
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showRolePage(Stage primaryStage, User user) {

        if (!user.isAdmin() && !user.isStudent() && !user.isInstructor()) {
            showAlert("You do not have any roles, please contact your administrator");
        }

        GridPane rolePane = new GridPane();
        rolePane.setPadding(new Insets(10));
        rolePane.setVgap(8);
        rolePane.setHgap(10);

        Label info = new Label("Please select how you want to log in");
        Button adminButton = new Button("Admin");
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
        Button logoutButton = new Button("Log Out");

        rolePane.add(info, 0, 0);
        if (user.isAdmin()) rolePane.add(adminButton, 0, 1);
        if (user.isStudent()) rolePane.add(studentButton, 0, 2);
        if (user.isInstructor()) rolePane.add(instructorButton, 0, 3);
        rolePane.add(logoutButton, 0, 4);


        Scene roleScene = new Scene(rolePane, 300, 300);
        primaryStage.setScene(roleScene);

        adminButton.setOnAction(e -> {
            // TODO: go to admin page
        });

        studentButton.setOnAction(e -> {
            // TODO: go to student page
        });

        instructorButton.setOnAction(e -> {
            // TODO: go to instructor page
        });

        logoutButton.setOnAction(e -> {
            start(primaryStage);
        });

    }

}

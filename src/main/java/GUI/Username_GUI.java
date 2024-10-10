package GUI;

import database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import util.User;

public class Username_GUI extends Application {

    
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
        Label passwordLabel = new Label("Password:");
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");

        loginPane.add(userLabel, 0, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(emailField, 1, 0);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);
        loginPane.add(signUpButton, 1, 3);

        Scene loginScene = new Scene(loginPane, 300, 150);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            char[] password = passwordField.getText().toCharArray();
            if (Database.hasUser(email)) {
                showSigninPage(primaryStage, email, password);
            } else {
                showAlert("Username not Found");
                start(primaryStage);
                //showSignupPage(primaryStage, email);
            }
        });
        signUpButton.setOnAction(e -> {
            showSetUpPage(primaryStage);
        });
    }

    private void showSetUpPage(Stage primaryStage) {
        GridPane setUpPane = new GridPane();
        setUpPane.setPadding(new Insets(10));
        setUpPane.setVgap(8);
        setUpPane.setHgap(10);

        Label usernameLabel = new Label("Username: ");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Label passwordAgainLabel = new Label("Re-enter Password: ");
        TextField passwordAgainField = new TextField();
        Button loginButton = new Button("SignUp");

        setUpPane.add(usernameLabel, 0, 0);
        setUpPane.add(usernameField, 1, 0);
        setUpPane.add(passwordLabel, 0, 1);
        setUpPane.add(passwordField, 1, 1);
        setUpPane.add(passwordAgainLabel, 0, 2);
        setUpPane.add(passwordAgainField, 1, 2);
        setUpPane.add(loginButton, 1, 3);

        Scene setUpScene = new Scene(setUpPane, 400, 400);
        primaryStage.setScene(setUpScene);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            char[] password = passwordField.getText().trim().toCharArray();
            char[] passwordAgain = passwordAgainField.getText().trim().toCharArray();
            if (username.isEmpty() || password.length == 0 || passwordAgain.length == 0 ) {
                showAlert("Please fill out all fields.");
            } else if (!Arrays.toString(password).equals(Arrays.toString(passwordAgain))) {
                showAlert("Passwords do not match!");
            }
            else {
                boolean isAdmin = Database.isEmpty(); // First user becomes admin
                System.out.println("empty: " + isAdmin);
                User newUser = new User(username, null, null, null, null, null, password, false, null, null, isAdmin, false, false);
                System.out.println(newUser.toString());;
                Database.addUser(newUser);
                showAlert("Sign up successful! You are now registered.");
                start(primaryStage); // Redirect to login page
            }

        });
    }
    private void finishSetUP(Stage primaryStage, User user) {
        GridPane finishSetUpPane = new GridPane();
        finishSetUpPane.setPadding(new Insets(10));
        finishSetUpPane.setVgap(8);
        finishSetUpPane.setHgap(10);
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label preferredNameLabel = new Label("Preferred Name:");
        TextField preferredNameField = new TextField();
        Label skillLabel = new Label("Skill Level:");
        ComboBox<String> skillBox = new ComboBox<>();
        int i = 0;
        if (user.getEmail() == null) {
            i++;
            finishSetUpPane.add(emailLabel, 0, i);
            finishSetUpPane.add(emailField, 1, 0);
        }
        if (user.getFirstName() == null){
            i++;
            finishSetUpPane.add(firstNameLabel, 0, i);
            finishSetUpPane.add(firstNameField, 1, i);
        }
        if(user.getMiddleName() == null){
            i++;
            finishSetUpPane.add(middleNameLabel, 0, i);
            finishSetUpPane.add(middleNameField, 1, i);
        }
        if(user.getLastName() == null){
            i++;
            finishSetUpPane.add(lastNameLabel, 0, i);
            finishSetUpPane.add(lastNameField, 1, i);
        }
        if(user.getPreferredName() == null){
            i++;
            finishSetUpPane.add(preferredNameLabel, 0, i);
            finishSetUpPane.add(preferredNameField, 1, i);
        }
        if(user.getSkillLevel() == null){
            i++;
            skillBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
            finishSetUpPane.add(skillLabel, 0, i);
            finishSetUpPane.add(skillBox, 1, i);
        }

        Button finishUpButton = new Button("Next");
        finishSetUpPane.add(finishUpButton, 1, i+1);

        Scene signupScene = new Scene(finishSetUpPane, 400, 400);
        primaryStage.setScene(signupScene);

        finishUpButton.setOnAction(e -> {
            if(user.getSkillLevel() == null) {
                user.setSkillLevel(skillBox.getValue());
            }
            if(user.getEmail() == null) {
                user.setEmail(emailField.getText());
            }
            if(user.getFirstName() == null) {
                user.setFirstName(firstNameField.getText());
            }
            if(user.getMiddleName() == null) {
                user.setMiddleName(middleNameField.getText());
            }
            if(user.getLastName() == null) {
                user.setLastName(lastNameField.getText());
            }
            if(user.getPreferredName() == null) {
                user.setPreferredName(preferredNameField.getText());
            }

            Database.updateUser(user);
        });

    }

   
    private void showSigninPage(Stage primaryStage, String username, char[] password) {
        User user = Database.findUserByUsername(username);
            if (user != null && Arrays.equals(user.getPassword(), password)) {
                if(user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getSkillLevel() == null) {
                    showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                    finishSetUP(primaryStage, user);
                }else{
                    showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                }
                showRolePage(primaryStage, user);
            } else {
                showAlert("Incorrect password. Please try again.");
            }
    }

  
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAdminPage(Stage primaryStage) {
        GridPane adminPane = new GridPane();
        adminPane.setPadding(new Insets(10));
        adminPane.setVgap(8);
        adminPane.setHgap(10);
        Button inviteButton = new Button("Invite");
        Button resetButton = new Button("Reset");
        Button deleteButton = new Button("Delete");
        Button listButton = new Button("List");
        Button rolesButton = new Button("Roles");
        Button logoutButton = new Button("Logout");
        Label spacer = new Label(" ");
        adminPane.add(spacer, 0, 0);
        adminPane.add(inviteButton, 0, 1);

        adminPane.add(resetButton, 0, 3);

        adminPane.add(deleteButton, 0, 5);

        adminPane.add(listButton, 0, 7);

        adminPane.add(rolesButton, 0, 9);

        adminPane.add(logoutButton, 0, 11);

        Scene setUpScene = new Scene(adminPane, 400, 400);
        primaryStage.setScene(setUpScene);

        inviteButton.setOnAction(e -> {
           //Invite button
        });
        resetButton.setOnAction(e -> {
           //Reset button
        });
        deleteButton.setOnAction(e -> {
           //delete button
        });
        listButton.setOnAction(e -> {
           //list button
        });
        rolesButton.setOnAction(e -> {
            //roles button
        });
        logoutButton.setOnAction(e -> {
            //logout button
            start(primaryStage);
        });

    }

    private void showRolePage(Stage primaryStage, User user) {

        if (user.isAdmin()){
            showAdminPage(primaryStage);
        } else if(!user.isAdmin() && !user.isStudent() && !user.isInstructor()){
            showAlert("You do not have any roles, please contact your administrator");
            showAdminPage(primaryStage);
        }

       /* GridPane rolePane = new GridPane();
        rolePane.setPadding(new Insets(10));
        rolePane.setVgap(8);
        rolePane.setHgap(10);

        //Label info = new Label("Please select how you want to log in");
        Button adminButton = new Button("Admin");
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
        Button logoutButton = new Button("Log Out");

        //rolePane.add(info, 0, 0);
        if (user.isAdmin()) rolePane.add(adminButton, 0, 1);
        if (user.isStudent()) rolePane.add(studentButton, 0, 2);
        if (user.isInstructor()) rolePane.add(instructorButton, 0, 3);
        rolePane.add(logoutButton, 0, 4);


        Scene roleScene = new Scene(rolePane, 300, 300);
        primaryStage.setScene(roleScene);

        adminButton.setOnAction(e -> {
            // TODO: go to admin page
            AdminPage admin = new AdminPage();
            admin.initialize();
            admin.show(primaryStage);
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
        */
    }

}

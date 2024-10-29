package GUI;

import database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
            } else {
                if(Database.isEmpty()){
                    User newUser = new User(username, null, null, null, null, null, password, false, null, null, true, false, false);
                    Database.addUser(newUser);
                    showAlert("Sign up successful! You are now registered.");
                    start(primaryStage);
                }else{
                    User newUser = new User(username, null, null, null, null, null, password, false, null, null, false, true, false);
                    Database.addUser(newUser);
                    showAlert("Sign up successful! You are now registered.");
                    start(primaryStage);
                }
            }
        });
    }

    private void showSigninPage(Stage primaryStage, String username, char[] password) {
        User user = Database.findUserByUsername(username);
        if (user != null && Arrays.equals(user.getPassword(), password)) {
            if(user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getSkillLevel() == null) {
                showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                finishSetUp(primaryStage, user);
            }else{
                showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
            }
            showRolePage(primaryStage, user);
        } else {
            showAlert("Incorrect password. Please try again.");
        }
    }

    private void finishSetUp(Stage primaryStage, User user) {
        GridPane finishSetUpPane = new GridPane();
        finishSetUpPane.setPadding(new Insets(10));
        finishSetUpPane.setVgap(8);
        finishSetUpPane.setHgap(10);

        // Labels and TextFields for user data
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(user.getEmail());

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField(user.getFirstName());

        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField(user.getMiddleName());

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField(user.getLastName());

        Label preferredNameLabel = new Label("Preferred Name:");
        TextField preferredNameField = new TextField(user.getPreferredName());

        Label skillLabel = new Label("Skill Level:");
        ComboBox<String> skillBox = new ComboBox<>();
        skillBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
        skillBox.setValue(user.getSkillLevel());

        // Adding components to GridPane
        finishSetUpPane.add(emailLabel, 0, 0);
        finishSetUpPane.add(emailField, 1, 0);

        finishSetUpPane.add(firstNameLabel, 0, 1);
        finishSetUpPane.add(firstNameField, 1, 1);

        finishSetUpPane.add(middleNameLabel, 0, 2);
        finishSetUpPane.add(middleNameField, 1, 2);

        finishSetUpPane.add(lastNameLabel, 0, 3);
        finishSetUpPane.add(lastNameField, 1, 3);

        finishSetUpPane.add(preferredNameLabel, 0, 4);
        finishSetUpPane.add(preferredNameField, 1, 4);

        finishSetUpPane.add(skillLabel, 0, 5);
        finishSetUpPane.add(skillBox, 1, 5);

        Button finishUpButton = new Button("Next");
        finishSetUpPane.add(finishUpButton, 1, 6);

        Scene signupScene = new Scene(finishSetUpPane, 400, 400);
        primaryStage.setScene(signupScene);

        finishUpButton.setOnAction(e -> {
            if (validateInput(emailField, firstNameField, middleNameField, lastNameField, preferredNameField, skillBox)) {
                user.setEmail(emailField.getText());
                user.setFirstName(firstNameField.getText());
                user.setMiddleName(middleNameField.getText());
                user.setLastName(lastNameField.getText());
                user.setPreferredName(preferredNameField.getText());
                user.setSkillLevel(skillBox.getValue());

                boolean updated = Database.updateUser(user);
                if (updated) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "User successfully updated!");
                    successAlert.showAndWait();
                } else {
                    Alert failureAlert = new Alert(Alert.AlertType.ERROR, "Failed to update user. Please check your information and try again.");
                    failureAlert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled and a skill level selected.");
                alert.showAndWait();
            }
        });

    }

    private boolean validateInput(TextField emailField, TextField firstNameField, TextField middleNameField, TextField lastNameField, TextField preferredNameField, ComboBox<String> skillBox) {
        return !emailField.getText().trim().isEmpty() &&
                !firstNameField.getText().trim().isEmpty() &&
                !middleNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() &&
                !preferredNameField.getText().trim().isEmpty() &&
                skillBox.getValue() != null;
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
        Button articlesButton = new Button("Articles");
        Button inviteButton = new Button("Invite");
        Button resetButton = new Button("Reset");
        Button deleteButton = new Button("Delete");
        Button listButton = new Button("List");
        Button rolesButton = new Button("Roles");
        Button logoutButton = new Button("Logout");
        Label spacer = new Label(" ");
        adminPane.add(spacer, 0, 0);
        adminPane.add(articlesButton, 0, 0);
        adminPane.add(inviteButton, 1, 0);
        adminPane.add(resetButton, 0, 1);
        adminPane.add(deleteButton, 1, 1);
        adminPane.add(listButton, 0, 2);
        adminPane.add(rolesButton, 1, 2);
        adminPane.add(logoutButton, 0, 3, 2, 1);
        GridPane.setHgrow(articlesButton, Priority.ALWAYS);
        GridPane.setHgrow(inviteButton, Priority.ALWAYS);
        GridPane.setHgrow(resetButton, Priority.ALWAYS);
        GridPane.setHgrow(deleteButton, Priority.ALWAYS);
        GridPane.setHgrow(listButton, Priority.ALWAYS);
        GridPane.setHgrow(rolesButton, Priority.ALWAYS);
        GridPane.setHgrow(logoutButton, Priority.ALWAYS);
        articlesButton.setMaxWidth(Double.MAX_VALUE);
        inviteButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        listButton.setMaxWidth(Double.MAX_VALUE);
        rolesButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);
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

    private void showInstructorPage(Stage primaryStage, User user) {
        GridPane instructorPane = new GridPane();
        instructorPane.setPadding(new Insets(10));
        instructorPane.setVgap(8);
        instructorPane.setHgap(10);
        Button createArticlesButton = new Button("Create Article");
        Button updateArticleButton = new Button("Update Article");
        Button deleteArticleButton = new Button("Delete Article");
        Button listArticlesButton = new Button("List Articles");
        Button logoutButton = new Button("Logout");
        instructorPane.add(createArticlesButton, 0, 1); // First row
        instructorPane.add(updateArticleButton, 0, 2); // Second row
        instructorPane.add(deleteArticleButton, 0, 3); // Third row
        instructorPane.add(listArticlesButton, 0, 4); // Fourth row
        instructorPane.add(logoutButton, 0, 5);       // Fifth row, span optional

        // Configure buttons to expand to fill space
        createArticlesButton.setMaxWidth(Double.MAX_VALUE);
        updateArticleButton.setMaxWidth(Double.MAX_VALUE);
        deleteArticleButton.setMaxWidth(Double.MAX_VALUE);
        listArticlesButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        GridPane.setHgrow(createArticlesButton, Priority.ALWAYS);
        GridPane.setHgrow(updateArticleButton, Priority.ALWAYS);
        GridPane.setHgrow(deleteArticleButton, Priority.ALWAYS);
        GridPane.setHgrow(listArticlesButton, Priority.ALWAYS);
        GridPane.setHgrow(logoutButton, Priority.ALWAYS);
        Scene instructorScene = new Scene(instructorPane, 400, 400);
        primaryStage.setScene(instructorScene);
        createArticlesButton.setOnAction(e -> {
            //create article
        });
        updateArticleButton.setOnAction(e -> {
            //update article
        });
        deleteArticleButton.setOnAction(e -> {
            //delete article
        });
        listArticlesButton.setOnAction(e -> {
            //list all articles
        });
        logoutButton.setOnAction(e -> {
            start(primaryStage);
        });
    }

    private void showStudentPage(Stage primaryStage, User user) {
        GridPane studentPane = new GridPane();
        studentPane.setPadding(new Insets(10));
        studentPane.setVgap(8);
        studentPane.setHgap(10);
        Button logoutButton = new Button("Logout");
        studentPane.add(logoutButton, 0, 0);
        Scene studentScene = new Scene(studentPane, 400, 400);
        primaryStage.setScene(studentScene);
        logoutButton.setOnAction(e -> {
            start(primaryStage);
        });
    }

    private void showResetPasswordPage(Stage primaryStage, User user) {
        GridPane resetPasswordPane = new GridPane();
        resetPasswordPane.setPadding(new Insets(10));
        resetPasswordPane.setVgap(8);
        resetPasswordPane.setHgap(10);
        Label spacer = new Label(" ");
        Label newPasswordLabel = new Label("New Password");
        TextField newPasswordField = new TextField();
        Label confirmPasswordLabel = new Label("Confirm Password");
        TextField confirmPasswordField = new TextField();
        Button newPasswordButton = new Button("Continue");
        resetPasswordPane.add(newPasswordLabel, 0, 0);
        resetPasswordPane.add(newPasswordField, 0, 1);
        resetPasswordPane.add(confirmPasswordLabel, 0, 2);
        resetPasswordPane.add(confirmPasswordField, 0, 3);
        resetPasswordPane.add(spacer, 0, 4);
        resetPasswordPane.add(newPasswordButton, 0, 5);
        Scene resetPasswordScene = new Scene(resetPasswordPane, 400, 400);
        primaryStage.setScene(resetPasswordScene);
        newPasswordButton.setOnAction(e -> {
            char[] password = newPasswordField.getText().trim().toCharArray();
            char[] confirmpassword = confirmPasswordField.getText().trim().toCharArray();
            if (password.length == 0 || confirmpassword.length == 0) {
                showAlert("Please fill out all fields.");
            } else if (!Arrays.equals(password, confirmpassword)) {
                showAlert("Passwords do not match!");
            } else {
                user.setPassword(confirmpassword);
                user.setIsOneTimePassword(false);
                boolean updated = Database.updateUser(user);
                if (updated) {
                    showAlert("Password updated successfully");
                    start(primaryStage);
                } else {
                    showAlert("Failed to update user. Please check your information and try again.");
                }
            }
        });

    }

    private boolean checkPasswordTime(User user){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = LocalDateTime.ofInstant(user.getPasswordExpiration().toInstant(), ZoneId.systemDefault());
        return expiration != null && now.isAfter(expiration);
    }
    private void showRolePage(Stage primaryStage, User user) {
        if(user.isOneTimePassword()){
            if(checkPasswordTime(user)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Your one time password is expired Contact Admin");
                alert.showAndWait();
            }else{
                showResetPasswordPage(primaryStage, user);
            }
        }
        RoleManager role = new RoleManager(user);
        showRoleSelector(primaryStage,user,role);

    }
    private void showRoleSelector(Stage primaryStage, User user, RoleManager roleManager) {
        // Count roles to determine if a selector is necessary
        int roleCount = 0;
        if (roleManager.isAdmin()) roleCount++;
        if (roleManager.isInstructor()) roleCount++;
        if (roleManager.isStudent()) roleCount++;

        // Directly redirect if only one role is present
        if (roleCount == 1) {
            if (roleManager.isAdmin()) {
                showAdminPage(primaryStage);
                return;
            }
            if (roleManager.isInstructor()) {
                showInstructorPage(primaryStage, user);
                return;
            }
            if (roleManager.isStudent()) {
                showStudentPage(primaryStage, user);
                return;
            }
        }

        // Continue with role selector if multiple roles
        GridPane roleSelector = new GridPane();
        roleSelector.setPadding(new Insets(10));
        roleSelector.setVgap(8);
        roleSelector.setHgap(10);
        roleSelector.setAlignment(Pos.CENTER);

        Label roleLabel = new Label("Select Your Role:");
        roleSelector.add(roleLabel, 0, 0); // Position label at the top

        int row = 1; // Start adding buttons from the second row

        if (roleManager.isAdmin()) {
            Button adminButton = new Button("Admin");
            adminButton.setOnAction(e -> {
                showAdminPage(primaryStage);
                primaryStage.close(); // Close the selector after selection
            });
            roleSelector.add(adminButton, 0, row++);
            adminButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(adminButton, Priority.ALWAYS);
        }

        if (roleManager.isInstructor()) {
            Button instructorButton = new Button("Instructor");
            instructorButton.setOnAction(e -> {
                showInstructorPage(primaryStage, user);
                primaryStage.close(); // Close the selector after selection
            });
            roleSelector.add(instructorButton, 0, row++);
            instructorButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(instructorButton, Priority.ALWAYS);
        }

        if (roleManager.isStudent()) {
            Button studentButton = new Button("Student");
            studentButton.setOnAction(e -> {
                showStudentPage(primaryStage, user);
                primaryStage.close(); // Close the selector after selection
            });
            roleSelector.add(studentButton, 0, row++);
            studentButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(studentButton, Priority.ALWAYS);
        }

        Scene roleScene = new Scene(roleSelector, 300, 150); // Set size depending on the content
        primaryStage.setTitle("Role Selection");
        primaryStage.setScene(roleScene);
        primaryStage.show(); // Display the stage with role options
    }

}

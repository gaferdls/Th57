package GUI;


import java.util.ArrayList;
import javafx.scene.layout.VBox;
import util.Article;
import database.Database;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
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
        loginPane.setPadding(new Insets(20));  // More padding for a cleaner look
        loginPane.setVgap(15);                 // Increased spacing for better layout
        loginPane.setHgap(10);
        loginPane.setAlignment(Pos.CENTER);    // Center the grid

        // Labels and input fields
        Label userLabel = new Label("Email:");
        userLabel.getStyleClass().add("label-text");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-text");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");  // Placeholder for hint text
        emailField.getStyleClass().add("input-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().add("input-field");

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("primary-button");

        Button signUpButton = new Button("Sign Up");
        signUpButton.getStyleClass().add("secondary-button");

        // Layout
        loginPane.add(userLabel, 0, 0);
        loginPane.add(emailField, 1, 0);
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);
        loginPane.add(loginButton, 1, 2);
        loginPane.add(signUpButton, 1, 3);

        Scene loginScene = new Scene(loginPane, 400, 300);
        loginScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Fade transition for the login pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), loginPane);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition

        // Button actions
        loginButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), loginButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> {
                // After scaling, revert back to normal size
                ScaleTransition revertScale = new ScaleTransition(Duration.seconds(0.2), loginButton);
                revertScale.setFromX(1.1);
                revertScale.setFromY(1.1);
                revertScale.setToX(1);
                revertScale.setToY(1);
                revertScale.play();
            });
            scaleTransition.play(); // Start the scale transition

            String email = emailField.getText().trim();
            char[] password = passwordField.getText().toCharArray();
            if (Database.hasUser(email)) {
                showSigninPage(primaryStage, email, password);
            } else {
                showAlert("Username not found");
                start(primaryStage);
            }
        });

        signUpButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), signUpButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> {
                // After scaling, revert back to normal size
                ScaleTransition revertScale = new ScaleTransition(Duration.seconds(0.2), signUpButton);
                revertScale.setFromX(1.1);
                revertScale.setFromY(1.1);
                revertScale.setToX(1);
                revertScale.setToY(1);
                revertScale.play();
            });
            scaleTransition.play(); // Start the scale transition

            showSetUpPage(primaryStage);
        });
    }


    private void showSetUpPage(Stage primaryStage) {
        GridPane setUpPane = new GridPane();
        setUpPane.setPadding(new Insets(20));  // Increased padding for a cleaner look
        setUpPane.setVgap(15);                 // More space between rows
        setUpPane.setHgap(10);                 // Space between columns
        setUpPane.setAlignment(Pos.CENTER);    // Center the content

        Label usernameLabel = new Label("Username: ");
        usernameLabel.getStyleClass().add("label-text");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("input-field");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-text");
        TextField passwordField = new TextField();
        passwordField.getStyleClass().add("input-field");

        Label passwordAgainLabel = new Label("Re-enter Password: ");
        passwordAgainLabel.getStyleClass().add("label-text");
        TextField passwordAgainField = new TextField();
        passwordAgainField.getStyleClass().add("input-field");

        Button loginButton = new Button("Sign Up");
        loginButton.getStyleClass().add("primary-button");

        setUpPane.add(usernameLabel, 0, 0);
        setUpPane.add(usernameField, 1, 0);
        setUpPane.add(passwordLabel, 0, 1);
        setUpPane.add(passwordField, 1, 1);
        setUpPane.add(passwordAgainLabel, 0, 2);
        setUpPane.add(passwordAgainField, 1, 2);
        setUpPane.add(loginButton, 1, 3);

        Scene setUpScene = new Scene(setUpPane, 400, 400);
        setUpScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setScene(setUpScene);
        primaryStage.show(); // Ensure the stage is shown

        // Fade transition for the setup pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), setUpPane);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition

        // Button action with scale transition
        loginButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), loginButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> {
                // After scaling, revert back to normal size
                ScaleTransition revertScale = new ScaleTransition(Duration.seconds(0.2), loginButton);
                revertScale.setFromX(1.1);
                revertScale.setFromY(1.1);
                revertScale.setToX(1);
                revertScale.setToY(1);
                revertScale.play();
            });
            scaleTransition.play(); // Start the scale transition

            // Validate and process the sign-up
            String username = usernameField.getText().trim();
            char[] password = passwordField.getText().trim().toCharArray();
            char[] passwordAgain = passwordAgainField.getText().trim().toCharArray();
            if (username.isEmpty() || password.length == 0 || passwordAgain.length == 0) {
                showAlert("Please fill out all fields.");
            } else if (!Arrays.toString(password).equals(Arrays.toString(passwordAgain))) {
                showAlert("Passwords do not match!");
            } else {
                if (Database.isEmpty()) {
                    User newUser = new User(username, null, null, null, null, null, password, false, null, null, true, false, false);
                    Database.addUser(newUser);
                    showAlert("Sign up successful! You are now registered.");
                    start(primaryStage);
                } else {
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
            // Check if user details need to be completed
            if (user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getSkillLevel() == null) {
                showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                finishSetUp(primaryStage, user); // Redirect to finish setup
            } else {
                showAlert("Welcome " + user.getUsername() + "! You are now logged in.");
                showRolePage(primaryStage, user);
            }
        } else {
            showAlert("Incorrect password. Please try again.");
        }

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), primaryStage.getScene().getRoot());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

    }


    private void finishSetUp(Stage primaryStage, User user) {
        System.out.println("Entering finishSetUp method"); // Debugging line

        GridPane finishSetUpPane = new GridPane();
        finishSetUpPane.setPadding(new Insets(20));
        finishSetUpPane.setVgap(15);
        finishSetUpPane.setHgap(10);
        finishSetUpPane.setAlignment(Pos.CENTER); // Center the content

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

        // Finish button
        Button finishUpButton = new Button("Next");
        finishUpButton.getStyleClass().add("primary-button");
        finishSetUpPane.add(finishUpButton, 1, 6);

        Scene signupScene = new Scene(finishSetUpPane, 400, 500);
        signupScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setScene(signupScene);
        primaryStage.show(); // Ensure the stage is shown

        // Button action with scale transition
        finishUpButton.setOnAction(e -> {
            if (validateInput(emailField, firstNameField, middleNameField, lastNameField, preferredNameField, skillBox)) {
                // Update user details
                user.setEmail(emailField.getText());
                user.setFirstName(firstNameField.getText());
                user.setMiddleName(middleNameField.getText());
                user.setLastName(lastNameField.getText());
                user.setPreferredName(preferredNameField.getText());
                user.setSkillLevel(skillBox.getValue());

                // Debugging before the update
                boolean updated = Database.updateUser(user);
                if (updated) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "User successfully updated!");
                    successAlert.showAndWait();

                    // Navigate to the next page after a successful update
                    showRolePage(primaryStage, user); // Adjust this line as necessary
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

    private void showAdminPage(Stage primaryStage, User user) {
        GridPane adminPane = new GridPane();
        adminPane.setPadding(new Insets(20));
        adminPane.setVgap(15);
        adminPane.setHgap(10);
        adminPane.setAlignment(Pos.CENTER);

        String[] buttonLabels = {"Articles", "Invite", "Reset", "Delete", "List", "Roles", "Logout"};
        Button[] buttons = new Button[buttonLabels.length];
        String[] tooltips = {
                "Manage Articles", "Invite Users", "Reset Settings", "Delete Records",
                "List All Entries", "Manage Roles", "Logout from Admin"
        };

        // Set up buttons with tooltips and individual actions
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].getStyleClass().add(i == buttonLabels.length - 1 ? "secondary-button" : "primary-button"); // Last button is Logout
            buttons[i].setTooltip(new Tooltip(tooltips[i]));
            adminPane.add(buttons[i], i % 2, i / 2);
            GridPane.setHgrow(buttons[i], Priority.ALWAYS);
            buttons[i].setMaxWidth(Double.MAX_VALUE);

            // Assign each button's action to navigate to its respective scene
            int index = i;
            buttons[i].setOnAction(e -> handleButtonNavigation(buttonLabels[index], primaryStage, user));
        }

        // Scene setup
        Scene setUpScene = new Scene(adminPane, 450, 450);
        setUpScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(setUpScene);
        primaryStage.show();

        // Fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), adminPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    // Method to handle navigation based on button label
    private void handleButtonNavigation(String label, Stage primaryStage,User user) {
        switch (label) {
            case "Articles":
                showArticlesPage(primaryStage,user);
                break;
            case "Invite":
                showInvitePage(primaryStage);
                break;
            case "Reset":
                showResetPage(primaryStage);
                break;
            case "Delete":
                showDeletePage(primaryStage);
                break;
            case "List":
                showListPage(primaryStage);
                break;
            case "Roles":
                showRolesPage(primaryStage);
                break;
            case "Logout":
                start(primaryStage);
                break;
            default:
                throw new IllegalArgumentException("Unexpected label: " + label);
        }
    }

    // Placeholder methods for each button's page
    private void showArticlesPage(Stage primaryStage, User user) {
        GridPane showarticlePane = new GridPane();
        showarticlePane.setPadding(new Insets(20));
        showarticlePane.setVgap(15);
        showarticlePane.setHgap(10);
        showarticlePane.setAlignment(Pos.CENTER);

        String[] buttonLabels = {"Create", "Update", "View", "Delete", "Back up", "Restore", "Back"};
        Button[] buttons = new Button[buttonLabels.length];
        String[] tooltips = {
                "Create Articles", "Update Articles", "View Articles", "Delete Articles",
                "Back up Articles", "Restore Articles", "Back to the menu"
        };

        // Set up buttons with tooltips and individual actions
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].getStyleClass().add(i == buttonLabels.length - 1 ? "secondary-button" : "primary-button"); // Last button is Logout
            buttons[i].setTooltip(new Tooltip(tooltips[i]));
            showarticlePane.add(buttons[i], i % 2, i / 2);
            GridPane.setHgrow(buttons[i], Priority.ALWAYS);
            buttons[i].setMaxWidth(Double.MAX_VALUE);

            // Assign each button's action to navigate to its respective scene
            int index = i;
            buttons[i].setOnAction(e -> handleArticleNavigation(buttonLabels[index], primaryStage, user));
        }

        // Scene setup
        Scene setUpScene = new Scene(showarticlePane, 450, 450);
        setUpScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(setUpScene);
        primaryStage.show();

        // Fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), showarticlePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void viewArticlesPage(Stage primaryStage, User user) {
        // Create a new layout to display the articles
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Set spacing between elements
        vbox.setPadding(new Insets(20)); // Set padding around the VBox


        // Create a title for the page
        Label titleLabel = new Label("Articles");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");


        // Create a list to hold articles
        ArrayList<Article> articles = new ArrayList<>();




        articles = Database.allArticles();


        ListView<String> listView = new ListView<>();
        for (Article article : articles) {
            String displayText = article.getTitle() + " - " + article.getShortDescription();
            listView.getItems().add(displayText);
        }


        // Create a back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showArticlesPage(primaryStage, user)); // Method to return to admin page


        // Add all elements to the VBox
        vbox.getChildren().addAll(titleLabel, listView, backButton);


        // Create a new Scene and set it to the Stage
        Scene scene = new Scene(vbox, 400, 300); // Set width and height as needed
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Articles");
        primaryStage.show();
    }



    private void addArticlesPage(Stage primaryStage, User user){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);


        // Level Selector
        Label levelLabel = new Label("Level:");
        ComboBox<String> levelSelector = new ComboBox<>();
        levelSelector.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");


        // Grouping Identifier
        Label groupLabel = new Label("Grouping Identifier:");
        TextField groupField = new TextField();


        // Title
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();


        // Short Description
        Label descLabel = new Label("Short Description:");
        TextField descField = new TextField();


        // Body
        Label bodyLabel = new Label("Body:");
        TextArea bodyArea = new TextArea();
        bodyArea.setWrapText(true);
        bodyArea.setPrefRowCount(10);


        // Add components to GridPane
        grid.add(levelLabel, 0, 0);
        grid.add(levelSelector, 1, 0);
        grid.add(groupLabel, 0, 1);
        grid.add(groupField, 1, 1);
        grid.add(titleLabel, 0, 2);
        grid.add(titleField, 1, 2);
        grid.add(descLabel, 0, 3);
        grid.add(descField, 1, 3);
        grid.add(bodyLabel, 0, 4);
        grid.add(bodyArea, 1, 4);


        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Get values as strings
            String level = levelSelector.getValue();
            String groupId = groupField.getText();
            String title = titleField.getText();
            String shortDescription = descField.getText();
            String body = bodyArea.getText();
            Article article = new Article(level, groupId, title, shortDescription, body);
            Database.addArticle(article);
            // Print values (or store them as needed)
            System.out.println("Level: " + level);
            System.out.println("Grouping Identifier: " + groupId);
            System.out.println("Title: " + title);
            System.out.println("Short Description: " + shortDescription);
            System.out.println("Body: " + body);
            showArticlesPage(primaryStage, user);
        });
        grid.add(submitButton, 1, 5);


        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));
        grid.add(backButton, 0, 5);
        // Scene setup
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help System Entry");
        primaryStage.show();




    }


    private void handleArticleNavigation(String label, Stage primaryStage, User user) {
        switch (label) {
            case "Create":
                //
                addArticlesPage(primaryStage, user);

                break;
            case "Update":
                //
                break;
            case "View":
                //
                viewArticlesPage(primaryStage, user);
                break;
            case "Delete":
                //
                break;
            case "Back up":
                //
                break;
            case "Restore":
                //
                break;
            case "Back":
                showAdminPage(primaryStage, user);
                break;
            default:
                throw new IllegalArgumentException("Unexpected label: " + label);
        }
    }

    private void showInvitePage(Stage primaryStage) {
        // Set up the Invite page scene and show it
    }

    private void showResetPage(Stage primaryStage) {
        // Set up the Reset page scene and show it
    }

    private void showDeletePage(Stage primaryStage) {
        // Set up the Delete page scene and show it
    }

    private void showListPage(Stage primaryStage) {
        // Set up the List page scene and show it
    }

    private void showRolesPage(Stage primaryStage) {
        // Set up the Roles page scene and show it
    }

    private void showInstructorPage(Stage primaryStage, User user) {
        GridPane instructorPane = new GridPane();
        instructorPane.setPadding(new Insets(20));
        instructorPane.setVgap(15);
        instructorPane.setHgap(10);
        instructorPane.setAlignment(Pos.CENTER); // Center the content

        // Create buttons with styles
        Button createArticlesButton = new Button("Create Article");
        Button updateArticleButton = new Button("Update Article");
        Button deleteArticleButton = new Button("Delete Article");
        Button listArticlesButton = new Button("List Articles");
        Button logoutButton = new Button("Logout");

        // Add styles to buttons
        createArticlesButton.getStyleClass().add("primary-button");
        updateArticleButton.getStyleClass().add("primary-button");
        deleteArticleButton.getStyleClass().add("primary-button");
        listArticlesButton.getStyleClass().add("primary-button");
        logoutButton.getStyleClass().add("secondary-button");

        // Adding buttons to the grid
        instructorPane.add(createArticlesButton, 0, 1);
        instructorPane.add(updateArticleButton, 0, 2);
        instructorPane.add(deleteArticleButton, 0, 3);
        instructorPane.add(listArticlesButton, 0, 4);
        instructorPane.add(logoutButton, 0, 5); // Logout button

        // Configure buttons to expand to fill space
        for (Button button : new Button[]{createArticlesButton, updateArticleButton, deleteArticleButton, listArticlesButton, logoutButton}) {
            button.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(button, Priority.ALWAYS);
        }

        Scene instructorScene = new Scene(instructorPane, 400, 400);
        instructorScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setScene(instructorScene);
        primaryStage.show(); // Ensure the stage is shown

        // Fade transition for the instructor pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), instructorPane);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition

        // Button actions with scale transition
        createArticlesButton.setOnAction(e -> handleInstructorButtonAction(createArticlesButton));
        updateArticleButton.setOnAction(e -> handleInstructorButtonAction(updateArticleButton));
        deleteArticleButton.setOnAction(e -> handleInstructorButtonAction(deleteArticleButton));
        listArticlesButton.setOnAction(e -> handleInstructorButtonAction(listArticlesButton));
        logoutButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), logoutButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> start(primaryStage)); // Start login screen
            scaleTransition.play(); // Start the scale transition
        });
    }

    // Handles the button actions with a scale transition
    private void handleInstructorButtonAction(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), button);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.1); // Scale up
        scaleTransition.setToY(1.1);  // Scale up
        scaleTransition.setOnFinished(event -> {
            ScaleTransition revertScale = new ScaleTransition(Duration.seconds(0.2), button);
            revertScale.setFromX(1.1);
            revertScale.setFromY(1.1);
            revertScale.setToX(1);
            revertScale.setToY(1);
            revertScale.play();
        });
        scaleTransition.play(); // Start the scale transition

        // Add logic for each button action here
        switch (button.getText()) {
            case "Create Article":
                // Logic for creating an article
                break;
            case "Update Article":
                // Logic for updating an article
                break;
            case "Delete Article":
                // Logic for deleting an article
                break;
            case "List Articles":
                // Logic for listing all articles
                break;
        }
    }


    private void showStudentPage(Stage primaryStage, User user) {
        GridPane studentPane = new GridPane();
        studentPane.setPadding(new Insets(20));
        studentPane.setVgap(15);
        studentPane.setHgap(10);
        studentPane.setAlignment(Pos.CENTER); // Center the content

        // Create a logout button with styles
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("secondary-button"); // Style for the logout button
        logoutButton.setMaxWidth(Double.MAX_VALUE); // Ensure the button expands

        // Adding buttons to the grid
        studentPane.add(logoutButton, 0, 0);

        // Scene setup
        Scene studentScene = new Scene(studentPane, 400, 400);
        studentScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setScene(studentScene);
        primaryStage.show(); // Ensure the stage is shown

        // Fade transition for the student pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), studentPane);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition

        // Button action with scale transition
        logoutButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), logoutButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> start(primaryStage)); // Start login screen
            scaleTransition.play(); // Start the scale transition
        });
    }


    private void showResetPasswordPage(Stage primaryStage, User user) {
        GridPane resetPasswordPane = new GridPane();
        resetPasswordPane.setPadding(new Insets(20));
        resetPasswordPane.setVgap(15);
        resetPasswordPane.setHgap(10);
        resetPasswordPane.setAlignment(Pos.CENTER); // Center the content

        // Labels and TextFields for password entry
        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.getStyleClass().add("label-text"); // Add styles
        TextField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.getStyleClass().add("input-field");

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("label-text"); // Add styles
        TextField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Re-enter password");
        confirmPasswordField.getStyleClass().add("input-field");

        // Button for continuing password reset
        Button newPasswordButton = new Button("Continue");
        newPasswordButton.getStyleClass().add("primary-button");

        // Adding components to the grid
        resetPasswordPane.add(newPasswordLabel, 0, 0);
        resetPasswordPane.add(newPasswordField, 0, 1);
        resetPasswordPane.add(confirmPasswordLabel, 0, 2);
        resetPasswordPane.add(confirmPasswordField, 0, 3);
        resetPasswordPane.add(newPasswordButton, 0, 5);

        // Scene setup
        Scene resetPasswordScene = new Scene(resetPasswordPane, 400, 400);
        resetPasswordScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setScene(resetPasswordScene);
        primaryStage.show(); // Ensure the stage is shown

        // Fade transition for the reset password pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), resetPasswordPane);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition

        // Button action with scale transition
        newPasswordButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), newPasswordButton);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1); // Scale up
            scaleTransition.setToY(1.1);  // Scale up
            scaleTransition.setOnFinished(event -> {
                // Revert back to normal size after scaling
                ScaleTransition revertScale = new ScaleTransition(Duration.seconds(0.2), newPasswordButton);
                revertScale.setFromX(1.1);
                revertScale.setFromY(1.1);
                revertScale.setToX(1);
                revertScale.setToY(1);
                revertScale.play();
            });
            scaleTransition.play(); // Start the scale transition

            // Validate passwords
            char[] password = newPasswordField.getText().trim().toCharArray();
            char[] confirmPassword = confirmPasswordField.getText().trim().toCharArray();
            if (password.length == 0 || confirmPassword.length == 0) {
                showAlert("Please fill out all fields.");
            } else if (!Arrays.equals(password, confirmPassword)) {
                showAlert("Passwords do not match!");
            } else {
                user.setPassword(confirmPassword);
                user.setIsOneTimePassword(false);
                boolean updated = Database.updateUser(user);
                if (updated) {
                    showAlert("Password updated successfully");
                    start(primaryStage); // Navigate back to the login screen
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
                showAdminPage(primaryStage,user);
                return;
            }
            if (roleManager.isInstructor()) {
                showArticlesPage(primaryStage,user);
                return;
            }
            if (roleManager.isStudent()) {
                showStudentPage(primaryStage, user);
                return;
            }
        }

        // Continue with role selector if multiple roles
        GridPane roleSelector = new GridPane();
        roleSelector.setPadding(new Insets(20));  // Increased padding for a cleaner look
        roleSelector.setVgap(15);                 // More space between rows
        roleSelector.setHgap(10);                 // Space between columns
        roleSelector.setAlignment(Pos.CENTER);     // Center the content

        Label roleLabel = new Label("Select Your Role:");
        roleLabel.getStyleClass().add("label-text"); // Add styles for the label
        roleSelector.add(roleLabel, 0, 0); // Position label at the top

        int row = 1; // Start adding buttons from the second row

        if (roleManager.isAdmin()) {
            Button adminButton = new Button("Admin");
            adminButton.getStyleClass().add("primary-button"); // Add button style
            adminButton.setOnAction(e -> {
                showAdminPage(primaryStage, user);// Close the selector after selection
            });
            roleSelector.add(adminButton, 0, row++);
            adminButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(adminButton, Priority.ALWAYS);
        }

        if (roleManager.isInstructor()) {
            Button instructorButton = new Button("Instructor");
            instructorButton.getStyleClass().add("primary-button"); // Add button style
            instructorButton.setOnAction(e -> {
                showArticlesPage(primaryStage,user);
                primaryStage.close(); // Close the selector after selection
            });
            roleSelector.add(instructorButton, 0, row++);
            instructorButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(instructorButton, Priority.ALWAYS);
        }

        if (roleManager.isStudent()) {
            Button studentButton = new Button("Student");
            studentButton.getStyleClass().add("primary-button"); // Add button style
            studentButton.setOnAction(e -> {
                showStudentPage(primaryStage, user);
                primaryStage.close(); // Close the selector after selection
            });
            roleSelector.add(studentButton, 0, row++);
            studentButton.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(studentButton, Priority.ALWAYS);
        }

        Scene roleScene = new Scene(roleSelector, 300, 200); // Set size depending on the content
        roleScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); // Load CSS styles
        primaryStage.setTitle("Role Selection");
        primaryStage.setScene(roleScene);
        primaryStage.show(); // Display the stage with role options

        // Fade transition for the role selector pane
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), roleSelector);
        fadeTransition.setFromValue(0); // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();            // Start the transition
    }


}

package GUI;

import javafx.scene.Node;
import javafx.scene.text.TextAlignment;
import util.HelpMessage;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.h2.command.ddl.GrantRevoke;
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

        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);

        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(20));  // More padding for a cleaner look
        loginPane.setVgap(15);                 // Increased spacing for better layout
        loginPane.setHgap(10);
        loginPane.setAlignment(Pos.CENTER);    // Center the grid

        // Labels and input fields
        Label userLabel = new Label("Username:");
        userLabel.getStyleClass().add("label-text");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-text");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your username");  // Placeholder for hint text
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
                showAlert("   Username not found. \nTry Signing Up instead! :)");
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
                    User newUser = new User(username, null, null, null, null, null, password, false, null, null, true, false, false, null);
                    Database.addUser(newUser);
                    showAlert("Sign up successful! You are now registered.");
                    start(primaryStage);
                } else {
                    User newUser = new User(username, null, null, null, null, null, password, false, null, null, false, true, false, null);
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

        // Label for Middle Name
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField(user.getMiddleName());
        Label optionalLabel = new Label("(optional)");
        middleNameField.setPromptText("(optional)");

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
                user.setGroups("none");

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
        // Create a VBox layout with spacing and padding
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Title Label with enhanced styling
        Label titleLabel = new Label("Articles");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Fetch and display articles
        ArrayList<Article> articles = Database.allArticles();
        ListView<String> listView = new ListView<>();
        for (Article article : articles) {
            String displayText = article.getTitle() + " - " + article.getShortDescription();
            listView.getItems().add(displayText);
        }
        listView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ced4da;");
        listView.setPrefHeight(200);

        // Back button styling
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));

        // Search bar
        TextField search = new TextField();
        search.setPromptText("Search...");
        search.getStyleClass().add("secondary_button");
        search.setMaxWidth(Double.MAX_VALUE);
        search.setOnKeyPressed(e -> {
            listView.getItems().clear();
            for (Article a : articles) {
                if (!a.getTitle().contains(search.getText()) ||
                        !a.getAbstract().contains(search.getText())) {
                    continue;
                }
                String displayText = a.getTitle() + " - " + a.getShortDescription();
                listView.getItems().add(displayText);
            }
        });

        // Apply fade-in animations
        FadeTransition titleFade = new FadeTransition(Duration.seconds(1), titleLabel);
        titleFade.setFromValue(0);
        titleFade.setToValue(1);
        titleFade.play();

        FadeTransition listFade = new FadeTransition(Duration.seconds(1), listView);
        listFade.setFromValue(0);
        listFade.setToValue(1);
        listFade.play();

        FadeTransition buttonFade = new FadeTransition(Duration.seconds(1), backButton);
        buttonFade.setFromValue(0);
        buttonFade.setToValue(1);
        buttonFade.play();

        // Add elements to VBox and set up the scene
        vbox.getChildren().addAll(titleLabel, search, listView, backButton);
        Scene scene = new Scene(vbox, 450, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Articles");
        primaryStage.show();

        // Fade transition for the entire VBox
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), vbox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void addArticlesPage(Stage primaryStage, User user) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

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

        // Keywords
        Label keywordsLabel = new Label("Keywords:");
        TextArea keywordsArea = new TextArea();
        keywordsArea.setWrapText(true);
        keywordsArea.setPrefRowCount(10);

        // References
        Label refsLabel = new Label("References:");
        TextArea refsArea = new TextArea();
        refsArea.setWrapText(true);
        refsArea.setPrefRowCount(10);

        Label groupsLabel = new Label("Groups");
        TextArea groupsArea = new TextArea();
        groupsArea.setWrapText(true);
        groupsArea.setPrefRowCount(10);

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
        grid.add(keywordsLabel, 0, 5);
        grid.add(keywordsArea, 1, 5);
        grid.add(refsLabel, 0, 6);
        grid.add(refsArea, 1, 6);
        grid.add(groupsLabel, 0, 7);
        grid.add(groupsArea, 1, 7);

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("primary-button");
        submitButton.setMaxWidth(Double.MAX_VALUE);
        submitButton.setOnAction(e -> {
            // Get values as strings
            String level = levelSelector.getValue();
            String groupId = groupField.getText();
            String title = titleField.getText();
            String shortDescription = descField.getText();
            String body = bodyArea.getText();
            String keywords = keywordsArea.getText();
            String refs = refsArea.getText();
            String groups = groupsArea.getText();
            Article article = new Article(level, groupId, title, shortDescription, body, keywords, refs, groups);
            Database.addArticle(article);
            showArticlesPage(primaryStage, user);
        });
        grid.add(submitButton, 1, 8);
        GridPane.setHgrow(submitButton, Priority.ALWAYS);

        // Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));
        grid.add(backButton, 0, 8);
        GridPane.setHgrow(backButton, Priority.ALWAYS);

        // Scene setup
        Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help System Entry");
        primaryStage.show();

        // Fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), grid);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void backupArticlesScreen(Stage primaryStage, User user) {
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Label text = new Label("Filename: ");
        TextField filenameField = new TextField();
        Button submit = new Button("submit");
        Button backButton = new Button("Back");  // New back button

        pane.add(text, 0, 0);
        pane.add(filenameField, 1, 0);
        pane.add(submit, 0, 1);
        pane.add(backButton, 1, 1);  // Add back button to grid

        submit.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");  // Style the back button

        submit.setOnAction(e -> {
            String filename = filenameField.getText();
            boolean did = Database.db.backupToFile(filename);
            showAlert(did ? "Backup successful" : "Backup failed");
            showArticlesPage(primaryStage, user);
        });

        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));  // Back button action

        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("backup articles");
        primaryStage.show();
    }

    private void restoreArticlesScreen(Stage primaryStage, User user) { //Restore Article functionalities and GUI
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Label text = new Label("Filename: ");
        TextField filenameField = new TextField();
        Button submit = new Button("Submit");
        Button backButton = new Button("Back");

        pane.add(text, 0, 0);
        pane.add(filenameField, 1, 0);
        pane.add(submit, 0, 1);
        pane.add(backButton, 1, 1);  // Add back button to grid

        submit.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");  // Style the back button

        submit.setOnAction(e -> {
            String filename = filenameField.getText();
            boolean did = Database.db.restoreFromFile(filename);
            showAlert(did ? "Restore successful" : "Restore failed");
            showArticlesPage(primaryStage, user);
        });

        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));  // Back button action

        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("restore articles");
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
                updateArticlesPage(primaryStage, user);
                break;
            case "View":
                //
                viewArticlesPage(primaryStage, user);
                break;
            case "Delete":
                //
                deleteArticlesPage(primaryStage, user);
                break;
            case "Back up":
                //
                backupArticlesScreen(primaryStage, user);
                break;
            case "Restore":
                //
                restoreArticlesScreen(primaryStage, user);
                break;
            case "Back":
                if (user.isAdmin())
                    showAdminPage(primaryStage, user);
                else showInstructorPage(primaryStage, user);
                break;
            default:
                throw new IllegalArgumentException("Unexpected label: " + label);
        }
    }

    private void showInvitePage(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Email input
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email address");

        // Role selection
        Label roleLabel = new Label("Role:");
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Student", "Instructor");
        roleBox.setValue("Student");

        // Buttons
        Button inviteButton = new Button("Send Invite");
        Button backButton = new Button("Back");

        inviteButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");

        // Layout
        pane.add(emailLabel, 0, 0);
        pane.add(emailField, 1, 0);
        pane.add(roleLabel, 0, 1);
        pane.add(roleBox, 1, 1);
        pane.add(inviteButton, 0, 2);
        pane.add(backButton, 1, 2);

        // Button actions
        inviteButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                showAlert("Please enter an email address");
            } else {
                // Add invite logic here
                showAlert("Invite sent to " + email);
            }
        });

        backButton.setOnAction(e -> showAdminPage(primaryStage, null));

        Scene scene = new Scene(pane, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showResetPage(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Username input
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        // Buttons
        Button resetButton = new Button("Reset Password");
        Button backButton = new Button("Back");

        resetButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");

        // Layout
        pane.add(usernameLabel, 0, 0);
        pane.add(usernameField, 1, 0);
        pane.add(resetButton, 0, 1);
        pane.add(backButton, 1, 1);

        // Button actions
        resetButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                showAlert("Please enter a username");
            } else {
                User user = Database.findUserByUsername(username);
                if (user != null) {
                    // Add reset logic here
                    showAlert("Password reset for " + username);
                } else {
                    showAlert("User not found");
                }
            }
        });

        backButton.setOnAction(e -> showAdminPage(primaryStage, null));

        Scene scene = new Scene(pane, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDeletePage(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Username input
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username to delete");

        // Confirmation checkbox
        CheckBox confirmBox = new CheckBox("I confirm this deletion");

        // Buttons
        Button deleteButton = new Button("Delete User");
        Button backButton = new Button("Back");

        deleteButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");

        // Layout
        pane.add(usernameLabel, 0, 0);
        pane.add(usernameField, 1, 0);
        pane.add(confirmBox, 1, 1);
        pane.add(deleteButton, 0, 2);
        pane.add(backButton, 1, 2);

        // Button actions
        deleteButton.setOnAction(e -> {
            if (!confirmBox.isSelected()) {
                showAlert("Please confirm the deletion");
                return;
            }
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                showAlert("Please enter a username");
            } else {
                User user = Database.findUserByUsername(username);
                if (user != null) {
                    Database.deleteUser(user);
                    showAlert("User " + username + " has been deleted");
                } else {
                    showAlert("User not found");
                }
            }
        });

        backButton.setOnAction(e -> showAdminPage(primaryStage, null));

        Scene scene = new Scene(pane, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showListPage(Stage primaryStage) {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("User List");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        // User list view
        ListView<String> userListView = new ListView<>();
        ArrayList<User> users = Database.getAllUsers();
        for (User user : users) {
            userListView.getItems().add(String.format("%s - %s",
                    user.getUsername(),
                    user.isAdmin() ? "Admin" : (user.isInstructor() ? "Instructor" : "Student")));
        }

        // Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");

        // Layout
        vbox.getChildren().addAll(titleLabel, userListView, backButton);

        // Button action
        backButton.setOnAction(e -> showAdminPage(primaryStage, null));

        Scene scene = new Scene(vbox, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRolesPage(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Username input
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        // Role checkboxes
        CheckBox adminBox = new CheckBox("Admin");
        CheckBox instructorBox = new CheckBox("Instructor");
        CheckBox studentBox = new CheckBox("Student");

        // Buttons
        Button updateButton = new Button("Update Roles");
        Button backButton = new Button("Back");

        updateButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button");

        // Layout
        pane.add(usernameLabel, 0, 0);
        pane.add(usernameField, 1, 0);
        pane.add(adminBox, 1, 1);
        pane.add(instructorBox, 1, 2);
        pane.add(studentBox, 1, 3);
        pane.add(updateButton, 0, 4);
        pane.add(backButton, 1, 4);

        // Button actions
        updateButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                showAlert("Please enter a username");
            } else {
                User user = Database.findUserByUsername(username);
                if (user != null) {
                    user.setAdmin(adminBox.isSelected());
                    user.setInstructor(instructorBox.isSelected());
                    user.setStudent(studentBox.isSelected());
                    Database.updateUser(user);
                    showAlert("Roles updated for " + username);
                } else {
                    showAlert("User not found");
                }
            }
        });

        backButton.setOnAction(e -> showAdminPage(primaryStage, null));

        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void helpMessages(Stage primaryStage, User user) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Create UI Components
        Button submitHelp = new Button("Submit");
        submitHelp.getStyleClass().add("primary-button"); // Applying primary button style
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button"); // Applying secondary button style
        ChoiceBox<String> messageTypes = new ChoiceBox<>();
        TextField message = new TextField();
        message.setPromptText("Type your help message here...");

        // Setup ChoiceBox items
        messageTypes.getItems().addAll("Generic", "Specific");
        messageTypes.setValue("Generic"); // Set a default value

        // Adding components to the GridPane
        pane.add(backButton, 0, 0);
        pane.add(new Label("Select Message Type:"), 0, 1);
        pane.add(messageTypes, 1, 1);
        pane.add(new Label("Enter Message:"), 0, 2);
        pane.add(message, 1, 2);
        pane.add(submitHelp, 1, 3);

        // Submit Help Request
        submitHelp.setOnAction(e -> {
            HelpMessage request = new HelpMessage(messageTypes.getValue(), message.getText());
            Database.addRequest(request);
            message.clear(); // Clear the text field after submission
            messageTypes.setValue("Generic"); // Reset the choice box to default
        });

        // Back button action to return to the student page
        backButton.setOnAction(e -> showStudentPage(primaryStage, user));

        // Scene and Stage Setup
        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help Messages");

        // Apply fade-in effect
        FadeTransition fade = new FadeTransition(Duration.seconds(1), pane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        primaryStage.show();
    }



    private void showInstructorPage(Stage primaryStage, User user) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        Button articlesButton = new Button("Articles");
        Button groupsButton = new Button("Groups");
        Button helpButton = new Button("Get Help");
        Button logoutButton = new Button("Logout");

        articlesButton.getStyleClass().add("primary-button");
        articlesButton.setMaxWidth(Double.MAX_VALUE);

        groupsButton.getStyleClass().add("primary-button");
        groupsButton.setMaxWidth(Double.MAX_VALUE);

        helpButton.getStyleClass().add("primary-button");
        helpButton.setMaxWidth(Double.MAX_VALUE);

        logoutButton.getStyleClass().add("secondary-button"); // Style for the logout button
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        pane.add(articlesButton, 0, 0);
        pane.add(groupsButton, 0, 1);
        pane.add(helpButton, 0, 2);
        pane.add(logoutButton, 0, 3);

        Scene scene = new Scene(pane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        articlesButton.setOnAction(e -> showArticlesPage(primaryStage, user));
        groupsButton.setOnAction(e -> showSetupGroups(primaryStage, user));
        helpButton.setOnAction(e -> showInstructorHelpPage(primaryStage, user));

        logoutButton.setOnAction(e -> start(primaryStage));

        primaryStage.setScene(scene);
    }

    private void showStudentPage(Stage primaryStage, User user) {
        GridPane studentPane = new GridPane();
        studentPane.setPadding(new Insets(20));
        studentPane.setVgap(15);
        studentPane.setHgap(10);
        studentPane.setAlignment(Pos.CENTER); // Center the content

        // Create buttons for Articles, Help, and Logout
        Button articlesButton = new Button("Articles");
        articlesButton.getStyleClass().add("primary-button"); // Assume a style class
        articlesButton.setMaxWidth(Double.MAX_VALUE);

        Button helpButton = new Button("Help Request");
        helpButton.getStyleClass().add("primary-button"); // Assume the same style as the Articles button
        helpButton.setMaxWidth(Double.MAX_VALUE);

        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("secondary-button"); // Style for the logout button
        logoutButton.setMaxWidth(Double.MAX_VALUE); // Ensure the button expands

        // Adding buttons to the grid in the desired order
        studentPane.add(articlesButton, 0, 0);
        studentPane.add(helpButton, 0, 1);
        studentPane.add(logoutButton, 0, 2);

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

        // Define actions for Articles, Help, and Logout buttons
        articlesButton.setOnAction(e -> {
            showGroupsPage(primaryStage, user);
            System.out.println("Articles button clicked");
        });

        helpButton.setOnAction(e -> {
            showHelpPage(primaryStage, user);// Add the action for the Help button here
            System.out.println("Help button clicked");
            helpMessages(primaryStage, user);
        });

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
                user.setGroups("none");
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
                showInstructorPage(primaryStage,user);
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
                System.out.println("Is here!!");
                showInstructorPage(primaryStage,user);
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
    private void showGroupsPage(Stage primaryStage, User user) {
        // Create a VBox layout with spacing and padding
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Title Label with enhanced styling
        Label titleLabel = new Label("User Groups");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Fetch and display groups that the user is part of
        String groupsString = user.getGroups(); // Get the comma-separated groups string
        ListView<String> groupsListView = new ListView<>();
        if (groupsString != null && !groupsString.isEmpty()) {
            String[] groupsArray = groupsString.split(","); // Split by comma
            for (String group : groupsArray) {
                groupsListView.getItems().add(group.trim()); // Trim any extra whitespace
            }
        }
        groupsListView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ced4da;");
        groupsListView.setPrefHeight(200);

        // Level selection dropdown
        Label levelLabel = new Label("Select Level:");
        ComboBox<String> levelComboBox = new ComboBox<>();
        levelComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert", "All");
        levelComboBox.setValue("All"); // Default value

        // Back button styling
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> showRolePage(primaryStage, user)); // Assuming this navigates back to the main page

        // Apply fade-in animations
        FadeTransition titleFade = new FadeTransition(Duration.seconds(1), titleLabel);
        titleFade.setFromValue(0);
        titleFade.setToValue(1);
        titleFade.play();

        FadeTransition groupsFade = new FadeTransition(Duration.seconds(1), groupsListView);
        groupsFade.setFromValue(0);
        groupsFade.setToValue(1);
        groupsFade.play();

        FadeTransition levelFade = new FadeTransition(Duration.seconds(1), levelComboBox);
        levelFade.setFromValue(0);
        levelFade.setToValue(1);
        levelFade.play();

        FadeTransition buttonFade = new FadeTransition(Duration.seconds(1), backButton);
        buttonFade.setFromValue(0);
        buttonFade.setToValue(1);
        buttonFade.play();

        // Add elements to VBox and set up the scene
        vbox.getChildren().addAll(titleLabel, groupsListView, levelLabel, levelComboBox, backButton);
        Scene scene = new Scene(vbox, 450, 500); // Adjusted height to accommodate new elements
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("View Groups");
        primaryStage.show();

        // Fade transition for the entire VBox
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), vbox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    private void showSetupGroups(Stage primaryStage, User user) {
        // Create a VBox layout with spacing and padding
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Title Label with enhanced styling
        Label titleLabel = new Label("Manage Groups");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Create button with styling and action
        Button createButton = new Button("Create");
        createButton.getStyleClass().add("primary-button");
        createButton.setMaxWidth(Double.MAX_VALUE);
        createButton.setOnAction(e -> handleCreateGroup(primaryStage, user));

        // Edit button with styling and action
        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("primary-button");
        editButton.setMaxWidth(Double.MAX_VALUE);
        // Uncomment and define handleEditGroup when ready
        // editButton.setOnAction(e -> handleEditGroup(primaryStage, user));

        // Delete button with styling and action
        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("primary-button");
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        // Uncomment and define handleDeleteGroup when ready
        // deleteButton.setOnAction(e -> handleDeleteGroup(primaryStage, user));

        // Back button with styling and action
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> showInstructorPage(primaryStage, user));

        // Apply fade-in animations to each component
        applyFadeTransition(titleLabel, 1.0);
        applyFadeTransition(createButton, 1.0);
        applyFadeTransition(editButton, 1.0);
        applyFadeTransition(deleteButton, 1.0);
        applyFadeTransition(backButton, 1.0);

        // Add elements to VBox
        vbox.getChildren().addAll(titleLabel, createButton, editButton, deleteButton, backButton);

        // Setup scene and stage
        Scene scene = new Scene(vbox, 300, 400); // Adjusted height for additional button
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Group Management");
        primaryStage.show();
    }

    private void applyFadeTransition(Node node, double durationSeconds) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(durationSeconds), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void handleCreateGroup(Stage primaryStage, User user) {
        GridPane createGroupGrid = new GridPane();
        createGroupGrid.setAlignment(Pos.CENTER);
        createGroupGrid.setHgap(10);
        createGroupGrid.setVgap(10);
        createGroupGrid.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Groups");
        titleLabel.getStyleClass().add("label");
        TextField groupNameField = new TextField();
        groupNameField.getStyleClass().add("text-field");
        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("primary-button");

        // Back button configuration
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        backButton.setOnAction(e -> showSetupGroups(primaryStage, user)); // This needs to navigate back to the appropriate page

        createGroupGrid.add(titleLabel, 0, 0, 2, 1); // Merge columns for the title
        createGroupGrid.add(new Label("Group Name:"), 0, 1);
        createGroupGrid.add(groupNameField, 1, 1);
        createGroupGrid.add(nextButton, 0, 2);
        createGroupGrid.add(backButton, 1, 2); // Place back button next to the next button

        nextButton.setOnAction(e -> {
            String groupName = groupNameField.getText(); // Assume groupName is used in the next screen
            createGroupGrid.getChildren().clear();
            showUserSelection(createGroupGrid, primaryStage, user, groupName);
        });

        Scene scene = new Scene(createGroupGrid, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        applyFadeTransition(createGroupGrid, 1.0);  // Use the predefined method for fade transition
    }

    private void showUserSelection(GridPane grid, Stage primaryStage, User user, String groupName) {
        Label selectUsersLabel = new Label("Select Users (click on names to toggle selection):");
        selectUsersLabel.getStyleClass().add("label");
        grid.add(selectUsersLabel, 0, 0);

        ListView<String> userListView = new ListView<>();
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        configureListViewForToggleSelection(userListView); // Configure ListView for toggle selection

        try {
            ArrayList<User> users = Database.getAllUsers();
            for (User usr : users) {
                userListView.getItems().add(usr.getUsername());
            }
        } catch (Exception ex) {
            System.err.println("Error fetching users: " + ex.getMessage());
        }

        grid.add(userListView, 0, 1, 2, 1);

        Button nextButton = new Button("Next: Select Articles");
        nextButton.getStyleClass().add("primary-button");
        grid.add(nextButton, 1, 2);
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button");
        grid.add(backButton, 0, 2);
        backButton.setOnAction(e -> handleCreateGroup(primaryStage, user));

        nextButton.setOnAction(e -> {
            for (int i = 0; i < userListView.getSelectionModel().getSelectedItems().size(); i++) {
                User selected = Database.findUserByUsername(userListView.getSelectionModel().getSelectedItems().get(i));
                String updateGroup = selected.getGroups() + ", " + groupName;
                selected.setGroups(updateGroup);
            }
            grid.getChildren().clear();
            selectArticles(primaryStage, user, grid, groupName);
        });

        FadeTransition fade = new FadeTransition(Duration.seconds(1), grid);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void selectArticles(Stage primaryStage, User user, GridPane grid, String groupName) {
        Label titleLabel = new Label("Articles");
        titleLabel.getStyleClass().add("label");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #333333;");

        ListView<String> articleListView = new ListView<>();
        articleListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        configureListViewForToggleSelection(articleListView); // Configure ListView for toggle selection

        try {
            ArrayList<Article> articles = Database.allArticles();
            for (Article article : articles) {
                articleListView.getItems().add(article.getTitle() + " - " + article.getShortDescription());
            }
        } catch (Exception ex) {
            System.err.println("Error fetching articles: " + ex.getMessage());
        }

        grid.add(titleLabel, 0, 0);
        grid.add(articleListView, 0, 1, 2, 1);

        Button finishButton = new Button("Finish Selection");
        finishButton.getStyleClass().add("primary-button");
        grid.add(finishButton, 1, 2);
        finishButton.setOnAction(e -> {
            for (int i = 0; i < articleListView.getSelectionModel().getSelectedItems().size(); i++) {

                System.out.print(articleListView.getSelectionModel().getSelectedItems().get(i));
                //String updateGroup = selected.getGroups() + ", " + groupName;
               showUserSelection(grid, primaryStage, user, groupName);
            }
            showRolePage(primaryStage, user);
        });

        FadeTransition fade = new FadeTransition(Duration.seconds(1), grid);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void configureListViewForToggleSelection(ListView<String> listView) {
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (listView.getSelectionModel().getSelectedIndices().contains(index)) {
                        listView.getSelectionModel().clearSelection(index);
                    } else {
                        listView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }
    private void showHelpPage(Stage primaryStage, User user) {
        // Create the main layout for the help page
        GridPane HelpPane = new GridPane();
        HelpPane.setPadding(new Insets(20));
        HelpPane.setVgap(15);
        HelpPane.setHgap(10);
        HelpPane.setAlignment(Pos.CENTER);

        // Create a label to guide the user
        Label instructionLabel = new Label("Contact administrator if you need help using the system.\n"
                + "Contact instructor if you need help with something else.");
        instructionLabel.setWrapText(true); // Wrap text if it overflows
        instructionLabel.setTextAlignment(TextAlignment.CENTER);

        // Create buttons
        Button GenericHelpButton = new Button("Contact admin");
        GenericHelpButton.getStyleClass().add("secondary-button");
        GenericHelpButton.setMaxWidth(Double.MAX_VALUE);

        Button SpecificHelpButton = new Button("Contact instructor");
        SpecificHelpButton.getStyleClass().add("secondary-button");
        SpecificHelpButton.setMaxWidth(Double.MAX_VALUE);

        // Create a text field to display when help buttons are pressed
        TextField HelpField = new TextField();
        HelpField.setPromptText("Enter your help request here");
        HelpField.getStyleClass().add("input-field");
        HelpField.setVisible(false); // Initially hidden

        // Add event handlers for the buttons
        GenericHelpButton.setOnAction(e -> {
            HelpField.setVisible(true); // Make the text field visible
            HelpField.setText("This is for generic help. Describe your issue."); // Set a generic prompt
            System.out.println("Generic Help button clicked");


        });

        SpecificHelpButton.setOnAction(e -> {
            HelpField.setVisible(true); // Make the text field visible
            HelpField.setText("This is for specific help. Specify what you need."); // Set a specific prompt
            System.out.println("Specific Help button clicked");
        });

        // Add components to the layout
        HelpPane.add(instructionLabel, 0, 0, 2, 1); // Add the instruction label to row 0, spanning 2 columns
        HelpPane.add(GenericHelpButton, 0, 1); // Add GenericHelpButton to row 1, column 0
        HelpPane.add(SpecificHelpButton, 0, 2); // Add SpecificHelpButton to row 2, column 0
        HelpPane.add(HelpField, 0, 3, 2, 1); // Add HelpField to row 3, spanning 2 columns

        // Create a scene and set it on the primary stage
        Scene scene = new Scene(HelpPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help Page");
        primaryStage.show();
    }

    private void showInstructorHelpPage(Stage primaryStage, User user) {
        // Create the main layout for the help page
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setVgap(15);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);

        // Create UI Components
        Button submitHelp = new Button("Submit");
        submitHelp.getStyleClass().add("primary-button"); // Applying primary button style
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("secondary-button"); // Applying secondary button style
        TextField message = new TextField();
        message.setPromptText("Type your help message here...");

        // Add components to the GridPane
        pane.add(backButton, 0, 0);
        pane.add(new Label("Enter Message for Admin:"), 0, 1);
        pane.add(message, 1, 1);
        pane.add(submitHelp, 1, 2);

        // Submit Help Request
        submitHelp.setOnAction(e -> {
            HelpMessage request = new HelpMessage("Generic", message.getText()); // Always set to "Generic"
            Database.addRequest(request);
            message.clear(); // Clear the text field after submission
        });

        // Back button action to return to the previous page
        backButton.setOnAction(e -> showInstructorPage(primaryStage, user)); // Assuming there’s a method for the instructor's page

        // Scene and Stage Setup
        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Instructor Help Messages");

        // Apply fade-in effect
        FadeTransition fade = new FadeTransition(Duration.seconds(1), pane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        primaryStage.show();
    }

    private void showEditForm(Stage primaryStage, User user, Article article) {
        GridPane editPane = new GridPane();
        editPane.setVgap(10);
        editPane.setHgap(10);
        editPane.setPadding(new Insets(20));
        editPane.setAlignment(Pos.CENTER);

        // Form fields to edit article
        Label groupingIdLabel = new Label("Grouping ID:");
        TextField groupingIdField = new TextField(article.getGroupId());

        Label levelLabel = new Label("Level:");
        TextField levelField = new TextField(article.getLevel());

        Label shortDescLabel = new Label("Short Description:");
        TextField shortDescField = new TextField(article.getShortDescription());

        Label bodyLabel = new Label("Body:");
        TextArea bodyArea = new TextArea(article.getBody());
        bodyArea.setPrefRowCount(5);

        Label keywordsLabel = new Label("Keywords:");
        TextField keywordsField = new TextField(article.getKeywords());

        Label referencesLabel = new Label("References:");
        TextField referencesField = new TextField(article.getReferences());

        Label groupsLabel = new Label("Groups:");
        TextField groupsField = new TextField(article.getGroups());

        Button updateButton = new Button("Update");
        Button cancelButton = new Button("Cancel");

        // Add fields to the grid
        editPane.add(groupingIdLabel, 0, 0);
        editPane.add(groupingIdField, 1, 0);

        editPane.add(levelLabel, 0, 1);
        editPane.add(levelField, 1, 1);

        editPane.add(shortDescLabel, 0, 2);
        editPane.add(shortDescField, 1, 2);

        editPane.add(bodyLabel, 0, 3);
        editPane.add(bodyArea, 1, 3);

        editPane.add(keywordsLabel, 0, 4);
        editPane.add(keywordsField, 1, 4);

        editPane.add(referencesLabel, 0, 5);
        editPane.add(referencesField, 1, 5);

        editPane.add(groupsLabel, 0, 6);
        editPane.add(groupsField, 1, 6);

        editPane.add(updateButton, 0, 7);
        editPane.add(cancelButton, 1, 7);

        updateButton.getStyleClass().add("primary-button");
        cancelButton.getStyleClass().add("secondary-button");

        updateButton.setOnAction(e -> {
            // Update article fields
            article.setGroupId(groupingIdField.getText());
            article.setLevel(levelField.getText());
            article.setShortDescription(shortDescField.getText());
            article.setBody(bodyArea.getText());
            article.setKeywords(keywordsField.getText());
            article.setReferences(referencesField.getText());
            article.setGroups(groupsField.getText());

            boolean updated = Database.db.updateArticleByTitle(article, article.getTitle());

            if (updated) {
                showAlert("Article updated successfully!");
                showArticlesPage(primaryStage, user); // Go back to the articles page
            } else {
                showAlert("Failed to update article.");
            }


        });

        cancelButton.setOnAction(e -> updateArticlesPage(primaryStage, user)); // Cancel and go back

        Scene editScene = new Scene(editPane, 600, 600);
        editScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(editScene);
        primaryStage.setTitle("Edit Article");
        primaryStage.show();
    }




    private void updateArticlesPage(Stage primaryStage, User user) {
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Article Title:");
        TextField titleField = new TextField();
        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");

        // Add search fields
        pane.add(titleLabel, 0, 0);
        pane.add(titleField, 1, 0);
        pane.add(searchButton, 0, 1);
        pane.add(backButton, 1, 1);  // Add back button to grid

        searchButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button"); // Style the back button
        searchButton.setOnAction(e -> {
            String title = titleField.getText();

            try {
                Article fetchedArticle = Database.db.getArticleByTitle(title);

                if (fetchedArticle != null) {
                    showEditForm(primaryStage, user, fetchedArticle);
                } else {
                    showAlert("No article found with the title: " + title);
                }
            } catch (SQLException ex) {
                System.err.println("Error retrieving article: " + ex.getMessage());
                showAlert("An error occurred while retrieving the article.");
            }
        });


        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));  // Back button action

        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Articles");
        primaryStage.show();
    }

    private void deleteArticlesPage(Stage primaryStage, User user) {
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Article Title:");
        TextField titleField = new TextField();
        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");

        // Add search fields
        pane.add(titleLabel, 0, 0);
        pane.add(titleField, 1, 0);
        pane.add(searchButton, 0, 1);
        pane.add(backButton, 1, 1);  // Add back button to grid

        searchButton.getStyleClass().add("primary-button");
        backButton.getStyleClass().add("secondary-button"); // Style the back button
        searchButton.setOnAction(e -> {
            String title = titleField.getText();

            try {
                Article fetchedArticle = Database.db.getArticleByTitle(title);

                if (fetchedArticle != null) {
                    Database.db.deleteArticle("title");
                } else {
                    showAlert("No article found with the title: " + title);
                }
            } catch (SQLException ex) {
                System.err.println("Error retrieving article: " + ex.getMessage());
                showAlert("An error occurred while retrieving the article.");
            }
        });


        backButton.setOnAction(e -> showArticlesPage(primaryStage, user));  // Back button action

        Scene scene = new Scene(pane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Articles");
        primaryStage.show();
    }
}

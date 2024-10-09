package GUI;

import java.awt.TextField;

import database.Database;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import util.SkillLevel;
import util.User;

public class SignupController {
    private TextField emailField;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    ComboBox<String> skillLevelBox = new ComboBox<>();
//    skillLevelBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");

    public void initialize() {
//        skillLevelBox.setItems(FXCollections.observableArrayList(SkillLevel.values()));
//        skillLevelBox.setValue(SkillLevel.INTERMEDIATE); // default
    }

    public void handleSignup() {
        // validate fields and password confirmation
        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            char[] password = passwordField.getText().toCharArray();
            // if the database is empty, this user becomes the admin
            boolean isFirstUser = Database.isEmpty();
            User newUser = new User(usernameField.getText(), emailField.getText(), passwordField.getText().toCharArray(), skillLevelBox.getValue(), false);
            newUser.setEmail(emailField.getText());
            newUser.setUsername(usernameField.getText());
//            newUser.setSkillLevel(skillLevelBox.getValue().name());


            Database.addUser(newUser);
        }
    }
}




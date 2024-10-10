package GUI;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import database.Database;
import javafx.collections.FXCollections;
import util.SkillLevel;
import util.User;

public class SignupController {
    private TextField emailField;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private ComboBox<String> skillLevelBox = new ComboBox<>();


    public void initialize() {
        skillLevelBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
        skillLevelBox.setValue("Intermediate"); // default
    }

    public void handleSignup() {
        if (usernameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            return;
        }

        if (passwordField.getText().equals(confirmPasswordField.getText())) {
            char[] password = passwordField.getText().toCharArray();
            boolean isFirstUser = Database.isEmpty();
            User newUser = new User(usernameField.getText(), emailField.getText(), password, false, null, skillLevelBox.getValue(), isFirstUser, false, false);

            newUser.setEmail(emailField.getText());
            newUser.setUsername(usernameField.getText());
            newUser.setSkillLevel(skillLevelBox.getValue());

            Database.addUser(newUser);
        } else {

        }
    }
}
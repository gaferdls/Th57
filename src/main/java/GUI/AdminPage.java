package GUI;


import java.util.UUID;

import database.Database;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Role;
import util.User;

public class AdminPage {
    private TextField inviteUsernameField;
    private ComboBox<Role> inviteRoleBox;
    private Label invitationCodeLabel;

    public void initialize() {
        inviteRoleBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    public void show(Stage primaryStage) {

    }

    public void handleInviteUser() {
        String username = inviteUsernameField.getText();
        Role role = inviteRoleBox.getValue();
        String oneTimeCode = generateOneTimeCode();

        User newUser = new User(username, "", "", "", "", "", new char[0], true, null, "Intermediate", false, false, false, null);

        // assign the user to a role
        if (role.equals(Role.ADMIN)) newUser.setAdmin(true);
        if (role.equals(Role.INSTRUCTOR)) newUser.setInstructor(true);
        if (role.equals(Role.STUDENT)) newUser.setStudent(true);

        Database.addUser(newUser);
        
        invitationCodeLabel.setText("Invite Code: " + oneTimeCode);
        // Provide the one-time code to the user
    }

    private String generateOneTimeCode() {
        // Generate a random one-time code (UUID or custom logic)
        return UUID.randomUUID().toString();
    }
}

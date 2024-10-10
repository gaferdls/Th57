package GUI;

import java.awt.TextField;

import database.Database;
import javafx.collections.FXCollections;
//import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import util.Role;

public class AdminController {
    private TextField inviteUsernameField;
    private ComboBox<Role> inviteRoleBox;
    
    public void initialize() {
        inviteRoleBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    public void handleInviteUser() {
        String username = inviteUsernameField.getText();
        Role role = inviteRoleBox.getValue();
        char[] oneTimeCode = generateOneTimeCode().toCharArray();
        Database.inviteUser(username, role, oneTimeCode);
        // Provide the code to the invited user
    }

    private String generateOneTimeCode() {
        return null;
    }
}



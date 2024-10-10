import java.awt.TextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class AdminController {
    private TextField inviteUsernameField;
    private ComboBox<Role> inviteRoleBox;
    
    public void initialize() {
        inviteRoleBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    public void handleInviteUser() {
        String username = inviteUsernameField.getText();
        Role role = inviteRoleBox.getValue();
        String oneTimeCode = (String) generateOneTimeCode();
        Database.inviteUser(username, role, oneTimeCode);
        // Provide the code to the invited user
    }

    private String generateOneTimeCode() {
		return null;
        // Implement one-time code generation logic
    }
}



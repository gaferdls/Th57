import java.time.LocalDateTime;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdminPage {
    private TextField inviteUsernameField;
    private ComboBox<Role> inviteRoleBox;
    private Label invitationCodeLabel;

    public void initialize() {
        inviteRoleBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    public void handleInviteUser() {
        String username = inviteUsernameField.getText();
        Role role = inviteRoleBox.getValue();
        String oneTimeCode = generateOneTimeCode();

        User newUser = new User(username, null, true, LocalDateTime.now().plusHours(24));
        newUser.addRole(role);
        Database.addUser(newUser);
        
        invitationCodeLabel.setText("Invite Code: " + oneTimeCode);
        // Provide the one-time code to the user
    }

    private String generateOneTimeCode() {
        // Generate a random one-time code (UUID or custom logic)
        return UUID.randomUUID().toString();
    }
}

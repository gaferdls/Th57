import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Logic {
    private TextField usernameField;
    private PasswordField passwordField;
    private Label errorMessageLabel;

    public void handleLogin() {
        String username = usernameField.getText();
        User user = Database.findUserByUsername(username);

        if (user == null) {
            errorMessageLabel.setText("User not found.");
            return;
        }

        char[] enteredPassword = passwordField.getText().toCharArray();
        if (Arrays.equals(user.getPassword(), enteredPassword)) {
            if (user.isOneTimePassword() && user.isPasswordExpired()) {
                // Redirect to password reset page
                resetUserPassword(user);
            } else {
                // Check if the user has multiple roles
                if (user.getRoles().size() > 1) {
                    // Redirect to role selection page
                    redirectToRoleSelection(user);
                } else {
                    // Redirect to role-specific home page
                    redirectToHomePage(user.getRoles().get(0));
                }
            }
        } else {
            errorMessageLabel.setText("Incorrect password.");
        }
    }

    private void resetUserPassword(User user) {
        // Logic to reset password and flag it as a one-time password
    }

    private void redirectToRoleSelection(User user) {
        // Logic to redirect to the role selection screen
    }

    private void redirectToHomePage(Role role) {
        // Logic to redirect to the home page based on the selected role
    }
}

//package GUI;
//
//import java.awt.TextField;
//import java.time.LocalDateTime;
//
//import database.Database;
//import javafx.scene.control.PasswordField;
//import util.User;
//
//public class LoginController {
//    private TextField usernameField;
//    private PasswordField passwordField;
//
//    public void handleLogin() {
//        String username = usernameField.getText();
//        User user = Database.findUserByEmail(username);
//        if (user != null) {
//            if (user.isOneTimePassword() && System.currentTimeMillis() > user.getPasswordExpiration().getTime()) {
//                // Redirect to password reset page
//            } else {
//                if (Arrays.equals(user.getPassword(), passwordField.getText().toCharArray())) {
//                    // Successful login
//
//                        // Show role selection page
//                    } else {
//                        // Go to the home page based on the single role
//                    }
//                }
//            }
//        }
//    }

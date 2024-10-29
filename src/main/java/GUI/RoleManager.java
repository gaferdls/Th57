package GUI;
import database.Database;
import util.User;

public class RoleManager {
    private User user;

    public RoleManager(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return user.isAdmin();
    }

    public boolean isStudent() {
        return user.isStudent();
    }

    public boolean isInstructor() {
        return user.isInstructor();
    }

    public boolean hasMultipleRoles() {
        int count = 0;
        if (isAdmin()) count++;
        if (isInstructor()) count++;
        if (isStudent()) count++;
        return count > 1;
    }

    // Fetch user details if needed
    public void refreshUserDetails() {
        try {
            this.user = Database.findUserByUsername(user.getUsername());
        } catch (Exception e) {
            System.out.println("Error refreshing user details: " + e.getMessage());
        }
    }
}

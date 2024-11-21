import database.Database;
import database.DatabaseHelper;
import org.junit.jupiter.api.Test;
import util.User;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUsers {

    // Tests adding a user and then getting it from the database
    @Test
    public void testAddUser() {
        Database.init();
        Database.clearDatabase();

        // Add the new user
        User user = new User("username", "firstName", "middleName", "lastName", "preferredName", "email", "password".toCharArray(), false, null, "skillLevel", false, false, true, "groups");
        Database.addUser(user);

        // Get the new user from the database
        User user2 = Database.db.getUserInformationFromUsername(user.getUsername());
        Database.close();

        // Check that they are the same
        assertEquals(user, user2);
    }

}

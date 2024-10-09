package database;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class DatabaseTester {

    public static void main(String[] args) {

        DatabaseHelper db = new DatabaseHelper();

        try {
            db.connectToDatabase();
            System.out.println("success");

            if (!db.doesUserExist("test@example.org"))
                db.register("test@example.org", "Password123!", true, new Time(1, 30, 0), new Date(2024, 10, 8), "testuser", 3);

            System.out.println("test user does exist: " + db.doesUserExist("test@example.org"));

            System.out.println("logging in to test user: " + db.login("test@example.org", "Password123!"));

            System.out.println("password for test user: " + db.getUserPassword("test@example.org"));

            db.changeUserPassword("test@example.org", "verysecurepassword");
            System.out.println("password for test user: " + db.getUserPassword("test@example.org"));

            db.displayUsers();

            db.closeConnection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

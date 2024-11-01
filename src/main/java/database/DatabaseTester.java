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
                db.register("test@example.org","", "", "", "", "Password123!".toCharArray(), true, new Time(1, 30, 0), "testuser", "Intermediate", false, false, false);

            System.out.println("test user does exist: " + db.doesUserExist("test@example.org"));

            System.out.println("logging in to test user: " + db.login("test@example.org", "Password123!".toCharArray()));

            System.out.println("password for test user: " + String.valueOf(db.getUserPassword("test@example.org")));

            db.changeUserPassword("test@example.org", "verysecurepassword".toCharArray());
            System.out.println("password for test user: " + String.valueOf(db.getUserPassword("test@example.org")));

            db.displayUsers();

            db.closeConnection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

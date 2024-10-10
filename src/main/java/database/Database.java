package database;

import util.Role;
import util.User;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;

public class Database {

	private static Database instance;
	private static DatabaseHelper db;

	// Initialize the database
	public static void init() {
		db = new DatabaseHelper();
		try {
			db.connectToDatabase();
			System.out.println("connected to database!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean inviteUser(String username, Role role, char[] oneTimeCode) {
		try {
			db.register(username,"", "", "", "", oneTimeCode, true, new Time(Time.from(Instant.now()).getTime()), new Date(Date.from(Instant.now().plusSeconds(86400 * 3)).getTime()), "", "Intermediate", false, false, false);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}


	public static User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return db.getUserInformationFromEmail(email);
	}



	public static boolean addUser(User user) {
		try {
			db.register(user.getUsername(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPreferredName(), user.getPassword(), user.isOneTimePassword(), null, null, user.getUsername(), user.getSkillLevel(), user.isAdmin(), user.isStudent(), user.isInstructor());
			System.out.println("added user " + user.getUsername());
		} catch (SQLException e) {
			System.out.println("could not add user: " + e.getMessage());
			return false;
		}
		return true;
	}

	public static boolean isEmpty() {
        try {
            return db.isDatabaseEmpty();
        } catch (SQLException e) {
			System.out.println("it broked");
            return false;
        }
    }

	public static boolean hasUser(String username) {
		return db.doesUserExist(username);
	}

}

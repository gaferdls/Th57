package database;

import util.Role;
import util.User;

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
			db.register(username,"", "", "", "", oneTimeCode, true, new Time(Time.from(Instant.now()).getTime()), "", "Intermediate", false, false, false);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}


	public static User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return db.getUserInformationFromUsername(username);
	}

	public static boolean updateUser(User user) {
		System.out.println("Attempting to update user with email: " + user.getEmail());
		return db.updateUser(user);
	}

	public static boolean addUser(User user) {
		try {
			db.register(user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPreferredName(), user.getPassword(), user.isOneTimePassword(), null, user.getUsername(), user.getSkillLevel(), user.isAdmin(), user.isStudent(), user.isInstructor());
			System.out.println("added user " + user.getUsername());
			db.displayUsers();
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
	public static void displayAllUsers() {
		try {
			db.displayUsers();
		} catch (SQLException e) {
			System.out.println("Error displaying users: " + e.getMessage());
		}
	}


}

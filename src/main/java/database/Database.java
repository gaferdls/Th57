package database;

import util.User;
import util.Article;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import util.HelpMessage;

public class Database {

	private static Database instance;
	public static DatabaseHelper db;

	// Initialize the database
	public static void init() {
		db = new DatabaseHelper();
		try {
			db.connectToDatabase();
			System.out.println("connected to database!");
			Article article = new Article("Expert", "Eclipse", "EclipseTut", "How to set up eclipse", "body", "coding", "carter", "g");
			//db.addToArticleDatabase(article);
			db.searchArticlesByGroupingID("Eclipse");




		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addRequest(HelpMessage message){
		try{
			db.addRequest(message);
			System.out.println("Request added!");
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static boolean inviteUser(String username, String role, char[] oneTimeCode) {
		try {
			db.register(username,"", "", "", "", oneTimeCode, true, new Time(Time.from(Instant.now()).getTime()), "", "Intermediate", false, false, false, null);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	public static ArrayList<Article> allArticles() {
		ArrayList<Article> articles = new ArrayList<>(); // Create a new ArrayList to hold articles


		articles = db.getAllArticles(); // Retrieve articles from the database


		return articles; // Return the ArrayList, even if it's empty
	}


	public static void addArticle(Article article){
		try {
			db.addToArticleDatabase(article);
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}


	public static User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return db.getUserInformationFromUsername(username);
	}
	public static User findUserByID(int id){
		return db.getUserInformationFromId(id);
	}
	public static boolean updateUser(User user) {
		System.out.println("Attempting to update user with email: " + user.getEmail());
		return db.updateUser(user);
	}

	public static boolean addUser(User user) {
		try {
			db.register(user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getPreferredName(), user.getPassword(), user.isOneTimePassword(), null, user.getUsername(), user.getSkillLevel(), user.isAdmin(), user.isStudent(), user.isInstructor(), user.getGroups());
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


	public static void deleteUser(User user) {
		try {
			if (user != null && user.getUsername() != null) {
				db.deleteUser(user.getUsername());
				System.out.println("Successfully deleted user: " + user.getUsername());
			} else {
				System.out.println("Cannot delete: Invalid user object");
			}
		} catch (SQLException e) {
			System.out.println("Error deleting user: " + e.getMessage());
		}
	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		try {
			users = db.getAllUsers();
		} catch (SQLException e) {
			System.out.println("Error retrieving users: " + e.getMessage());
		}
		return users;
	}

	public static void close() {
		if (db != null) {
			db.closeConnection();
			db = null;
		}
	}

	public static boolean clearDatabase() {
		return db.clearDatabase();
	}

	public static void updateArticle(Article updatedArticle) {
		try {
			if (updatedArticle == null) {
				System.out.println("Cannot update: Article is null");
				return;
			}

			// Check if article exists before updating
			Article existingArticle = db.getArticleByTitle(updatedArticle.getTitle());
			if (existingArticle == null) {
				System.out.println("Cannot update: Article not found with title: " + updatedArticle.getTitle());
				return;
			}

			db.updateArticle(updatedArticle);
			System.out.println("Successfully updated article: " + updatedArticle.getTitle());

		} catch (SQLException e) {
			System.out.println("Error updating article: " + e.getMessage());
		}
	}
}
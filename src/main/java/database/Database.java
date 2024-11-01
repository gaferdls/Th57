package database;

import util.Role;
import util.User;
import util.Article;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;

public class Database {

	private static Database instance;
	public static DatabaseHelper db;

	// Initialize the database
	public static void init() {
		db = new DatabaseHelper();
		try {
			db.connectToDatabase();
			System.out.println("connected to database!");
			Article article = new Article("Expert", "Eclipse", "EclipseTut", "How to set up eclipse", "body", "coding", "carter");
			//db.addToArticleDatabase(article);
			db.searchArticlesByGroupingID("Eclipse");




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

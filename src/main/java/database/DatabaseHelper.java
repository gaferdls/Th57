package database;
import util.User;
import util.Article;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

// Code modified from FirstDatabaseMaven
public class DatabaseHelper {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/Th57";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    private Connection connection = null;
    private Statement statement = null;

    /**
     * Initialize a connection to the database.
     *
     * @throws SQLException
     */
    public void connectToDatabase() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER); // Load the JDBC driver
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            createTables();  // Create the necessary tables if they don't exist
            createArticlesTables();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        String userTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "email VARCHAR(255), "
                + "firstName VARCHAR(255), "
                + "middleName VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "preferredName VARCHAR(255), "
                + "password VARCHAR(255), "
                + "onetimepassword BIT, "
                + "expirationtime TIME, "
                + "username VARCHAR(255) UNIQUE, "
                + "level VARCHAR(255), "
                + "admin BIT, "
                + "student BIT, "
                + "instructor BIT, "
                + "groups VARCHAR(255))";
        statement.execute(userTable);
    }

    public void updateArticle() {

    }

    public void deleteArticle() {

    }

    public boolean backupToFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (Article article : getAllArticles()) {
                writer.write(String.join(";",
                        new String(article.getTitle()),
                        new String(article.getGroupId()),
                        new String(article.getShortDescription()),
                        new String(article.getKeywords()),
                        new String(article.getBody()),
                        new String(article.getReferences()),
                        new String(article.getLevel()),
                        new String(article.getGroups())
                ));
                writer.newLine();
            }
            writer.flush();
            System.out.println("Backup completed successfully to " + filename);
            return true;
        } catch (Exception e) {
            System.out.println("failed to backup to " + filename);
            return false;
        }
    }

    public boolean restoreFromFile(String filename) {
        try {
            String sql = "DELETE FROM articles";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 8);
                if (parts.length == 8) {
                    System.out.println("restoring article " + parts[0]);
                    addToArticleDatabase(new Article(parts[6], parts[1], parts[0], parts[2], parts[4], parts[3], parts[5], parts[7]));
                }
            }
            System.out.println("restored from file " + filename);
            return true;
        } catch (Exception e) {
            System.out.println("failed to restore from " + filename);
            return false;
        }
    }

    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        String sql = "SELECT level, groupingID, title, short, body, keywords, references, groups FROM articles";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create an Article object for each row in the ResultSet
                Article article = new Article(
                        rs.getString("level"),
                        rs.getString("groupingID"),
                        rs.getString("title"),
                        rs.getString("short"),
                        rs.getString("body"),
                        rs.getString("keywords"),
                        rs.getString("references"),
                        rs.getString("groups")
                );
                articles.add(article); // Add article to the ArrayList
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving articles: " + e.getMessage());

        }

        return articles; // Return the populated ArrayList
    }


    public List<Article> searchArticlesByGroupingID(String groupingID) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT level, groupingID, title, short, body, keywords, references, groups FROM articles WHERE groupingID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, groupingID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create an Article object for each row in the ResultSet
                Article article = new Article(
                        rs.getString("level"),
                        rs.getString("groupingID"),
                        rs.getString("title"),
                        rs.getString("short"),
                        rs.getString("body"),
                        rs.getString("keywords"),
                        rs.getString("references"),
                        rs.getString("groups")
                );
                articles.add(article);
            }

            for (Article article : articles) {
                System.out.println(article.getShortDescription());
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving articles: " + e.getMessage());
            throw e;
        }

        return articles;
    }


    public void addToArticleDatabase(Article article) throws SQLException {
        // SQL query to insert a new article
        String sql = "INSERT INTO articles (level, groupingID, title, short, body, keywords, references, groups) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to ensure proper resource management
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
            pstmt.setString(1, article.getLevel());
            pstmt.setString(2, article.getGroupId());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getShortDescription());
            pstmt.setString(5, article.getBody());
            pstmt.setString(6, article.getKeywords());
            pstmt.setString(7, article.getReferences());
            pstmt.setString(8, article.getGroups());

            // Execute the insert operation
            pstmt.executeUpdate();
            System.out.println("Article added successfully!");
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Error adding article: " + e.getMessage());
            throw e; // Optionally rethrow the exception after logging it
        }
    }


    /**
     * Check if a database is empty.
     *
     * @return true if there are no registered users in the database.
     * @throws SQLException
     */
    public boolean isDatabaseEmpty() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM users";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getInt("count") == 0;
        }
        return true;
    }

    /**
     * Register a new user in the database
     *
     * @param email
     * @param password
     * @param isOneTimePassword
     * @param expirationTime
     * @param level             How experienced the user is  (0 = beginner, 1 = intermediate, 2 = advanced, 3 = expert)
     * @throws SQLException
     */
    public void register(String email, String firstName, String middleName, String lastName, String preferredName, char[] password, boolean isOneTimePassword, Time expirationTime, String username, String level, boolean admin, boolean student, boolean instructor, String groups) throws SQLException {
        String insertUser = "INSERT INTO users (email, firstName, middleName, lastName, preferredName, password, onetimepassword, expirationtime, username, level, admin, student, instructor, groups) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
            pstmt.setString(1, email);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, preferredName);
            pstmt.setString(6, String.valueOf(password));
            pstmt.setBoolean(7, isOneTimePassword);
            pstmt.setTime(8, expirationTime);
            pstmt.setString(9, username);
            pstmt.setString(10, level);
            pstmt.setBoolean(11, admin);
            pstmt.setBoolean(12, student);
            pstmt.setBoolean(13, instructor);
            pstmt.setString(14, groups);
            pstmt.executeUpdate();
        }
    }

    /**
     * Log in as a user.
     *
     * @param email
     * @param password
     * @return true if the login was successful
     * @throws SQLException
     */
    public boolean login(String email, char[] password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, String.valueOf(password));
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks if an email is already registered in the database.
     *
     * @return
     */
    public boolean doesUserExist(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // True if user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Default to false if the query fails
    }


    /**
     * Lists information about all users registered in the database.
     *
     * @throws SQLException
     */
    public void displayUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            // Retrieve by column name
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String firstName = rs.getString("firstName");
            String middleName = rs.getString("middleName");
            String lastName = rs.getString("lastName");
            String preferredName = rs.getString("preferredName");
            char[] password = rs.getString("password").toCharArray();
            boolean otp = rs.getBoolean("onetimepassword");
            Time otpTime = rs.getTime("expirationtime");
//            Date otpDate = rs.getDate("expirationdate");
            String name = rs.getString("username");
            String level = rs.getString("level");
            boolean admin = rs.getBoolean("admin");
            boolean student = rs.getBoolean("student");
            boolean instructor = rs.getBoolean("instructor");

            // Display values
            System.out.print("ID: " + id);
            System.out.print(", Email: " + email);
            System.out.print(", Password: " + String.valueOf(password));
//            System.out.print(", One-Time Password: " + otp);
//            System.out.print(", Expiration Time for OTP:" + otpTime.toString());
//            System.out.print(", Expiration Date for OTP:" + otpDate.toString());
//            System.out.print(", Name: " + name);
            System.out.print(", Level: " + level);
            System.out.print(", roles: " + admin + student + instructor);
        }
    }

    private void createArticlesTables() throws SQLException {
        String articlesTable = "CREATE TABLE IF NOT EXISTS articles ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT, " // Correct placement of AUTO_INCREMENT
                + "groupingID VARCHAR(4000), "
                + "level VARCHAR(4000), " // Added missing comma
                + "title VARCHAR(4000), "
                + "short VARCHAR(4000), "
                + "body VARCHAR(4000), "
                + "keywords VARCHAR(4000), "
                + "references VARCHAR(4000), "// Added missing comma
                + "groups VARCHAR(4000)"
                + ");";

        statement.execute(articlesTable);
    }


    public char[] getUserPassword(String email) throws SQLException {
        String sql = "SELECT password FROM users WHERE email='" + email + "'";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getString("password").toCharArray();
        }
        return new char[0];
    }

    public boolean changeUserPassword(String email, char[] newPassword) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE users SET password = '").append(newPassword).append("' WHERE email = '").append(email).append("'");
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql.toString()) != 0;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET email = ?, username = ?, firstName = ?, middleName = ?, lastName = ?, admin = ?, student = ?, instructor = ?, level = ?, password = ?, groups = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail()); // Include the email update
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getMiddleName());
            pstmt.setString(5, user.getLastName());
            pstmt.setBoolean(6, user.isAdmin());
            pstmt.setBoolean(7, user.isStudent());
            pstmt.setBoolean(8, user.isInstructor());
            pstmt.setString(9, user.getSkillLevel());
            pstmt.setString(10, String.valueOf(user.getPassword()));  // Ensure the password is being updated
            pstmt.setString(11, user.getGroups());  // Assuming username is used for identification
            pstmt.setString(12, user.getUsername());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Update failed. Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public void DisplayArticles() throws SQLException {

        String sql = "SELECT * FROM articles";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

    }


    public User getUserInformationFromUsername(String username) {
        String sql = "SELECT * FROM users WHERE username='" + username + "'";
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String firstName = rs.getString("firstName");
                String middleName = rs.getString("middleName");
                String lastName = rs.getString("lastName");
                String preferredName = rs.getString("preferredName");
                char[] password = rs.getString("password").toCharArray();
                boolean otp = rs.getBoolean("onetimepassword");
                Time otpTime = rs.getTime("expirationtime");
//                Date otpDate = rs.getDate("expirationdate");
//                String name = rs.getString("username");
                String level = rs.getString("level");
                boolean admin = rs.getBoolean("admin");
                boolean student = rs.getBoolean("student");
                boolean instructor = rs.getBoolean("instructor");
                String groups = rs.getString("groups");
                return new User(username, firstName, middleName, lastName, preferredName, email, password, otp, otpTime, level, admin, student, instructor, groups);
            }
        } catch (SQLException e) {
            System.out.println("could not find user for " + username);
        }
        return null;
    }

    /**
     * End the connection to the database
     */
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        try {
            if (connection != null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User successfully deleted: " + username);
            } else {
                System.out.println("No user found with username: " + username);
                throw new SQLException("User not found");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw e;
        }
    }

    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("firstName"),
                        rs.getString("middleName"),
                        rs.getString("lastName"),
                        rs.getString("preferredName"),
                        rs.getString("email"),
                        rs.getString("password").toCharArray(),
                        rs.getBoolean("onetimepassword"),
                        rs.getTime("expirationtime"),
                        rs.getString("level"),
                        rs.getBoolean("admin"),
                        rs.getBoolean("student"),
                        rs.getBoolean("instructor"),
                        rs.getString("groups")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            throw e;
        }

        return users;
    }
}

package database;
import util.User;

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
     * @throws SQLException
     */
    public void connectToDatabase() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER); // Load the JDBC driver
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            createTables();  // Create the necessary tables if they don't exist
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
                + "instructor BIT)";
        statement.execute(userTable);
    }


    /**
     * Check if a database is empty.
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
     * @param email
     * @param password
     * @param isOneTimePassword
     * @param expirationTime The expiration time for the one time password
     * @param expirationDate The expiration date for the one time password
     * @param name
     * @param level How experienced the user is  (0 = beginner, 1 = intermediate, 2 = advanced, 3 = expert)
     * @throws SQLException
     */
    public void register(String email, String firstName, String middleName, String lastName, String preferredName, char[] password, boolean isOneTimePassword, Time expirationTime, String username, String level, boolean admin, boolean student, boolean instructor) throws SQLException {
        String insertUser = "INSERT INTO users (email, firstName, middleName, lastName, preferredName, password, onetimepassword, expirationtime, username, level, admin, student, instructor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.executeUpdate();
        }
    }

    /**
     * Log in as a user.
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
     * @param email
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
     * @throws SQLException
     */
    public void displayUsers() throws SQLException{
        String sql = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()) {
            // Retrieve by column name
            int id  = rs.getInt("id");
            String  email = rs.getString("email");
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
        String sql = "UPDATE users SET username = ?, firstName = ?, middleName = ?, lastName = ?, admin = ?, student = ?, instructor = ?, level = ?, password = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getMiddleName());
            pstmt.setString(4, user.getLastName());
            pstmt.setBoolean(5, user.isAdmin());
            pstmt.setBoolean(6, user.isStudent());
            pstmt.setBoolean(7, user.isInstructor());
            pstmt.setString(8, user.getSkillLevel());
            pstmt.setString(9, String.valueOf(user.getPassword()));  // Ensure the password is being updated
            pstmt.setString(10, user.getUsername());  // Assuming username is used for identification

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Update failed. Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




    public User getUserInformationFromUsername(String username) {
        String sql = "SELECT * FROM users WHERE username='" + username + "'";
        ResultSet rs;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int id  = rs.getInt("id");
                String  email = rs.getString("email");
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
                return new User(username, firstName, middleName, lastName, preferredName, email, password, otp, otpTime, level, admin, student, instructor);
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
        try{
            if(statement!=null) statement.close();
        } catch(SQLException se2) {
            se2.printStackTrace();
        }
        try {
            if(connection!=null) connection.close();
        } catch(SQLException se){
            se.printStackTrace();
        }
    }

}

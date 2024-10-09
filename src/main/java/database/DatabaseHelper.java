package database;
import java.sql.*;
import java.util.Arrays;

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
                + "email VARCHAR(255) UNIQUE, "
                + "password VARCHAR(255), "
                + "onetimepassword BIT, "
                + "expirationtime TIME, "
                + "expirationdate DATE, "
                + "name VARCHAR(255), "
                + "level TINYINT)";
        statement.execute(userTable);
    }


    /**
     * Check if a database is empty.
     * @return true if there are no registered users in the database.
     * @throws SQLException
     */
    public boolean isDatabaseEmpty() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM cse360users";
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
    public void register(String email, char[] password, boolean isOneTimePassword, Time expirationTime, Date expirationDate, String name, int level) throws SQLException {
        String insertUser = "INSERT INTO users (email, password, onetimepassword, expirationtime, expirationdate, name, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
            pstmt.setString(1, email);
            pstmt.setString(2, String.valueOf(password));
            pstmt.setBoolean(3, isOneTimePassword);
            pstmt.setTime(4, expirationTime);
            pstmt.setDate(5, expirationDate);
            pstmt.setString(6, name);
            pstmt.setShort(7, (byte) level);
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
    public boolean doesUserExist(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // If the count is greater than 0, the user exists
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // If an error occurs, assume user doesn't exist
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
            char[] password = rs.getString("password").toCharArray();
            boolean otp = rs.getBoolean("onetimepassword");
            Time otpTime = rs.getTime("expirationtime");
            Date otpDate = rs.getDate("expirationdate");
            String name = rs.getString("name");
            byte level = rs.getByte("level");

            // Display values
            System.out.print("ID: " + id);
            System.out.print(", Email: " + email);
            System.out.print(", Password: " + String.valueOf(password));
            System.out.print(", One-Time Password: " + otp);
            System.out.print(", Expiration Time for OTP:" + otpTime.toString());
            System.out.print(", Expiration Date for OTP:" + otpDate.toString());
            System.out.print(", Name: " + name);
            System.out.print(", Level: " + level);
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

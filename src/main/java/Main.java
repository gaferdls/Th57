import GUI.Username_GUI;
import database.Database;

public class Main {
    public static void main(String[] args) {
        try {
            Database.init();
            displayAllUsers();

            Username_GUI.main(args);
        } catch (Exception e) {
            System.out.println("whoops");
            e.printStackTrace();
        }
    }

    private static void displayAllUsers() {
        Database.displayAllUsers();
    }

}

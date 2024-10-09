import GUI.Username_GUI;

public class Main {
    public static void main(String[] args) {
        try {
//            TestUI.go(args);
            Username_GUI.main(args);
        } catch (Exception e) {
            System.out.println("whoops");
            e.printStackTrace();
        }
    }
}

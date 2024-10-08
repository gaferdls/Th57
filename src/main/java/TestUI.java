import javafx.application.Application;
import javafx.stage.Stage;

public class TestUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("ok");
    }

    public static void go(String[] args) {
        launch(args);
    }
}

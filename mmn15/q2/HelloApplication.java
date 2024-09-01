import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello, Pedestrians!");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(re -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: Exactly two arguments are needed for this program, one for red light cycle, one for green light cycle.");
            System.exit(0);
        }

        long redCycle, greenCycle;
        try {
            redCycle = Long.parseLong(args[0]);
            greenCycle = Long.parseLong(args[1]);
            HelloController.setCycles(redCycle, greenCycle);
            launch();
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format.");
        }
    }
}
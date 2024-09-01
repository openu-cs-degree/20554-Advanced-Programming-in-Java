
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientController controller = fxmlLoader.getController();
        if (!controller.parseArguments(getParameters().getRaw().toArray(new String[0]))) {
            System.exit(0);
        }

        stage.setTitle("Mi Rotse Lihyot Milyoner");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(re -> {
            System.exit(0);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
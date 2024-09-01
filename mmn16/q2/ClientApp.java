import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client View");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(re -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
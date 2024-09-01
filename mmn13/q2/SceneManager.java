import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that holds the scene management logic of the entire program.
 */
public class SceneManager {
    public static final String MENU_FXML_PATH = "menu.fxml";
    public static final String SAVE_FXML_PATH = "save.fxml";

    private static void switchToScene(String fxmlPath, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlPath)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.sizeToScene();
    }

    public static void switchToMenu(ActionEvent event) throws IOException {
        switchToScene(MENU_FXML_PATH, event);
    }

    public static void switchToSave(ActionEvent event) throws IOException {
        switchToScene(SAVE_FXML_PATH, event);
    }
}

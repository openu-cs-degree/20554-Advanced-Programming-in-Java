import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that holds the scene management logic of the entire program.
 */
public class SceneManager {
    public static final String CHOICE_FXML_PATH = "choice.fxml";
    public static final String NOTEBOOK_FXML_PATH = "notebook.fxml";
    public static Stage currentStage;

    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    private static void switchToScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlPath)));
            currentStage.setScene(new Scene(root));
            currentStage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchToChoice() {
        switchToScene(CHOICE_FXML_PATH);
        currentStage.setTitle("Choose a notebook");
    }

    public static void switchToNotebook(String fileName) {
        switchToScene(NOTEBOOK_FXML_PATH);
        currentStage.setTitle("Showing notebook " + fileName);
    }
}

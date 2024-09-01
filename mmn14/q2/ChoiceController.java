import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Controller class for choosing-notebook scene
 */
public class ChoiceController {
    public void newNotebook() {
        TextInputDialog dialog = new TextInputDialog("my notebook");
        dialog.setHeaderText(null);
        dialog.setContentText("Please provide new notebook's name:");
        dialog.showAndWait().ifPresent(fileName -> {
            NotebookManager.createNotebook(new File(fileName));
            SceneManager.switchToNotebook(fileName);
        });
    }

    public void openNotebook() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(SceneManager.currentStage);
        if (file != null) {
            NotebookManager.loadNotebook(file);
        }
    }
}
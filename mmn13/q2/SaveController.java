import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class that holds control methods for the save scene
 */
public class SaveController {
    @FXML
    private TextField labelName;

    private static final String USER_NAME_REGEX_FORMAT = "^[a-zA-Z]+[0-9]+$"; // one or more letter followed by one or more digits

    /**
     * If the TextField's text is in the correct format, save the file and return to menu.
     * Otherwise, alert the incorrectness to the user.
     */
    public void saveAndSwitchToMenu(ActionEvent event) throws IOException {
        String name = labelName.getText();
        if (name.matches(USER_NAME_REGEX_FORMAT)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(name)))) {
                writer.write(MenuController.currentOrder.toString());
                SceneManager.switchToMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
                IOManager.showAlert("Error!", "An IO error occurred, the order was not saved.");
            }
        } else {
            IOManager.showAlert("Wrong format", "The name was saved in a wrong format. \nPlease insert in format \"name123456789\".");
        }

    }
}

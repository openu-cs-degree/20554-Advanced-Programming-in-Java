import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable {

    @FXML private TextArea textAreaMessage;

    private Server server;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        server = new Server();
        server.start();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        server.broadcastMessage(textAreaMessage.getText());
    }
}

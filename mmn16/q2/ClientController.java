import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController implements Initializable {
    @FXML private TextArea textAreaMessage;
    @FXML private TextField textFieldIP;

    private Client client;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        client = new Client(this);
        client.start();
    }

    public void sign() {
        sendMessage(Server.SIGN_CLIENT);
    }

    public void resign() {
        sendMessage(Server.RESIGN_CLIENT);
    }

    public void clear() {
        textAreaMessage.setText("");
    }

    public void setMessage(String message) {
        Platform.runLater(() -> textAreaMessage.setText(message));
    }
    
    private void sendMessage(String message) {
        client.sendMessage(message, getIP());
    }

    private String getIP() {
        return textFieldIP.getText();
    }
}

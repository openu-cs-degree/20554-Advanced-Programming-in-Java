import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    // declaring static variables
    private static long redTime;
    private static long greenTime;

    // declaring traffic lights by this order: u(pper), l(eft), r(right), b(ottom).

    @FXML private Circle uCarGreen;
    @FXML private Circle uCarRed;
    @FXML private Circle uPedGreen;
    @FXML private Circle uPedRed;
    TrafficLight upperLight;

    @FXML private Circle lCarGreen;
    @FXML private Circle lCarRed;
    @FXML private Circle lPedGreen;
    @FXML private Circle lPedRed;
    TrafficLight leftLight;

    @FXML private Circle rCarGreen;
    @FXML private Circle rCarRed;
    @FXML private Circle rPedGreen;
    @FXML private Circle rPedRed;
    TrafficLight rightLight;

    @FXML private Circle bCarGreen;
    @FXML private Circle bCarRed;
    @FXML private Circle bPedGreen;
    @FXML private Circle bPedRed;
    TrafficLight bottomLight;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        upperLight  = new TrafficLight(uCarGreen, uCarRed, uPedGreen, uPedRed, true);
        leftLight   = new TrafficLight(lCarGreen, lCarRed, lPedGreen, lPedRed, false);
        rightLight  = new TrafficLight(rCarGreen, rCarRed, rPedGreen, rPedRed, false);
        bottomLight = new TrafficLight(bCarGreen, bCarRed, bPedGreen, bPedRed, true);
        LightsManager.initialize(redTime, greenTime, upperLight, leftLight, rightLight, bottomLight);
        LightsManager.startToggling();
    }

    public static void setCycles(long redCycle, long greenCycle) {
        redTime = redCycle;
        greenTime = greenCycle;
    }
}
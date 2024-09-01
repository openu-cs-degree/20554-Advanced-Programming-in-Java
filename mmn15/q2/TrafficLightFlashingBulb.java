import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLightFlashingBulb extends TrafficLightBulb {
    private boolean isFlashing;
    public TrafficLightFlashingBulb(Circle bulb, Color color, boolean isStartingOn) {
        super(bulb, color, isStartingOn);
        isFlashing = true;
    }

    @Override
    protected Color getCurrentColor() {
        return isOn && isFlashing ? COLOR_ON : COLOR_OFF;
    }

    public void flash() {
        isFlashing = !isFlashing;
        changeColor();
    }
}

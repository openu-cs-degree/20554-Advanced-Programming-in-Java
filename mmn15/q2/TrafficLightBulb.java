import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLightBulb {
    protected final Circle bulb;
    protected final Color COLOR_ON;
    protected static final Color COLOR_OFF = Color.web("000000");
    protected boolean isOn;

    public TrafficLightBulb(Circle bulb, Color color, boolean isStartingOn) {
        this.bulb = bulb;
        this.COLOR_ON = color;
        isOn = isStartingOn;
    }

    public void changeColor() {
        isOn = !isOn;
        bulb.setFill(getCurrentColor());
    }

    protected Color getCurrentColor() {
        return isOn ? COLOR_ON : COLOR_OFF;
    }
}

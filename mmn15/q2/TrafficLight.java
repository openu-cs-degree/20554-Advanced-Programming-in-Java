import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLight {
    private static final Color COLOR_GREEN = Color.web("1df82b");
    private static final Color COLOR_RED = Color.web("ff1f1f");

    private final TrafficLightBulb carRed;
    private final TrafficLightBulb carGreen;
    private final TrafficLightBulb pedRed;
    private final TrafficLightBulb pedGreen;

    public TrafficLight(Circle carGreen, Circle carRed, Circle pedGreen, Circle pedRed, boolean isCarStarting) {
        this.carRed   = new TrafficLightBulb(carRed  , COLOR_RED  , !isCarStarting);
        this.carGreen = new TrafficLightBulb(carGreen, COLOR_GREEN, isCarStarting);
        this.pedRed   = new TrafficLightBulb(pedRed  , COLOR_RED  , isCarStarting);
        this.pedGreen = new TrafficLightFlashingBulb(pedGreen, COLOR_GREEN, !isCarStarting);
    }

    public void changeColor() {
        carRed.changeColor();
        carGreen.changeColor();
        pedRed.changeColor();
        pedGreen.changeColor();
    }

    public void flashPedestrian() {
        ((TrafficLightFlashingBulb)pedGreen).flash();
    }
}

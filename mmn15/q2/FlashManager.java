import java.util.ArrayList;
import java.util.Arrays;

// a singleton that flashes the pedestrians' green lights every 200 miliseconds
public class FlashManager extends Thread {
    private static FlashManager instance;
    private static final long CYCLE = 200; // ms
    private static ArrayList<TrafficLight> trafficLights;

    private FlashManager(TrafficLight... lights) {
        trafficLights = new ArrayList<>(Arrays.asList(lights));
    }

    public static void initialize(TrafficLight... lights) {
        if (instance == null) {
            instance = new FlashManager(lights);
        }
    }

    public static void startToggling() {
        instance.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(CYCLE);
                trafficLights.stream().forEach(TrafficLight::flashPedestrian);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

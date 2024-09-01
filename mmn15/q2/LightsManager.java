import java.util.ArrayList;
import java.util.Arrays;

// a singleton that toggles lights change every pre-defined amounts of miliseconds
public class LightsManager extends Thread {
    private static LightsManager instance;
    private static long redTime;
    private static long greenTime;
    private static ArrayList<TrafficLight> trafficLights;

    private LightsManager(long redTime, long greenTime, TrafficLight... lights) {
        LightsManager.redTime = redTime;
        LightsManager.greenTime = greenTime;
        trafficLights = new ArrayList<>(Arrays.asList(lights));
    }

    public static void initialize(long redTime, long greenTime, TrafficLight... lights) {
        if (instance == null) {
            synchronized (LightsManager.class) {
                if (instance == null) {
                    instance = new LightsManager(redTime, greenTime, lights);
                    FlashManager.initialize(lights);
                }
            }
        }
    }

    public static void startToggling() {
        instance.start();
        FlashManager.startToggling();
    }

    @Override
    public void run() {
        while (true) {
            try {
                trafficLights.stream().forEach(TrafficLight::changeColor);
                sleep(redTime);
                trafficLights.stream().forEach(TrafficLight::changeColor);
                sleep(greenTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

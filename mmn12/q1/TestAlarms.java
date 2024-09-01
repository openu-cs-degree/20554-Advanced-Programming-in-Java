import java.util.ArrayList;

public class TestAlarms {
    public void process(ArrayList<Alarm> list) {
        int smokeCount = 0;
        for (Alarm a : list) {
            a.action();
            if (a instanceof Smoke && !(a instanceof Fire)) {
                smokeCount++;
            } else if (a instanceof Elevator) {
                ((Elevator) a).reset();
            }
        }
        System.out.println(smokeCount + " smoke alarms were detected.");
    }
}

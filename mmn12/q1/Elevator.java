public class Elevator extends Alarm {
    private int floor;
    public Elevator(String address, int floor) throws BadAlarm {
        super(address);
        this.floor = floor;
    }

    @Override
    public void action() {
        System.out.println("Fire " + generalAlarmDescription() + " at floor " + floor + ".");
    }

    public void reset() {
        floor = 0;
    }
}

public class Fire extends Smoke {
    private boolean active;
    public Fire(String address, String activator) throws BadAlarm {
        super(address, activator);
        active = true;
    }

    @Override
    public void action() {
        active = false;
        System.out.println("Fire " + generalAlarmDescription() + ".");
    }
}

public class Smoke extends Alarm {
    protected final String activator;
    public Smoke(String address, String activator) throws BadAlarm {
        super(address);
        this.activator = activator;
    }

    @Override
    public void action() {
        System.out.println("Smoke " + generalAlarmDescription() + " by activator " + activator + ".");
    }
}

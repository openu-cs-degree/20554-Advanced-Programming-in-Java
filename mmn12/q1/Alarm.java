import java.util.Date;

public abstract class Alarm {
    protected final Date activationTime;
    protected final String address;
    public Alarm(String address) throws BadAlarm {
        if (address == null) {
            throw new BadAlarm();
        }
        this.address = address;
        activationTime = new Date();
    }
    protected final String generalAlarmDescription() {
        return "alarm was activated at " + activationTime.toString().split("\\s+")[3] + " on " + address;
    }
    public abstract void action();
}

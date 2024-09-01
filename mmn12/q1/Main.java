import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws BadAlarm {
        ArrayList<Alarm> list = new ArrayList<Alarm>();

        list.add(new Smoke("street 1", "yoni"));
        list.add(new Smoke("street 2", "yoni"));
        list.add(new Smoke("street 1", "yana"));
        list.add(new Smoke("street 2", "yana"));
        list.add(new Fire("street 1", "yoni"));
        list.add(new Fire("street 2", "yana"));
        list.add(new Fire("street 3", "yene"));
        list.add(new Fire("street 4", "yuno"));
        list.add(new Fire("street 3", "yani"));
        list.add(new Elevator("street 1", 1));
        list.add(new Elevator("street 2", 0));
        list.add(new Elevator("street 3", 2));
        list.add(new Elevator("street 1", 3));
        list.add(new Elevator("street 2", 1));
        list.add(new Elevator("street 3", 0));
        list.add(new Elevator("street 4", 2));
        try { list.add(new Smoke(null, null)); }   catch (BadAlarm e) {System.out.println("Couldn't add Smoke Alarm!"); }
        try { list.add(new Smoke(null, "null")); } catch (BadAlarm e) {System.out.println("Couldn't add Smoke Alarm!"); }
        try { list.add(new Smoke(null, "1234")); } catch (BadAlarm e) {System.out.println("Couldn't add Smoke Alarm!"); }
        try { list.add(new Fire(null, null)); }    catch (BadAlarm e) {System.out.println("Couldn't add Fire Alarm!"); }
        try { list.add(new Fire(null, "null")); }  catch (BadAlarm e) {System.out.println("Couldn't add Fire Alarm!"); }
        try { list.add(new Fire(null, "1234")); }  catch (BadAlarm e) {System.out.println("Couldn't add Fire Alarm!"); }
        try { list.add(new Elevator(null, 0)); }      catch (BadAlarm e) {System.out.println("Couldn't add Elevator Alarm!"); }
        try { list.add(new Elevator(null, 1)); }      catch (BadAlarm e) {System.out.println("Couldn't add Elevator Alarm!"); }
        try { list.add(new Elevator(null, 2)); }      catch (BadAlarm e) {System.out.println("Couldn't add Elevator Alarm!"); }
        list.add(new Smoke("street 1", "yoni"));
        list.add(new Fire("street 1", "yoni"));
        list.add(new Elevator("street 1", 1));

        new TestAlarms().process(list);
    }
}

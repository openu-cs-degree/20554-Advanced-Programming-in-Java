import java.util.HashMap;
import java.util.Set;

public final class Data {
    public static final HashMap<Integer, int[]> records = new HashMap<Integer, int[]>(){{
        put(2017, new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12});
        put(2018, new int[]{11,22,43,54,45,16,27,18,29,10,11,12});
        put(2019, new int[]{21,32,33,54,35,36,27,28,19,10,11,12});
        put(2020, new int[]{31,42,23,64,25,46,27,48,19,10,11,12});
        put(2021, new int[]{41,12,13,34,15,26,27,38, 9,10,11,12});
    }};
    public static final Integer[] years = records.keySet().toArray(new Integer[0]);
}

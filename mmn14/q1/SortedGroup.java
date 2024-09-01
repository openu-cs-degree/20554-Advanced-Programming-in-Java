import java.util.ArrayList;
import java.util.Iterator;

/**
 * Answer to branch a (סעיף א)
 */
public class SortedGroup<T extends Comparable<T>> implements Iterable<T> {
    private ArrayList<T> data;

    public SortedGroup() {
        data = new ArrayList<>();
    }

    public void add(T element) {
        int index = 0;
        while (index < data.size() && data.get(index).compareTo(element) < 0) {
            index++;
        }
        data.add(index, element);
    }

    public int remove(T element) {
        int oldSize = data.size();
        data.removeIf(e -> e.equals(element));
        return oldSize - data.size();
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}

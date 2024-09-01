import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * A class that represents a menu, logic-wise.
 */
public class Menu {

    /**
     * Each Dish's unique id is its index in the array
     */
    private final Dish[] dishes;

    public Menu(int dishCount) {
        dishes = new Dish[dishCount];
    }

    public Dish getDish(int id) {
        return dishes[id];
    }

    public int getSize() {
        return dishes.length;
    }

    public void addDish(int id, DishType type, String description, int price) {
        assert id < dishes.length;
        dishes[id] = new Dish(description, type, price);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        Arrays.stream(dishes).map(Dish::toString).forEach(joiner::add);
        return joiner.toString();
    }
}

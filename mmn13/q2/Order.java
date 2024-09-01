import javafx.util.Pair;
import java.util.ArrayList;

/**
 * A class that represents an order, logic-wise.
 */
public class Order {
    // it would be more convenient to use HashMap<Dish, Integer> in here
    private final ArrayList<Pair<Dish, Integer>> dishes; // array of <dish, quantity>
    private int totalPrice;

    public Order() {
        dishes = new ArrayList<>();
        totalPrice = 0;
    }

    public void addDish(Dish dish, int quantity) {
        dishes.add(new Pair<>(dish, quantity));
        totalPrice += dish.getPrice() * quantity;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        dishes.forEach(pair -> stringBuilder
                .append(pair.getValue() < 10 ? "0" : "")
                .append(pair.getValue())
                .append(" x ")
                .append(pair.getKey().getDescription())
                .append("\t\t")
                .append(pair.getKey().getPrice() * pair.getValue())
                .append("₪\n"));
        stringBuilder.append("Total price: ").append(totalPrice).append("₪.");
        return stringBuilder.toString();
    }
}

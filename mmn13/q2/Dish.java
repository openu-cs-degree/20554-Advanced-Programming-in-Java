/**
 * A class that represents a dish, logic-wise.
 */
public class Dish {
    private final String description;
    private final DishType type;
    private final int price;

    public Dish(String description, DishType type, int price) {
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public DishType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Dish[")
                .append("description=").append(description).append(", ")
                .append("type=").append(type).append(", ")
                .append("price=").append(price).append(']');
        return stringBuilder.toString();
    }

}

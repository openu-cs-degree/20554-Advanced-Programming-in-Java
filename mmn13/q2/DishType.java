import java.util.Arrays;
import java.util.Optional;

/**
 * An enum representation of the Dish's possible types
 */
public enum DishType {
    STARTER("starter"),
    MAIN("main"),
    DESSERT("dessert"),
    DRINK("drink");

    private final String type;

    DishType(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    public static Optional<DishType> fromString(String text) {
        return Arrays.stream(DishType.values()) // iterate the possible values
                .filter(dishType -> dishType.getType().equalsIgnoreCase(text)) // filter the value(s) that match the text
                .findFirst(); // return the value that matched the text, if exists.
    }
}

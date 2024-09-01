import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

import java.util.stream.IntStream;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 * A class that's responsible for creating the GUI representation of a dish.
 * It provides only one public method, createDishFX, but its logic is a bit complicated,
 * so it uses a few private methods to make the code clearer.
 */
public class DishFXCreator {
    /**
     * public constants
     */
    public final static int MIN_DISH_QUANTITY = 1;
    public final static int MAX_DISH_QUANTITY = 99;
    public final static int DEFAULT_QUANTITY = 1;

    /**
     * private constants
     */
    private final static int DESCRIPTION_LABEL_MAX_WIDTH = 200;
    private final static int PRICE_LABEL_MAX_WIDTH = 30;
    private final static int COMBO_BOX_MAX_WIDTH = 60;

    /**
     * @param description dish's description to be visualized on screen
     * @return the Label representing that description
     */
    private static Label createDishDescriptionLabel(String description) {
        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(DESCRIPTION_LABEL_MAX_WIDTH);
        descriptionLabel.setTextAlignment(TextAlignment.LEFT);
        return descriptionLabel;
    }

    /**
     * @param price dish's price to be visualized on screen
     * @return the Label representing that price
     */
    private static Label createDishPriceLabel(int price) {
        Label priceLabel = new Label(price + "₪");
        priceLabel.setTextAlignment(TextAlignment.RIGHT);
        return priceLabel;
    }

    /**
     * @return a CheckBox representing whether the dish is checked
     */
    private static CheckBox createDishCheckBox() {
        CheckBox checkBox = new CheckBox();
        checkBox.setAlignment(Pos.TOP_CENTER);
        return checkBox;
    }

    /**
     * @return a ComboBox representing the dish's quantity
     */
    private static ComboBox<Integer> createDishComboBox() {
        ComboBox<Integer> comboBox = new ComboBox<>();
        IntStream.rangeClosed(MIN_DISH_QUANTITY, MAX_DISH_QUANTITY).forEach(comboBox.getItems()::add);
        comboBox.setValue(DEFAULT_QUANTITY);
        comboBox.setDisable(true);
        return comboBox;
    }


    /**
     * @param dish a Dish containing the dish's data
     * @return a GridPane holds the GUI representation of that data
     */
    private static GridPane createDishGrid(Dish dish) {
        GridPane dishGrid = new GridPane();
        dishGrid.setHgap(50);
        dishGrid.setPadding(new Insets(0, 0, 0, 0));

        ColumnConstraints col1 = new ColumnConstraints(DESCRIPTION_LABEL_MAX_WIDTH, DESCRIPTION_LABEL_MAX_WIDTH, DESCRIPTION_LABEL_MAX_WIDTH, Priority.NEVER, HPos.LEFT, true);
        ColumnConstraints col2 = new ColumnConstraints(PRICE_LABEL_MAX_WIDTH, PRICE_LABEL_MAX_WIDTH, PRICE_LABEL_MAX_WIDTH, Priority.NEVER, HPos.RIGHT, true);
        ColumnConstraints col3 = new ColumnConstraints(0, USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true);
        ColumnConstraints col4 = new ColumnConstraints(COMBO_BOX_MAX_WIDTH, COMBO_BOX_MAX_WIDTH, COMBO_BOX_MAX_WIDTH, Priority.NEVER, HPos.LEFT, true);
        dishGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        return dishGrid;
    }

    /**
     * @param id the Dish's unique id
     * @param dish the Dish's data
     * @return a GridPane that holds the visual representation of the given Dish
     */
    public static GridPane createDishFX(int id, Dish dish) {
        // create the four nodes to be inserted to the GridPane
        Label descriptionLabel = DishFXCreator.createDishDescriptionLabel(dish.getDescription());
        Label priceLabel = DishFXCreator.createDishPriceLabel(dish.getPrice());
        CheckBox checkBox = DishFXCreator.createDishCheckBox();
        ComboBox<Integer> comboBox = DishFXCreator.createDishComboBox();

        // CheckBox logic
        checkBox.selectedProperty().addListener((t ->
            comboBox.setDisable(!comboBox.isDisable())
        ));

        // create constraints for the four elements to be viewed in the right place on the GridPane
        GridPane.setConstraints(descriptionLabel, 0, 0);
        GridPane.setConstraints(priceLabel, 1, 0);
        GridPane.setConstraints(checkBox, 2, 0);
        GridPane.setConstraints(comboBox, 3, 0);

        // create the GridPane  and add the four nodes to it uniquely
        GridPane dishGrid = DishFXCreator.createDishGrid(dish);
        dishGrid.getChildren().addAll(descriptionLabel, priceLabel, checkBox, comboBox);
        dishGrid.setId(Integer.toString(id));

        return dishGrid;
    }
}

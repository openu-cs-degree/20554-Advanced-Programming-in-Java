import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * A class that holds control methods for the main scene
 */
public class MenuController implements Initializable {

    /**
     * VBox for each dish type
     */
    @FXML private VBox vboxStarters;
    @FXML private VBox vboxMain;
    @FXML private VBox vboxDesserts;
    @FXML private VBox vboxDrinks;
    private ArrayList<VBox> vBoxes; // arranges the four vBoxes together for more concise traversal

    public static Menu menu;
    public static Order currentOrder;

    /**
     * Create a GUI representation of a Dish and insert into GUI in the right vBox
     * @param id the Dish's unique id
     * @param dish a Dish (logic-wise representation of a dish)
     */
    private void createDishFX(int id, Dish dish) {
        var dishFX = DishFXCreator.createDishFX(id, dish);

        switch (dish.getType()) {
            case STARTER -> vboxStarters.getChildren().add(dishFX);
            case MAIN    -> vboxMain    .getChildren().add(dishFX);
            case DESSERT -> vboxDesserts.getChildren().add(dishFX);
            case DRINK   -> vboxDrinks  .getChildren().add(dishFX);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBoxes = new ArrayList<>(Arrays.asList(vboxStarters, vboxMain, vboxDesserts, vboxDrinks));
        IntStream.range(0, menu.getSize()).forEach(id -> createDishFX(id, menu.getDish(id)));
    }

    /**
     * @return a stream of all children (and grandchildren, recursively) of a given node
     */
    private static Stream<Node> traverseNodes(Node node) {
        return Stream.concat(Stream.of(node), node instanceof Parent
                ? ((Parent) node).getChildrenUnmodifiable().stream().flatMap(MenuController::traverseNodes)
                : Stream.empty());
    }

    /**
     * Clears the GUI instead of re-draw it whenever an order is canceled
     */
    private void clearGUI() {
        vBoxes.forEach(vBox -> traverseNodes(vBox)
                .forEach(node -> {
                    if (node instanceof CheckBox) {
                        ((CheckBox)node).setSelected(false);
                    } else if (node instanceof ComboBox<?>) {
                        ((ComboBox<Integer>)node).setValue(DishFXCreator.DEFAULT_QUANTITY);
                    }
                }));
    }


    /**
     * @param gridPane a GridPane representation of a dish
     * @return whether it was checked by the user
     */
    private boolean isDishChecked(GridPane gridPane) {
        return gridPane.getChildren().stream()
                .filter(node -> node instanceof CheckBox)
                .map(node -> (CheckBox)node)
                .anyMatch(CheckBox::isSelected);
    }

    /**
     * @return an Order object, representing the user's order (dishes and quantities)
     */
    private Order createOrder() {
        Order order = new Order();

        vBoxes.forEach(vBox -> vBox.getChildren().stream()
                .filter(node -> node instanceof GridPane)   // get all Dish GUI representations
                .map(node -> (GridPane)node)                // cast them to GridPane
                .filter(this::isDishChecked)                // get only the checked gridPanes
                .forEach(gridPane -> order.addDish(         // add a new Dish
                        menu.getDish(Integer.parseInt(gridPane.getId())),
                        gridPane.getChildren().stream()
                                .filter(node -> node instanceof ComboBox<?>)    // get the ComboBox(es) holding quantity
                                .map(node -> (ComboBox<Integer>)node)           // cast them to ComboBox<Integer>
                                .findFirst()                                    // get the first (and only) ComboBox
                                .get().getValue()                               // use its value (=Dish's quantity) to create the order
                ))
        );

        return order;
    }

    /**
     * This method is invoked whenever the user has pressed the "Order!" button on the main scene
     * @throws IOException although it can't really be thrown, promised by me
     */
    public void showSwitchToSaveDialog(ActionEvent event) throws IOException {
        // create order
        currentOrder = createOrder();
        if (currentOrder.isEmpty()) {
            IOManager.showAlert("Error", "Please choose some dish(s) and try again.");
            return;
        }

        // create dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.setTitle("Confirmware");
        alert.setHeaderText("Confirm order:");

        // add the order's description to the dialog box
        TextArea area = new TextArea(currentOrder.toString());
        area.setWrapText(true);
        area.setEditable(false);
        alert.getDialogPane().setContent(area);

        // create and add three buttons to the dialog box
        ButtonType acceptButton = new ButtonType("Accept Order");
        ButtonType updateButton = new ButtonType("Update Order", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType cancelButton = new ButtonType("Cancel Order");
        alert.getButtonTypes().setAll(acceptButton, updateButton, cancelButton);

        // add logic to each button
        alert.showAndWait().ifPresent(response -> {
            if (response == acceptButton) {
                try {
                    SceneManager.switchToSave(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (response == updateButton) {
                alert.hide();
            } else {
                clearGUI();
                alert.hide();
            }
        });
    }
}
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

/**
 * A class that holds the IO logic of the entire program.
 */
public class IOManager {
    public static final String menuPath = "src\\demoMenu";

    private static final int LINES_PER_DISH = 3;

    private static Scanner input;
    private static int lineCount = 0; // the number of lines in the scanned file

    /**
     * @return a scanner whose source is the file. Might fail if file is corrupted or does not exist.
     */
    private static boolean getScannerOfMenuFile() {
        try {
            input = new Scanner(new File(menuPath));
            lineCount = (int)Files.lines(Path.of(menuPath)).count();
            if (lineCount % LINES_PER_DISH != 0) {
                showAlert("Error", "Menu file is corrupted!\nEach dish should be represented by 3 and only 3 lines.\nTerminating program.");
                return false;
            }
        } catch (IOException e) {
            showAlert("Error", "Menu file does not exist!\nTerminating program.");
            return false;
        }

        return true;
    }

    /**
     * @param menu a Menu to add a dish to
     * @param input a Scanner to read the Dish's data from
     * @return whether the addition succeeded
     */
    private static boolean parseDish(Menu menu, int dishId, Scanner input) {
        String description = input.nextLine();

        String type = input.nextLine();
        Optional<DishType> dishType = DishType.fromString(type);
        if (dishType.isEmpty()) {
            showAlert("Error", "Menu file is corrupted!\n"+type+" is not a known dish type.\nTerminating program.");
            return false;
        }

        String strPrice = input.nextLine();
        int price;
        try {
            price = Integer.parseInt(strPrice);
        } catch (Exception e) {
            showAlert("Error", "Menu file is corrupted!\n"+strPrice+" is not a number, thus not a valid price.\nTerminating program.");
            return false;
        }

        menu.addDish(dishId, dishType.get(), description, price);

        return true;
    }

    /**
     * Parse a menu file.
     * @return whether the parsing succeeded
     */
    public static boolean parseMenu() {
        if (!getScannerOfMenuFile()) {
            return false;
        }

        MenuController.menu = new Menu(lineCount / LINES_PER_DISH);

        int dishCount = 0;
        while (dishCount < MenuController.menu.getSize() && parseDish(MenuController.menu, dishCount++, input));
        input.close();

        return dishCount == MenuController.menu.getSize();
    }

    /**
     * General alert viewer.
     * @param title the alert's title
     * @param message the alert's message
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}

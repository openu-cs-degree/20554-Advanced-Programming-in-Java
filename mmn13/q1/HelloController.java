import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import static SudokuBoard.*;
import static SudokuCell.*;

/**
 * The controller class that holds the IO methodology of the JavaFX window application.
 */
public class HelloController implements Initializable {
    /**
     * The 9x9 JavaFX GridPane that take cares of sudoku board's IO.
     */
    @FXML
    private GridPane myGrid;

    /**
     * The label that indicates the current game mode to the user
     */
    @FXML
    private Label labelTitle;

    /**
     * Colors to make the board more appealing
     */
    private static final String WHITE_CELL_COLOR = "#ffffff";
    private static final String GREY_CELL_COLOR = "#dddddd";
    private static final String EDITABLE_TEXT_COLOR = "#000000";
    private static final String UNEDITABLE_TEXT_COLOR = "#0000ff";

    /**
     * Two titles for two game modes
     */
    private static final String GAME_MODE = "Current Phase: Game";
    private static final String PREPARATION_MODE = "Current Phase: Preparation";

    /**
     * A method that's called once in the initialization of the controller.
     * It initializes the Sudoku board and creates the text fields inside the GridPane.
     *
     * @param url unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize the sudoku board
        initSudokuBoard();

        // create text-fields
        for(int i = 0; i < BOARD_SIZE; ++i) {
            TextField textField = new TextField();

            textField.setAlignment(Pos.CENTER);
            textField.setMaxWidth(Double.MAX_VALUE);
            textField.setMaxHeight(Double.MAX_VALUE);
            textField.setStyle("-fx-control-inner-background: " + ((getBlock(i)%2==0)?WHITE_CELL_COLOR:GREY_CELL_COLOR) + ";");

            int cellId = i;
            textField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER /* or if cell got out of focus (not asked in instructions */) {
                    checkTextFieldValidity(textField, cellId);
                } // end if
            }); // end setOnKeyPressed
            myGrid.add(textField, cellId % ROW_LENGTH, cellId / ROW_LENGTH);
        }

        // indicate the game mode to the user
        labelTitle.setText(PREPARATION_MODE);
    } // end initialize

    /**
     * A method sets the cell to a specific value, both IO-wise and logic-wise.
     *
     * @param textField the TextField to be changed
     * @param cellId the cellId to be set
     */
    private void setCell(TextField textField, int cellId, int cellValue) {
        textField.setText(cellValue+"");
        setValueOfCell(cellId, cellValue);
    } // end clearCell

    /**
     * A method that erases the cell, both IO-wise and logic-wise.
     * @param textField the TextField to be cleaned
     * @param cellId the cellId to be removed
     */
    private void clearCell(TextField textField, int cellId) {
        textField.setText("");
        removeCell(cellId);
    } // end clearCell

    /**
     * A routine that's called when an invalid value was entered to a cell.
     *
     * @param textField the cell's javafx Node
     * @param cellId the cell's ID (0 - 80)
     */
    private void invalidCellRoutine(TextField textField, int cellId) {
        // show error message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Boring alert title idk");
        alert.setHeaderText(null);
        alert.setContentText("The entered number is not valid.");
        alert.show();

        clearCell(textField, cellId);
    } // end invalidCellRoutine

    /**
     * A method that's called whenever the "enter" key was pressed on a specific TextField node.
     * This method checks if the cell's value is a valid number between 1 - 9,
     * And also that it does not contradict the Sudoku game rules.
     *
     * @param textField the cell's javafx Node
     * @param cellId the cell's ID (0 - 80)
     */
    private void checkTextFieldValidity(TextField textField, int cellId) {
        if (!textField.isEditable()) { // if the textField is uneditable (i.e. is in game mode)
            return;
        }

        String cellText = textField.getText().trim();
        if (cellText.matches("[0\\s]*")) { // user wanted to clear cell
            clearCell(textField, cellId);
            return;
        }

        boolean valid = true; // a boolean to represent validity, because failure can occur on different locations
        try {  // check that the number is a valid number
            int cellValue = Integer.parseInt(cellText);
            if (valid &= isCellValueValid(cellValue)) { // fast C-style checking
                setCell(textField, cellId, cellValue);
            }
        } catch (Exception e) {
            valid = false; // in case Integer.parseInt failed
        } // end catch

        // check that cellValue does not contradict game-rules
        if (!(valid && isCellValid(cellId))) {
            invalidCellRoutine(textField, cellId); // if something failed, show error and clear cell
        } // end if
    }

    /**
     * A method that's changes some properties of the TextFields that meets the predicate's condition.
     * The properties need to be changed when the "set" and "clear" button are pressed.
     *
     * @param color A string representation of the new TextFields' color
     * @param editable Are the TextFields editable
     * @param predicate A predicate that decides to which cells change the properties
     */
    private void setTextFieldsProperties(String color, boolean editable, Predicate<Node> predicate) {
        myGrid.getChildren().stream() // get all 81 cells of the board
                .filter(predicate) // get only the relevant cells to be changed
                .forEach(node -> { // the function that changes each cell
                    TextField textField = ((TextField)node); // convert the Node to a TextField
                    textField.setStyle(textField.getStyle() + "-fx-text-fill: " + color + " ;"); // ugly but fast, I'm afraid to use cleverer methods at this point
                    textField.setEditable(editable); // enable / disable the TextField's ability to be written to
                    //textField.setDisable(!editable); // thought it was cool but unnecessary
                    if (editable) { // in case we clear the board
                        textField.setText(""); // clear the current cell
                    } // end if
                }); // end forEach
    } // end setTextFieldsProperties

    /**
     * A method that's called whenever the "set" button is pressed.
     * It sets the game to "game mode"
     */
    public void pressButtonSet() {
        setTextFieldsProperties(UNEDITABLE_TEXT_COLOR, false, node -> !(((TextField)node).getText().equals("")));
        labelTitle.setText(GAME_MODE); // indicate the new game mode to the user
        // disable buttonSet is not necessary
    } // end pressButtonSet


    /**
     * A method that's called whenever the "clear" button is pressed.
     * It sets the game as if it was just initialized, i.e. prepares the board to a new game.
     */
    public void pressButtonClear() {
        initSudokuBoard();
        setTextFieldsProperties(EDITABLE_TEXT_COLOR, true, node -> true);
        labelTitle.setText(PREPARATION_MODE); // indicate the new game mode to the user
    } // end pressButtonClear

    /**
     * A potentially cool function for the third button of the window.
     * Had no time to implement such function.
     *
     * I have also decided not to remove the button because I have no time to change things.
     */
    public void pressButtonKing() {
    } // end pressButtonKing
} // end HelloController

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Controller class for notebook scene
 */
public class NotebookController implements Initializable {
    private final static int MIN_YEAR = 2013;
    private final static int MAX_YEAR = 2199;
    private final static int MIN_MONTH = 1;
    private final static int MAX_MONTH = 12;
    private final static int MIN_DAY = 1;

    private final static int DEFAULT_YEAR = MIN_YEAR;
    private final static int DEFAULT_MONTH = MIN_MONTH;
    private final static int DEFAULT_DAY = MIN_DAY;

    @FXML private ComboBox<Integer> comboBoxDay;
    @FXML private ComboBox<Integer> comboBoxMonth;
    @FXML private ComboBox<Integer> comboBoxYear;
    @FXML private TextArea textArea;

    private Date currentDate;

    /**
     * Update the comboBoxDay choice-range, based on current year and month.
     * Set the current day value to the default value (day = 1).
     */
    private void updateComboBoxDay() {
        final int MAX_DAY = Date.daysInMonth(currentDate.getYear(), currentDate.getMonth());
        comboBoxDay.setItems(FXCollections.observableList(IntStream.rangeClosed(MIN_DAY, MAX_DAY).boxed().toList()));
        comboBoxDay.setValue(DEFAULT_DAY);
        currentDate.setDay(DEFAULT_DAY);
    }

    /**
     * Initialize the ComboBoxes with possible choice-ranges.
     * Update each comboBox value to its default value.
     */
    private void initializeComboBoxes() {
        // initialize year
        IntStream.rangeClosed(MIN_YEAR, MAX_YEAR).forEach(comboBoxYear.getItems()::add);
        comboBoxYear.setValue(DEFAULT_YEAR);
        comboBoxYear.setOnAction(e -> {
            currentDate.setYear(comboBoxYear.getValue());
            updateComboBoxDay();
        });

        // initialize month
        IntStream.rangeClosed(MIN_MONTH, MAX_MONTH).forEach(comboBoxMonth.getItems()::add);
        comboBoxMonth.setValue(DEFAULT_MONTH);
        comboBoxMonth.setOnAction(e -> {
            currentDate.setMonth(comboBoxMonth.getValue());
            updateComboBoxDay();
        });

        // initialize day
        updateComboBoxDay();
        comboBoxDay.setOnAction(e ->
            currentDate.setDay(Objects.requireNonNullElse(comboBoxDay.getValue(), DEFAULT_DAY))
        );
    }

    /**
     * C'mon...
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentDate = new Date(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
        initializeComboBoxes();
        loadNote();
    }

    /**
     * ... do I really need to...
     */
    public void backToChoiceScene() {
        SceneManager.switchToChoice();
    }

    /**
     * ...document these? After all...
     */
    public void loadNote() {
        textArea.setText(NotebookManager.loadNote(currentDate));
    }

    /**
     * ...a good code is a code that documents itself ;)
     */
    public void saveNote() {
        NotebookManager.saveNote(currentDate, textArea.getText());
    }
}
import javafx.scene.control.Alert;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * Manager class for notebook logic, i.e. loading, saving, writing and reading.
 */
public class NotebookManager {
    private static HashMap<Date, String> currentNotebook;
    private static File currentFile;

    /**
     * Create a new notebook
     * @param file the file to save the notebook to
     */
    public static void createNotebook(File file) {
        currentFile = file;
        currentNotebook = new HashMap<>();
    }

    /**
     * @param obj obj
     * @return obj cast to HashMap<Date, String>
     */
    @SuppressWarnings("unchecked")
    private static HashMap<Date, String> safelyCastObjectToHashMap(Object obj) {
        return (HashMap<Date, String>) obj;
    }

    /**
     * Load a new notebook from file.
     * If file does not contain notebook, alert to the user.
     *
     * @param file the file to load the notebook from.
     */
    public static void loadNotebook(File file) {
        currentFile = file;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object deserializedObject = ois.readObject();
            if (deserializedObject instanceof HashMap) {
                currentNotebook = safelyCastObjectToHashMap(deserializedObject);
                SceneManager.switchToNotebook(file.getName());
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Alert(Alert.AlertType.ERROR, "The provided file is not a notebook!").show();
    }

    /**
     * Save the notebook to the provided file when notebook was created/loaded.
     * @return whether the saving was successful or not
     */
    public static boolean saveNotebook() {
        try (FileOutputStream fos = new FileOutputStream(currentFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(currentNotebook);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load the note at a certain date.
     * If no note exists in that date, return an empty note.
     *
     * @param key date to load note from
     * @return the note on that date
     */
    public static String loadNote(Date key) {
        assert currentNotebook != null;
        return Objects.requireNonNullElse(currentNotebook.get(key), "");
    }

    /**
     * Save a note at a certain date.
     * Alert to the user whether the saving was successful or not.
     *
     * @param key date of the note
     * @param value the note itself
     */
    public static void saveNote(Date key, String value) {
        assert currentNotebook != null;

        if (value.isEmpty()) {
            currentNotebook.remove(key);
        } else {
            currentNotebook.put(new Date(key), value);
            // NOTE: HashMap takes by reference by I need by value in order to not override keys
        }

        if (saveNotebook()) { // should I save only at exit? It sounds... unsafe.
            new Alert(Alert.AlertType.CONFIRMATION, "Note saved successfully on date " + key + "!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "IOException occurred, note was not saved!").show();
        }
    }
}

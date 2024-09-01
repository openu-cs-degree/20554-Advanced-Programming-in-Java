import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static SudokuCell.*;

/**
 * The logic class that holds the logic methodology of the Sudoku board.
 * It is designed to be independent of any graphic library, e.g. JavaFX.
 */
public class SudokuBoard {
    /**
     * The sudoku board, implemented as an array of cellValue (the index is cellId)
     */
    private static int[] board;

    /**
     * Initializes the sudoku board, a.k.a. clear it.
     */
    public static void initSudokuBoard() {
        board = new int[BOARD_SIZE]; // Clear the board
    } // end InitSudokuBoard

    /**
     * sets the cell with a specific ID to a specific value.
     * If the ID is invalid, do nothing.
     *
     * @param cellId the cell's ID
     * @param cellValue the cell's new value
     */
    public static void setValueOfCell(int cellId, int cellValue) {
        if (isCellIdValid(cellId)) {
            board[cellId] = cellValue; // sets the cell's new value
        } // end if
    } // end setValueOfCell

    /**
     * removes a cell with a specific ID from the board.
     * If the ID is invalid, do nothing.
     *
     * @param cellId the cell's ID
     */
    public static void removeCell(int cellId) {
        if (isCellIdValid(cellId)) {
            board[cellId] = 0; // remove the cell from the board
        } // end if
    } // end removeCell

    /**
     * This method checks if the cells on the board, that match the predicate, are valid,
     * i.e. they match the game's rules (no number occurs twice within specific ranges).
     *
     * @param predicate the predicate of the cells. Should predicate same row / column / block of a certain cell.
     * @return whether the cells match the game's rules
     */
    private static boolean areCellsValid(Predicate<Integer> predicate) {
        HashSet<Integer> seen = new HashSet<>(); // create an empty set

        return IntStream.range(0, board.length)         // get all possible cellIDs
                .filter(predicate::test)                // skip those who do not match the predicate
                .map(cellId -> board[cellId])           // convert them to cellValues
                .filter(SudokuCell::isCellValueValid)   // skip those who are empty (i.e. 0)
                .allMatch(seen::add);                   // return false if one of them appears twice
    } // end areCellsValid

    /**
     * check if a certain row meets the game's rules.
     * plays a part inside isCellValid.
     *
     * @param row the row to be checked
     * @return if it meets the game's rules
     */
    private static boolean isRowValid(int row) {
        return areCellsValid(cellId -> getRow(cellId) == row);
    } // end isRowValid

    /**
     * check if a certain column meets the game's rules.
     * plays a part inside isCellValid.
     *
     * @param col the column to be checked
     * @return if it meets the game's rules
     */
    private static boolean isColValid(int col) {
        return areCellsValid(cellId -> getCol(cellId) == col);
    } // end isColValid

    /**
     * check if a certain block meets the game's rules.
     * plays a part inside isCellValid.
     *
     * @param block the row to be checked
     * @return if it meets the game's rules
     */
    private static boolean isBlockValid(int block) {
        return areCellsValid(cellId -> getBlock(cellId) == block);
    } // end isBlockValid

    /**
     * A public method that checks if a (new inserted) cell is valid, i.e. meets the game's rules.
     * The game's rules say no number appears twice in each row, column, and block,
     * therefore we check the cell's row, column, and block to see if they have recurring numbers.
     *
     * NOTE: if the cell's ID is not valid, we return false as for it does not match game rules
     *
     * @param cellId the cellId to be checked
     * @return whether it meets the game's rules
     */
    public static boolean isCellValid(int cellId) {
        if (!isCellIdValid(cellId)) return false;
        if (!isRowValid(getRow(cellId))) return false;
        if (!isColValid(getCol(cellId))) return false;
        return isBlockValid(getBlock(cellId));
    } // end isCellValid
}

/**
 * A utility class that wraps around cell methodology and constants
 */
public class SudokuCell {
    /**
     * Constants of cell values
     */
    public final static int MIN_CELL_VALUE = 1;
    public final static int MAX_CELL_VALUE = 9;

    /**
     * Length of a row in the sudoku board.
     * This size is the range of values a cell can hold.
     */
    public static final int ROW_LENGTH = MAX_CELL_VALUE - MIN_CELL_VALUE + 1;

    /**
     * Length of a block in the sudoku board.
     * This size is the square root of the row's length.
     */
    public static final int BLOCK_LENGTH = (int)Math.sqrt(ROW_LENGTH);

    /**
     * Size of the sudoku board.
     * The size is the square of its row's length.
     */
    public static final int BOARD_SIZE = (int)Math.pow(ROW_LENGTH, 2);

    /**
     * Constants of cell IDs
     */
    public final static int MIN_CELL_ID = 0;
    public final static int MAX_CELL_ID = BOARD_SIZE - 1;

    /**
     * returns whether a cellId has a valid value
     *
     * @param cellId the value to check
     * @return whether the value is valid
     */
    public static boolean isCellIdValid(int cellId) {
        return (cellId >= MIN_CELL_ID && cellId <= MAX_CELL_ID);
    } // end isCellIdValid

    /**
     * returns whether a cellValue has a valid value
     *
     * @param cellValue the value to check
     * @return whether the value is valid
     */
    public static boolean isCellValueValid(int cellValue) {
        return (cellValue >= MIN_CELL_VALUE && cellValue <= MAX_CELL_VALUE);
    } // end isCellValueValid

    /**
     * returns the row of a specific cell
     * @param cellId the cell's ID
     * @return the cell's row
     */
    public static int getRow(int cellId) {
        return cellId / ROW_LENGTH;
    } // end getRow

    /**
     * returns the column of a specific cell
     * @param cellId the cell's ID
     * @return the cell's column
     */
    public static int getCol(int cellId) {
        return cellId % ROW_LENGTH;
    } // end getCol

    /**
     * returns the block number of a specific cell.
     * a block is usually the 3x3 (sometimes 4x4) colored area in which the cell's in.
     *
     * @param cellId the cell's ID
     * @return the cell's block number
     */
    public static int getBlock(int cellId) {
        return BLOCK_LENGTH * (getRow(cellId) / BLOCK_LENGTH) + getCol(cellId) / BLOCK_LENGTH;
    } // end getBlock

}

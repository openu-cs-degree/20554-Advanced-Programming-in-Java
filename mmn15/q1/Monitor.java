import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Monitor {
    int[][] resultMatrix;
    boolean[][] printed;
    int nextRow;
    int nextCol;
    Semaphore semaphore;
    CountDownLatch latch;

    Monitor(int rows, int cols, CountDownLatch latch) {
        resultMatrix = new int[rows][cols];
        printed = new boolean[rows][cols];
        nextRow = 0;
        nextCol = 0;
        semaphore = new Semaphore(1);
        this.latch = latch;
    }

    private static String spacesOf(int value) {
        final int MAX_NUM_LENGTH = 4;
        StringBuilder s = new StringBuilder();
        IntStream.range((int) Math.log10(value), MAX_NUM_LENGTH).forEach(i -> s.append(' '));
        return s.toString();
    }

    void printCell(int row, int col, int value) {
        try {
            semaphore.acquire();
            resultMatrix[row][col] = value;
            printed[row][col] = true;
            if (row == nextRow && col == nextCol) {
                while (true) {
                    if (nextCol == 0) {
                        System.out.print("| ");
                    }

                    int valueToPrint = resultMatrix[nextRow][nextCol];
                    System.out.print(valueToPrint + spacesOf(valueToPrint));
                    nextCol++;
                    if (nextCol == resultMatrix[0].length) {
                        System.out.println('|');
                        nextCol = 0;
                        nextRow++;
                    }
                    if (nextRow >= resultMatrix.length || !printed[nextRow][nextCol]) {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            semaphore.release();
        }
    }
}
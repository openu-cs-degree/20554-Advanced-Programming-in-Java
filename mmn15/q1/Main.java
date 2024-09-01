import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int p = Integer.parseInt(args[2]);

        int[][] A = generateMatrix(m, n);
        int[][] B = generateMatrix(p, m);

        System.out.println("Matrix A:");
        printMatrix(A);

        System.out.println("Matrix B:");
        printMatrix(B);

        System.out.println("Matrix A * B:");
        multiplyMatricesAndPrint(A, B, n, p);
    }

    private static int[][] generateMatrix(int rows, int cols) {
        return IntStream.range(0, rows)
                .mapToObj(r -> IntStream.range(0, cols).map(c -> random.nextInt(11)).toArray())
                .toArray(int[][]::new);
    }

    private static void printMatrix(int[][] matrix) {
        Arrays.stream(matrix)
                .map(Arrays::toString)
                .forEach(System.out::println);
    }

    private static void multiplyMatricesAndPrint(int[][] A, int[][] B, int n, int p) {
        Object monitor = new Object();
        int[][] outputOrder = new int[n][p];

        ExecutorService executorService = Executors.newFixedThreadPool(n * p);
        IntStream.range(0, n).forEach(i -> IntStream.range(0, p).forEach(j -> executorService.submit(() -> {
            int value = IntStream.range(0, A[i].length).map(k -> A[i][k] * B[k][j]).sum();

            synchronized (monitor) {
                while (outputOrder[i][j] != 1) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                printValue(value, j == 0, j == p - 1);

                if (j + 1 < p) {
                    outputOrder[i][j + 1] = 1;
                } else if (i + 1 < n) {
                    outputOrder[i + 1][0] = 1;
                }
                monitor.notifyAll();
            }
        })));

        synchronized (monitor) {
            outputOrder[0][0] = 1;
            monitor.notifyAll();
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("An error occurred! Terminating program.");
        }
    }

    private static void printValue(int value, boolean firstInRow, boolean lastInRow) {
        final int MAX_NUM_LENGTH = 2;
        StringBuilder s = new StringBuilder();
        if (firstInRow) {
            s.append('[');
        }
        s.append(value);
        if (!lastInRow) {
            s.append(", ");
            IntStream.range((int) Math.log10(value), MAX_NUM_LENGTH).forEach(i -> s.append(' '));
        } else {
            s.append("]\n");
        }
        System.out.print(s);
    }
}

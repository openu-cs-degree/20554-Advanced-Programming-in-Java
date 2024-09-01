public class MatrixMultiplicationThread implements Runnable {
    int[][] A;
    int[][] B;
    int[][] C;
    int row;
    int col;
    Monitor monitor;

    public MatrixMultiplicationThread(int[][] a, int[][] b, int[][] c, int row, int col, Monitor monitor) {
        A = a;
        B = b;
        C = c;
        this.row = row;
        this.col = col;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < A[0].length; ++i) {
            sum += A[row][i] * B[i][col];
        }
        C[row][col] = sum;
        monitor.printCell(row, col, sum);
    }
}
package matrix;

import java.time.Duration;
import java.time.Instant;

public class Main {

    private final static int FIRST_MATRIX_ROWS = 1000;
    private final static int FIRST_MATRIX_COLS = 2000;
    private final static int SECOND_MATRIX_ROWS = FIRST_MATRIX_COLS;
    private final static int SECOND_MATRIX_COLS = 1000;

    public static void main(String[] args){

        Matrix firstMatrix = new Matrix(FIRST_MATRIX_ROWS, FIRST_MATRIX_COLS);
        Matrix secondMatrix = new Matrix(SECOND_MATRIX_ROWS, SECOND_MATRIX_COLS);
        firstMatrix.fill();
        secondMatrix.fill();
        Instant startTime = Instant.now();

        Matrix resultMatrixMT = Matrix.multiplyMatrixMT(firstMatrix, secondMatrix, Runtime.getRuntime().availableProcessors());

        Instant stopTime = Instant.now();
        System.out.println("multiThread: " + Duration.between(startTime, stopTime));

        startTime = Instant.now();
        Matrix resultMatrix = Matrix.multiply(firstMatrix, secondMatrix);
        stopTime = Instant.now();
        System.out.println("one thread: " + Duration.between(startTime, stopTime));
        if (!resultMatrixMT.equals(resultMatrix)) {
            System.out.println("Error in multithreaded calculation!");
        }
    }
}

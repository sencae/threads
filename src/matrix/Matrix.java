package matrix;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Matrix {
    public final int rows;
    public final int columns;
    public int[][] matrix;

    public Matrix() {
        this.rows    = 1;
        this.columns = 1;
        this.matrix  = new int[rows][columns];
    }

    public Matrix(int rows, int columns) {

        this.rows    = rows;
        this.columns = columns;
        this.matrix  = new int[rows][columns];
    }

    public Matrix(int[][] matrix) {
        this.rows    = matrix.length;
        this.columns = matrix[0].length;
        this.matrix  = matrix;
    }


    public static  Matrix multiply(Matrix matrix1, Matrix matrix2){

            final Matrix result = new Matrix(matrix1.rows,matrix2.columns);

            for (int row = 0; row < matrix1.rows; ++row) {
                for (int col = 0; col < matrix2.columns; ++col) {
                    int sum = 0;
                    for (int i = 0; i < matrix2.rows; ++i)
                        sum += matrix1.matrix[row][i] * matrix2.matrix[i][col];
                    result.matrix[row][col] = sum;
                }
            }

            return result;

    }

    public static Matrix multiplyMatrixMT(Matrix matrix1, Matrix matrix2, int threadCount)  {
        assert threadCount > 0;

        final int rowCount = matrix1.rows;
        final int colCount = matrix2.columns;
        final Matrix result = new Matrix(matrix1.rows,matrix2.columns);

        if (threadCount > rowCount) {
            threadCount = rowCount;
        }
        final int cellsForThread = (rowCount * colCount) / threadCount;
        int firstIndex = 0;
        final MultiplierThread[] multiplierThreads = new MultiplierThread[threadCount];

        for (int threadIndex = threadCount - 1; threadIndex >= 0; threadIndex--) {
            int lastIndex = firstIndex + cellsForThread;
            if (threadIndex == 0) {
                lastIndex = rowCount * colCount;
            }
            multiplierThreads[threadIndex] = new MultiplierThread(matrix1, matrix2, result, firstIndex, lastIndex);
            multiplierThreads[threadIndex].start();
            firstIndex = lastIndex;
        }

        try {
            for (final MultiplierThread multiplierThread : multiplierThreads)
                multiplierThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void fill() {

        final Random random = new Random();

        for (int row = 0; row < matrix.length; ++row)
            for (int col = 0; col < matrix[row].length; ++col)
                matrix[row][col] = random.nextInt(100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        if(!(rows == matrix1.rows &&
                columns == matrix1.columns))
            return false;

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < matrix1.columns; ++col) {
                if (matrix[row][col] != matrix1.matrix[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }
}


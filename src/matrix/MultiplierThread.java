package matrix;


class MultiplierThread extends Thread {

    private final Matrix firstMatrix;
    private final Matrix secondMatrix;
    private final Matrix resultMatrix;
    private final int firstIndex;
    private final int lastIndex;
    private final int sumLength;


    public MultiplierThread(final Matrix matrix1, final Matrix matrix2, final Matrix resultMatrix,
                            final int firstIndex, final int lastIndex) {
        this.firstMatrix = matrix1;
        this.secondMatrix = matrix2;
        this.resultMatrix = resultMatrix;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;

        sumLength = secondMatrix.rows;
    }


    private void calcValue(final int row, final int col) {
        int sum = 0;
        for (int i = 0; i < sumLength; ++i)
            sum += firstMatrix.matrix[row][i] * secondMatrix.matrix[i][col];
        resultMatrix.matrix[row][col] = sum;
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " started. Calculating cells from " + firstIndex + " to " + lastIndex + "...");

        final int colCount = secondMatrix.columns;
        for (int index = firstIndex; index < lastIndex; ++index)
            calcValue(index / colCount, index % colCount);

        System.out.println("Thread " + getName() + " finished.");
    }
}
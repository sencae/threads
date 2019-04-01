package shell;


import java.util.Arrays;

import static shell.ParallelShellSort.swap;

public class SortThread extends Thread {
    private final int[] mas;
    private final int endInd;
    private final int startInd;

    public SortThread(int[] mas, int startIndex, int endIndex) {
        this.mas = mas;
        this.endInd = endIndex;
        this.startInd = startIndex;
    }

    public void sort(int[] array, int startIndex, int endInd) {
        int h = 1;
        while (h * 3 < endInd - startIndex)
            h = h * 3 + 1;

        while (h >= 1) {
            hSort(array, h, startIndex, endInd);
            h = h / 3;
        }
    }

    private void hSort(int[] array, int h, int startInd, int endInd) {
        int length = endInd - startInd;
        for (int i = h; i < length; i++) {
            for (int j = i; j >= h; j = j - h) {
                if (array[j + startInd] < array[j + startInd - h])
                    swap(array, j + startInd, j + startInd - h);
                else
                    break;
            }
        }
    }

    @Override
    public void run() {
        sort(mas, startInd, endInd);
    }
}

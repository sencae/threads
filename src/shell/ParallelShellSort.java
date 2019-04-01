package shell;

public class ParallelShellSort {

    public static int[] simpleShellSort(int[] array) {
        int[] result = array.clone();
        int n = result.length;
        int i, j;
        int gap = n / 2;
        while (gap > 0) {
            for (i = 0, j = i + gap; j < n; i++, ++j) {
                if (result[i] > result[j]) {
                    int temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
            gap = gap / 2;
        }
        return result;

    }

    public void sort(int[] array) {

        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;

        while (h >= 1) {
            hSort(array, h);
            h = h / 3;
        }
    }

    private void hSort(int[] array, int h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            for (int j = i; j >= h; j = j - h) {
                if (array[j] < array[j - h])
                    swap(array, j, j - h);
                else
                    break;
            }
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public boolean parallelSort(int[] array, int threats) {
        assert threats > 0;
        int endIndex = 0;
        int startIndex;
        boolean die;
        while (threats >= 1) {
            SortThread[] sortThreads = new SortThread[threats];
            for (int i = 0; i < threats; i++) {
                startIndex = endIndex;
                endIndex += (int) Math.ceil((double) array.length / threats);
                if (endIndex >= array.length)
                    endIndex = array.length;
                sortThreads[i] = new SortThread(array, startIndex, endIndex);
                sortThreads[i].start();
            }
            try {
                for (final SortThread sortThread : sortThreads)
                    sortThread.join();
                threats = threats / 2;
                die = parallelSort(array, threats);
                if (die) {
                    return die;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        die = true;
        return die;
    }

}


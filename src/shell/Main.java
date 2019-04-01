package shell;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        ParallelShellSort parallelShellSort = new ParallelShellSort();
        int arraySize = 1677777;
        int[] array = new int[arraySize];
        final Random random = new Random();
        for (int i = 0; i < arraySize; i++)
            array[i] = random.nextInt(100);
        int[] result = array.clone();
        Instant startTime = Instant.now();
        parallelShellSort.sort(result);
        Instant stopTime = Instant.now();
        System.out.println("simpleShell: " + Duration.between(startTime, stopTime));

        startTime = Instant.now();

        parallelShellSort.parallelSort(array, Runtime.getRuntime().availableProcessors());

        stopTime = Instant.now();

        System.out.println("parallelShell: " + Duration.between(startTime, stopTime));

    }
}

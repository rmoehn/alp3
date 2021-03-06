package alp3.ueb;

import java.util.concurrent.Callable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import bb.util.Benchmark;

public class SlowsortBenchmark {
    private static final int EXEC_CNT = 10;

    public static void main(String[] args) throws Exception {
        int[] testElemCnts = { 9, 10, 11, 12, 13 };

        // Time the slowsorts for random input lists of different lengths
        for (final int testElemCnt : testElemCnts) {
            System.out.println(
                "Timing normal slowsort with " + testElemCnt + " items.");
            timeLongRunning(
                new Callable<List<Integer>>() {
                    public List<Integer> call() {
                        return Slowsort.sort( randomIntList(testElemCnt) );
                    }
                },
                EXEC_CNT
            );

            System.out.println(
                "Timing randomised slowsort with " + testElemCnt + " items.");
            timeLongRunning(
                new Callable<List<Integer>>() {
                    public List<Integer> call() {
                        return RandomisedSlowsort.sort(
                            randomIntList(testElemCnt)
                        );
                    }
                },
                EXEC_CNT
            );
        }


        // Benchmark the slowsorts with a constant short input list
        final List<Integer> testlist = randomIntList(6);

        System.out.println("Benchmarking normal slowsort.");
        System.out.println(
            new Benchmark(
                new Callable<List<Integer>>() {
                    public List<Integer> call() {
                        return Slowsort.sort(testlist);
                    }
                }
            ).toString()
        );

        System.out.println("Benchmarking randomised slowsort.");
        System.out.println(
            new Benchmark(
                new Callable<List<Integer>>() {
                    public List<Integer> call() {
                        return RandomisedSlowsort.sort(testlist);
                    }
                }
            ).toString()
        );
    }

    /**
     * Times a longrunning callable execCnt times and prints some statistics.
     * (Of course, one can also time shortrunning callables, but the simple
     * method for timing used here would yield very bad results.)
     */
    private static <V> void timeLongRunning(Callable<V> task, int execCnt)
            throws Exception {
        ArrayList<Long> times      = new ArrayList<Long>(execCnt);
        DescriptiveStatistics stat = new DescriptiveStatistics(execCnt);

        // Run the Callable the specified amount of times
        for (int execNr = 1; execNr <= execCnt; ++execNr) {
            // Time it
            long startTime = System.currentTimeMillis();
            V result       = task.call();
            long endTime   = System.currentTimeMillis();

            // Add the time to the list of times
            Long duration = endTime - startTime;
            times.add(duration);
            stat.addValue(duration.doubleValue());

            // Do something with the result to prevent dead code elimination
            System.err.println(result.toString());
        }

        // Print the results
        System.out.println("Single times: " + times.toString());
        System.out.println("Statistics: "   + stat.toString());

        return;
    }

    /**
     * Creates a {@code List} of random integers of the specified length.
     */
    private static List<Integer> randomIntList(int length) {
        Random random     = new Random();
        List<Integer> res = new ArrayList<Integer>(length);
        for (int i = 0; i < length; ++i) {
            res.add(random.nextInt());
        }

        return res;
    }
}

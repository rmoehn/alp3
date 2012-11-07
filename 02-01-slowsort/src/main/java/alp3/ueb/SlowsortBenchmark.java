package alp3.ueb;

import java.util.concurrent.Callable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import bb.util.Benchmark;

public class SlowsortBenchmark {
    public static void main(String[] args) throws Exception {
        List<List<Integer>> testlists = new ArrayList<List<Integer>>();
        testlists.add(randomIntList(10));
        testlists.add(randomIntList(11));

        for (final List<Integer> testlist : testlists) {
            long time = timeLongRunning(new Callable<List<Integer>>() {
                            public List<Integer> call() {
                                return Slowsort.sort(testlist);
                            }
                        });

            System.out.println("Non-random: " + time);
        }

        for (final List<Integer> testlist : testlists) {
            long time = timeLongRunning(new Callable<List<Integer>>() {
                            public List<Integer> call() {
                                return RandomisedSlowsort.sort(testlist);
                            }
                        });

            System.out.println("Random: " + time);
        }

        System.out.println(
            new Benchmark(
                new Callable<List<Integer>>() {
                    public List<Integer> call() {
                        return RandomisedSlowsort.sort(randomIntList(6));
                    }
                }
            ).toString()
        );
    }

    /**
     * Times a longrunning callable and returns the running time in
     * milliseconds. (Of course, one can also time shortrunning callables, but
     * the simple method for timing used here would yield very bad results.)
     */
    private static <V> long timeLongRunning(Callable<V> task, int execCnt)
            throws Exception {
        long startTime = System.currentTimeMillis();
        V result       = task.call();
        long endTime   = System.currentTimeMillis();

        // Do something with the result to prevent dead code elimination
        System.out.println(result.toString());

        return endTime - startTime;
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

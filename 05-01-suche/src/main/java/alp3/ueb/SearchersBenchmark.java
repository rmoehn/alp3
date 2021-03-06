package alp3.ueb;

import java.util.concurrent.Callable;
import java.util.Random;
import java.util.Arrays;
import richard.util.RandomUtils;
import bb.util.Benchmark;
import alp3.ueb.Searchers;

/**
 * Simple comparison of binary and interpolation search.
 */
public class SearchersBenchmark {
    /**
     * Compares binary and interpolation search on arrays of the given length.
     */
    public static void main (String[] argv) {
        final RandomUtils randomList = new RandomUtils();
        final Random randomScal      = new Random();
        final int testarrayLength    = Integer.parseInt(argv[0]);

        try {
            // Benchmark the binary search
            System.out.println(new Benchmark(
                new Callable<Integer>() {
                    public Integer call() throws Exception {
                        int[] testarray
                            = randomList.randomIntArray(testarrayLength);
                        Arrays.sort(testarray);

                        // Sometimes with elements that are there...
                        if (randomScal.nextBoolean()) {
                            return Searchers.binarySearch(
                                       testarray,
                                       randomScal.nextInt()
                                   );
                        }
                        // ...sometimes with elements that are not
                        else {
                            return Searchers.binarySearch(
                                       testarray,
                                       testarray[
                                           randomScal.nextInt(testarrayLength)
                                       ]
                                   );
                        }
                    }
                }
            ).toString());

            // Benchmark the interpolation search
            System.out.println(new Benchmark(
                new Callable<Integer>() {
                    public Integer call() throws Exception {
                        int[] testarray
                            = randomList.randomIntArray(testarrayLength);
                        Arrays.sort(testarray);

                        if (randomScal.nextBoolean()) {
                            return Searchers.interpolationSearch(
                                       testarray,
                                       randomScal.nextInt()
                                   );
                        }
                        else {
                            return Searchers.interpolationSearch(
                                       testarray,
                                       testarray[
                                           randomScal.nextInt(testarrayLength)
                                       ]
                                   );
                        }
                    }
                }
            ).toString());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

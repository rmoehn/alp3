package alp3.ueb;

import java.lang.Runnable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import bb.util.Benchmark;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import static alp3.ueb.QuickselectUtils.uniqueRandomList;


/**
 * Depending on the command line arguments, runs various benchmarks on the
 * quickselect implementation.
 */
class QuickselectBenchmark {
    /*
     * Also a little study of nesting. Wouldn't do this in production code,
     * though...
     */

    private static final int EXEC_CNT = 100;
    private static Random random = new Random();

    private static final String COUNT_COMPARISONS = "count";
    private static final String MEASURE_TIME      = "time";
    private static Map<String, Runnable> actionForArg
        = new HashMap<String, Runnable>();

    public static void main (String[] args) {
        // Determine the length of the lists to generate
        final int listLength = Integer.parseInt(args[1]);

        // Set the actions possible with this program
        actionForArg.put(
            COUNT_COMPARISONS,
            new Runnable() {
                public void run() {
                    // Set up statistics
                    SummaryStatistics stat = new SummaryStatistics();

                    // Repeatedly perform the following
                    for (int execNr = 0; execNr < EXEC_CNT; ++execNr) {
                        // Create a random list
                        List<Integer> testlist = uniqueRandomList(listLength);

                        // Select the random-th element from the list
                        Integer element = Quickselect.select(
                                              random.nextInt(testlist.size()),
                                              testlist
                                          );

                        // Print it to avoid dead code elimination
                        System.err.println(element);

                        // Add the number of comparisons to our statistics
                        Integer compNr
                            = new Integer(
                                  Quickselect.getAndResetComparisonNr()
                              );
                        stat.addValue(compNr.doubleValue());
                    }

                    // Print the performance values
                    System.out.println(
                        listLength                    + "\t"
                        + stat.getMean()              + "\t"
                        + stat.getStandardDeviation()
                    );
                }
            }
        );

        actionForArg.put(
            MEASURE_TIME,
            new Runnable() {
                public void run() {
                    Benchmark benchmark;
                    try {
                        // Do the benchmark (behold the Callable-in-Runnable!)
                        benchmark = new Benchmark(
                            new Callable<Integer>() {
                                public Integer call() throws Exception {
                                    // Generate a random list
                                    List<Integer> testlist
                                        = uniqueRandomList(listLength);

                                    // Select the random-th element from it
                                    return Quickselect.select(
                                               random.nextInt(
                                                   testlist.size()
                                               ),
                                               testlist
                                           );
                                }
                            }
                        );
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Error in benchmark.");
                    }
                    System.err.println(benchmark.toString());

                    // Print the performance values
                    System.out.println(
                        listLength            + "\t"
                        + benchmark.getMean() + "\t"
                        + benchmark.getSd()
                    );
                }
            }
        );

        actionForArg.get(args[0]).run();
    }
}

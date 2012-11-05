package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.Math;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.lang.ArrayUtils;

import static alp3.ueb.Permutations.*;

public class RandomPermutationsTest {
    private static final int TEST_CNT = 100000;

    List<Integer> testlist;
    int testlistSize;
    RandomPermutations<Integer> testperms;


    public RandomPermutationsTest() {
        super();
    }

    @Before public void setUp() {
        testlist     = Arrays.asList(1, 2, 3, 4, 5);
        testlistSize = testlist.size();
        testperms    = new RandomPermutations<Integer>(testlist);
    }

    @Test public void testPermutations0() {
        for (int i = 0; i <= TEST_CNT; ++i) {
            List<Integer> perm = testperms.nextElement();
            assertEquals(testlistSize, perm.size());
            assertTrue(perm.containsAll(testlist));
        }
    }

    @Test public void testPermutations1() {
        Map<List<Integer>, Integer> occurenceCntFor
            = new HashMap<List<Integer>, Integer>(TEST_CNT, 1.0f);

        // Create a large number of permutations
        for (int i = 0; i <= TEST_CNT; ++i) {
            List<Integer> perm = testperms.nextElement();

            // If a permutation hadn't occured before, set its count to 0
            if (! occurenceCntFor.containsKey(perm)) {
                occurenceCntFor.put(perm, 0);
            }

            // Increase the number of times the current permutation occured
            int occCnt = occurenceCntFor.get(perm);
            occurenceCntFor.put(perm, ++occCnt);
        }

        // Create a new statistics object from the occurence numbers
        DescriptiveStatistics stat = new DescriptiveStatistics(TEST_CNT);
        for (Integer cnt : occurenceCntFor.values()) {
            stat.add( cnt.doubleValue() );
        }

        // Make sure that values lie within 3 standard deviations from mean
        double mean         = stat.getMean();
        double threeStdDevs = stat.getStandardDeviation();
        for (Integer cnt : occurenceCntFor.values()) {
            assertTrue( Math.abs(cnt.doubleValue() - mean) <= threeStdDevs );
        }
    }
}

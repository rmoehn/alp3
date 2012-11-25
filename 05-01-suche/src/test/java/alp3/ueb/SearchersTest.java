package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import richard.util.RandomUtils;

import alp3.ueb.Searchers;

public class SearchersTest {
    private static final int TESTARRAY_LEN = 523;
    private static final int MAX_TESTVAL   = 13097;
    int[] testarray0;
    int[] presentVals0    = { 24, 9837, -1345, -1344, 14039, 14040 };
    int[] notPresentVals0 = { -89, -2004, 15909 };
        // Should lie outside 0..MAX_TESTVAL-1

    @Before public void setUp() {
        // Create an array of random numbers
        int[] randomArray = (new RandomUtils()).randomIntArray(
                               TESTARRAY_LEN - presentVals0.length,
                               MAX_TESTVAL
                            );

        // Copy them into a larger array
        testarray0 = Arrays.copyOf(randomArray, TESTARRAY_LEN);

        // Append test values that should be present in the array
        for (int i = 0; i < presentVals0.length; ++i) {
            testarray0[testarray0.length - i - 1] = presentVals0[i];
        }

        // Sort it
        Arrays.sort(testarray0);
    }

    @Test public void testBinsearchPresent0() {
        for (int presentVal : presentVals0) {
            int index = Searchers.binarySearch(testarray0, presentVal);
            assertEquals(presentVal, testarray0[index]);
        }
    }

    @Test public void testBinsearchNotPresent0() {
        for (int notPresentVal : notPresentVals0) {
            int index = Searchers.binarySearch(testarray0, notPresentVal);
            assertEquals(Searchers.NOT_FOUND, index);
        }
    }

    @Test public void testInterpsearchPresent0() {
        for (int presentVal : presentVals0) {
            int index = Searchers.interpolationSearch(testarray0, presentVal);
            assertEquals(presentVal, testarray0[index]);
        }
    }

    @Test public void testInterpsearchNotPresent0() {
        for (int notPresentVal : notPresentVals0) {
            int index
                = Searchers.interpolationSearch(testarray0, notPresentVal);
            assertEquals(Searchers.NOT_FOUND, index);
        }
    }
}

package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class QuickselectTest {
    public int[] sizes = { 1, 2, 3, 5, 8, 13, 21, 34 };
    public List<List<Integer>> testlists;
    public List<List<Integer>> sortedTestlists;

    @Before public void setUp() {
        Random random   = new Random();
        testlists       = new ArrayList(sizes.length);
        sortedTestlists = new ArrayList(sizes.length);

        // Create sorted and an unsorted versions of random lists
        for (int size : sizes) {
            List<Integer> testlist = randomList(size);
                // Gibt's da was von Commons?
            testlists.add(testlist);
        // TODO: Funktioniert so noch nicht. Damit unser Algorithmus stimmt,
        // dürfen alle Elemente der Liste nur ein Mal vorkommen. Brauchen also
        // noch irgendeine Art von reduce.

            List<Integer> sortedTestlist = new ArrayList(testlist);
            Collections.sort(sortedTestlist);
            sortedTestlists.add(sortedTestlists);
        }
    }

    @Test public void testAllSizes() {
        // Go through lists of various sizes
        for (int i = 0; i < sizes.length; ++i) {
            // Get the lists for the current length
            List<Integer> testlist       = testlists.get(i);
            List<Integer> sortedTestlist = sortedTestlists.get(i);

            // Test the correct the selection of every k-th smallest element
            for (int k = 0; k < testlist.size(); ++k) {
                assertEquals(
                    sortedTestlist.get(k),
                    Quickselect.select(k, testlist)
                );
            }
        }
    }


    // Test with empty list
    @Test(expected=IllegalArgumentException.class)
    public void testException0() {
        Quickselect.select(1, new ArrayList<Integer>());
    }

    // Test with too small index
    @Test(expected=IllegalArgumentException.class)
    public void testException1() {
        Quickselect.select(-10, testlists.get(2));
    }

    // Test with too large index
    @Test(expected=IllegalArgumentException.class)
    public void testException2() {
        List<Integer> testlist = testlists.get(3);
        Quickselect.select(testlist.size(), testlist);
    }

}

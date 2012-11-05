package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static alp3.ueb.Slowsort.*;
import static alp3.ueb.Permutations.*;

public class PermutationsTest {
    List<Integer> testlist;
    int testlistSize;
    Permutations testperms;


    public PermutationsTest() {
        super();
    }

    @Before public void setUp() {
        testlist     = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        testlistSize = testlist.size();
        testperms    = new Permutations(testlist);
    }

    @Test public void testNumberToPattern() {
        // The first few patterns
        int[][] testpatterns = {
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 1, 1, 0 },
            { 0, 0, 0, 0, 2, 0, 0 }
        };

        for (int i = 0; i < testpatterns.length; ++i) {
            assertTrue(
                Arrays.equals(testpatterns[i], testperms.numberToPattern(i))
            );
        }
    }

    @Test public void testPermutations0() {
        List<List<Integer>> allperms
            = new ArrayList<List<Integer>>( factorial(testlistSize) );

        // Create permutations of testlist0, one by one
        for ( ; testperms.hasMoreElements(); ) {
            List<Integer> perm = testperms.nextElement();

            // Permutation must have just as much elements as the original
            assertEquals(testlistSize, perm.size());

            // Permutation must not have occured already
            assertTrue( !allperms.contains(perm) );

            // Append this permutation to the list of all permutations
            allperms.add(perm);
        }

        // Make sure there are testlistSize0! permutations in all
        assertEquals(factorial(testlistSize), allperms.size());
    }

    @Test(expected=NoSuchElementException.class)
    public void testPermutations1() {
        List<Integer> testlist = Arrays.asList(1, 2);

        // Do nothing on all permutations
        Permutations perms = new Permutations(testlist);
        for ( ; perms.hasMoreElements(); ) {
            List<Integer> perm = perms.nextElement();
        }

        // Provoke the exception
        perms.nextElement();
    }

    @Test public void testFactorial0() {
        assertEquals(1, factorial(0));
        assertEquals(1, factorial(1));
        assertEquals(120, factorial(5));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFactorial1() {
        factorial(-1);
    }
}

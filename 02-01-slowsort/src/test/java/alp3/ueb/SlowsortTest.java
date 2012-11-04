package alp3.ueb;

import junit.framework.*;
import static junit.framework.Assert.*;

import java.util.Arrays;
import java.util.List;

public class SlowsortTest {
    private List<Integer> sortedList0;
    private List<Integer> unsortedList0;

    @Before public void setUp() {
        sortedList0 =   Arrays.asList(-13, -4, 3, 3, 800, 1344, 6_000_000);
        unsortedList0 = Arrays.asList(50, 100, 300, 33, 50);
    }

    @Test public void testIsSorted0() {
        assertTrue( isSorted(sortedList0) );
    }

    @Test public void testIsSorted1() {
        assertTrue( !isSorted(sortedList1) );
    }

    @Test public void testPermutations() {
        List<List<Integer>> perms = permutations(unsortedList0);
        assertEquals( perms.size() );
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

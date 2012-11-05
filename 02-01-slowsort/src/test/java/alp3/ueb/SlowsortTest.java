package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import alp3.ueb.Slowsort;

public class SlowsortTest {
    private List<Integer> sortedList0;
    private List<Integer> unsortedList0;

    @Before public void setUp() {
        sortedList0 =   Arrays.asList(-13, -4, 3, 3, 800, 1344, 6000000);
        unsortedList0 = Arrays.asList(50, 100, 300, 33, 50);
    }

    @Test public void testIsSorted0() {
        assertTrue( Slowsort.isSorted(sortedList0) );
    }

    @Test public void testIsSorted1() {
        assertTrue( !Slowsort.isSorted(unsortedList0) );
    }

    @Test public void testSort0() {
        List<Integer> tempList0 = new ArrayList(unsortedList0);
        Collections.sort( tempList0     );

        assertEquals(tempList0, Slowsort.sort(unsortedList0));
    }
}

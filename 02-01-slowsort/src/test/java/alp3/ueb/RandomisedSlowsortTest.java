package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import alp3.ueb.RandomisedSlowsort;

public class RandomisedSlowsortTest extends SlowsortTest {
    public RandomisedSlowsortTest() {
        super();
    }

    @Override @Test public void testSort0() {
        List<Integer> tempList0 = new ArrayList<Integer>(unsortedList0);
        Collections.sort( tempList0 );

        assertEquals(tempList0, RandomisedSlowsort.sort(unsortedList0));
    }
}

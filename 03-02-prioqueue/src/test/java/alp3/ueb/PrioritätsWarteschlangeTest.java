package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for testing the class <code>ArrayQueue</code>.
 */
public abstract class PrioritätsWarteschlangeTest {
    private static final int TESTENTRIES_CNT = 500;
    private static final int MAX_PRIORITY    = 100;

    public List< Eintrag<Integer, Integer> > testentries
        = new ArrayList< Eintrag<Integer, Integer> >(TESTENTRIES_CNT);
    public PrioritätsWarteschlange<Integer, Integer> testqueue0;

    /**
     * Generates an some test objects for the queue.
     */
    @Before public void setUp() {
        // Create list of random entries
        Random random = new Random();
        for (int i = 0; i < TESTENTRIES_CNT; ++i) {
            Integer randomInt = random.nextInt(MAX_PRIORITY);
            testentries.add(
                new Entry<Integer, Integer>(randomInt, randomInt));
        }

        testqueue0 = createPWS();
    }

    public abstract PrioritätsWarteschlange<Integer, Integer> createPWS();

    @Test public void testEinfuege0() {
        // Insert some element into our PWS
        int testindex = 4;
        testqueue0.einfuege(testentries.get(testindex));

        // Make sure it is there
        assertEquals(1, testqueue0.groesse());
        assertSame(testentries.get(testindex), testqueue0.min());

        // Make sure it is removed and returned
        assertSame(testentries.get(testindex), testqueue0.streicheMin());
        assertEquals(0, testqueue0.groesse());
        assertTrue(testqueue0.istLeer());
    }

    @Test public void testLeer0() {
        // Make sure there are no elements in an empty list
        assertEquals(0, testqueue0.groesse());
        assertTrue(testqueue0.istLeer());
    }

    @Test public void testMassive0() {
        // Add all entries to our priority queue
        for (Eintrag<Integer, Integer> entry : testentries) {
            testqueue0.einfuege(entry);
        }

        // Make sure there are as many entries as were added
        assertEquals(TESTENTRIES_CNT, testqueue0.groesse());

        // Get them back
        Eintrag<Integer, Integer> lastEntry = testqueue0.min();
        for (int i = 0; i < TESTENTRIES_CNT; ++i) {
            // Again make sure that min() returns the same as streicheMin()
            Eintrag<Integer, Integer> curEntry = testqueue0.min();
            assertSame(curEntry, testqueue0.streicheMin());

            // Make sure the priorities ascend
            assertTrue(curEntry.getSchluessel()
                               .compareTo(lastEntry.getSchluessel()) >= 0);
        }

        // Make sure the queue is empty now
        assertEquals(0, testqueue0.groesse());
        assertTrue(testqueue0.istLeer());
    }

    @Test(expected=LeereWarteschlangeException.class)
    public void testException0() {
        testqueue0.streicheMin();
    }

    @Test(expected=LeereWarteschlangeException.class)
    public void testException1() {
        testqueue0.min();
    }

    @Test(expected=LeereWarteschlangeException.class)
    public void testException2() {
        testqueue0.einfuege(testentries.get(10));
        testqueue0.streicheMin();
        testqueue0.streicheMin();
    }

    @Test(expected=LeereWarteschlangeException.class)
    public void testException3() {
        testqueue0.einfuege(testentries.get(7));
        testqueue0.streicheMin();
        testqueue0.min();
    }
}

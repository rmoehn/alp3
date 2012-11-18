package alp3.ueb;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Random;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.*;
import static org.junit.Assert.*;

public class DictTest {
    private static final int TESTKEY_LEN   = 2;
    private static final int TESTENTRY_CNT
        = ((int) Math.round( Math.pow(98, TESTKEY_LEN) )) + 100;
        // Want to have duplicate keys

    List<Eintrag<String, String>> testentries
        = new ArrayList<Eintrag<String, String>>(TESTENTRY_CNT);
    WoerterBuch<String, String> testdict0;

    @Before public void setUp() {
        // Generate a bunch of random entries
        for (int i = 0; i < TESTENTRY_CNT; ++i) {
            testentries.add(
                 new Entry<String, String>(
                     RandomStringUtils.randomAscii(TESTKEY_LEN),
                     RandomStringUtils.randomAscii(TESTKEY_LEN)
                 )
            );
        }

        testdict0 = new Dict<String, String>();
    }

    /**
     * Tests storage and retrieval of a large number of entries.
     */
    @Test public void testMassive0() {
        // Add a large number of entries to our dictionary
        for (Eintrag<String, String> entry : testentries) {
            testdict0.einfuege(entry);
        }

        assertEquals(TESTENTRY_CNT, testdict0.groesse());

        // Make sure that successive identical queries have the same result
        String testkey = testentries.get(10).getSchluessel();
        assertSame(testdict0.finde(testkey), testdict0.finde(testkey));

        // Retrieve them back and delete them
        Collections.shuffle(testentries, new Random());
        for (Eintrag<String, String> entry : testentries) {
            // Make sure retrieved entries have the key that was called for
            assertEquals(
                entry.getSchluessel(),
                testdict0.finde(entry.getSchluessel()).getSchluessel()
            );

            testdict0.streiche(entry);
                // Might not be the same entry as was removed
        }

        assertTrue(testdict0.istLeer());
    }

    /**
     * Makes sure that new dictionaries are empty
     */
    @Test public void testIstLeer0() {
        assertTrue(testdict0.istLeer());
    }

    /**
     * Makes sure that a dictionary is empty again after an element was added
     * and removed.
     */
    @Test public void testIstLeer1() {
        int testentryInd = 2;

        testdict0.einfuege(testentries.get(testentryInd));
        assertFalse(testdict0.istLeer());

        testdict0.streiche(testentries.get(testentryInd));
        assertTrue(testdict0.istLeer());
    }

    /**
     * Makes sure that a new dictionary as 0 elements.
     */
    @Test public void testGroesse0() {
        assertEquals(0, testdict0.groesse());
    }

    /**
     * Makes sure that we get the same object back we hav inserted (for one
     * object).
     */
    @Test public void testSingleEinfuege0() {
        Eintrag<String, String> testentry = testentries.get(4);

        testdict0.einfuege(testentry);
        assertSame(testentry, testdict0.finde(testentry.getSchluessel()));
    }

    /**
     * Makes sure that we get null when attempting to find nonexistent keys.
     */
    @Test public void testFinde0() {
        assertNull(testdict0.finde("a"));
    }

    /**
     * Makes sure that {@link findeAlle} works properly.
     */
    @Test public void testFindeAlle0() {
        String testkey   = "abc";
        int testentryCnt = 10;
        Collection<Eintrag<String, String>> testcollection
            = new ArrayList<Eintrag<String, String>>(testentryCnt);

        // Add some entries, all having the same key
        for (int i = 0; i < testentryCnt; ++i) {
            Entry<String, String> testentry
                = new Entry<String, String>(
                      testkey,
                      RandomStringUtils.randomAscii(TESTKEY_LEN)
                  );

            testdict0.einfuege(testentry);
            testcollection.add(testentry);
        }

        // Make sure they are all there (and not more)
        assertTrue(
            testcollection.containsAll( testdict0.findeAlle(testkey) )
        );
        assertEquals(testcollection.size(), testdict0.groesse());
    }

    /**
     * Makes sure that we get an empty collection when trying findeAlle()
     * on a nonexistent key.
     */
    @Test public void testFindeAlle1() {
        assertTrue(testdict0.findeAlle("b").isEmpty());
    }

    /**
     * Makes sure that removing a nonexistent entry causes an exception
     */
    @Test(expected=NoSuchElementException.class)
    public void testStreiche0() {
        testdict0.streiche(testentries.get(2));
    }

    /**
     * Makes sure that if there are duplicate entries in the dictionary,
     * streiche() removes only one.
     */
    @Test public void testStreiche1() {
        Eintrag<String, String> testentry = testentries.get(3);
        testdict0.einfuege(testentry);
        testdict0.einfuege(testentry);

        testdict0.streiche(testentry);
        assertFalse(testdict0.istLeer());
    }
}

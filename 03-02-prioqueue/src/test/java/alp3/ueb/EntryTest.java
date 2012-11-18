package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

public class EntryTest {
    String testkey0 = "Humpty";
    String testval0 = "Dumpty";
    String testkey1 = "Wall";
    String testval1 = "Horse";

    Eintrag<String, String> testentry0;
    Eintrag<String, String> testentry1;
    Eintrag<String, String> testentry2;
    Eintrag<String, String> testentry3;
    Eintrag<String, String> testentry4;

    @Before public void setUp() {
        testentry0 = new Entry<String, String>(testkey0, testval0);
        testentry1 = new Entry<String, String>(testkey0, testval0);

        testentry2 = new Entry<String, String>(testkey0, testval1);
        testentry3 = new Entry<String, String>(testkey1, testval0);
        testentry4 = new Entry<String, String>(testkey1, testval1);
    }

    @Test public void testEqualsReflexivity() {
        assertEquals(testentry0, testentry0);
    }

    @Test public void testEqualsSymmetry0() {
        assertEquals(testentry0, testentry1);
        assertEquals(testentry1, testentry0);
    }

    @Test public void testEqualsSymmetry1() {
        assertFalse(testentry0.equals(testentry2));
        assertFalse(testentry2.equals(testentry0));

        assertFalse(testentry0.equals(testentry3));
        assertFalse(testentry3.equals(testentry0));

        assertFalse(testentry0.equals(testentry4));
        assertFalse(testentry4.equals(testentry0));
    }

    @Test public void testEqualsNonnullity() {
        assertFalse(testentry0.equals(null));
    }

    @Test public void testEqualsOther0() {
        assertFalse(testentry0.equals(new Object()));
    }

    @Test public void testHashCodeConsistency() {
        assertEquals(testentry0.hashCode(), testentry0.hashCode());
    }

    @Test public void testHashCodeOther0() {
        assertEquals(testentry0.hashCode(), testentry1.hashCode());
    }
}

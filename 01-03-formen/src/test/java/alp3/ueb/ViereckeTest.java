package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing the class {@link Viereck} and its subclasses.
 */
public class ViereckeTest {
    private static final double DOUBLE_DELTA = 0.1;

    @Test public void testViereck() {
        Viereck test
            = Viereck.newSidesAngleInstance(3.00, 7.28, 7.21, 3.16, 1.8925);
        assertEquals(21.50, test.flaeche(), DOUBLE_DELTA);
        assertEquals(20.65, test.umfang(), DOUBLE_DELTA);
    }
}

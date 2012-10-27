package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for testing the class {@link Viereck} and its subclasses.
 */
public class ViereckeTest {
    /*
     * What follows is a horrible abuse of JUnit.
     */
    /*
     * And also a lot of duplication.
     */

    private static final double DELTA = 0.1;
    private List<Figur> allQuadlats = new ArrayList<Figur>();

    @Test public void testAll() {
        // Test Viereck
        Figur test
            = Viereck.newSidesAngleInstance(3.00, 7.28, 7.21, 3.16, 1.8925);
        assertEquals(21.50, test.flaeche(), DELTA);
        assertEquals(20.65, test.umfang(), DELTA);

        allQuadlats.add(test);

        // Test Trapez
        double[][] values = {
            {  35.0 ,  25.0,  17.0 , 0.3  * Math.PI, 412.60  ,   90.75},
            {   3.8 ,   5.2,   0.8 , 0.1  * Math.PI, 1.11    ,   11.97},
            { 290.0 , 660.0, 165.0 , 0.55 * Math.PI, 77410.07, 1495.82},
            {   1.45,   4.5,   0.65, 0.8  * Math.PI, 1.14    ,    9.15}
        };

        for (double[] trapez : values) {
            test = Trapez.newSidesAngleInstance(
                               trapez[0],
                               trapez[1],
                               trapez[2],
                               trapez[3]
                           );
            assertEquals(trapez[4], test.flaeche(), DELTA);
            assertEquals(trapez[5], test.umfang(),  DELTA);

            allQuadlats.add(test);
        }

        // Test Parallelogramm
        test
            = Parallelogramm.newSidesAngleInstance(12.4, 1.1, 0.05 * Math.PI);
        assertEquals(2.13, test.flaeche(), DELTA);
        assertEquals(27.0, test.umfang(), DELTA);

        allQuadlats.add(test);

        // Test Rechteck
        test = Rechteck.newSidesInstance(1.1, 0.5);
        assertEquals(0.55, test.flaeche(), DELTA);
        assertEquals(3.2, test.umfang(), DELTA);

        allQuadlats.add(test);

        // Test Quadrat
        test = Quadrat.newSideInstance(37.0);
        assertEquals(1369.0, test.flaeche(), DELTA);
        assertEquals(148.0, test.umfang(), DELTA);

        allQuadlats.add(test);

        // Test Drachenviereck
        test = Drachenviereck.newSidesAngleInstance(3.5, 2.0, 0.6 * Math.PI);
        assertEquals(6.66, test.flaeche(), DELTA);
        assertEquals(11.0, test.umfang(), DELTA);

        allQuadlats.add(test);

        // Test Raute
        test = Raute.newSideAngleInstance(21.0, 0.27 * Math.PI);
        assertEquals(330.80, test.flaeche(), DELTA);
        assertEquals(84.0, test.umfang(), DELTA);

        allQuadlats.add(test);


        // Test comparability
        Collections.sort(allQuadlats);

        for (int i = 0; i < allQuadlats.size() - 1; ++i) {
            assertTrue(
                allQuadlats.get(i).flaeche() <= allQuadlats.get(i+1).flaeche()
            );
        }
    }

    @Test(expected=IllegalArgumentException.class) public void excTest1() {
        Viereck.newSidesAngleInstance(3.0, 0.4, 6.4, 5.5, 0.0);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest2() {
        Trapez.newSidesAngleInstance(3.0, 0.4, 6.4, -0.5 * Math.PI);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest3() {
        Parallelogramm.newSidesAngleInstance(3.0, 5.5, Math.PI);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest4() {
        Drachenviereck.newSidesAngleInstance(3.0, 5.5, 0.4 * Math.PI);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest5() {
        Raute.newSideAngleInstance(3.0, 2 * Math.PI);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest6() {
        Rechteck.newSidesInstance(3.0, 0.0);
    }

    @Test(expected=IllegalArgumentException.class) public void excTest7() {
        Quadrat.newSideInstance(-1.5);
    }
}

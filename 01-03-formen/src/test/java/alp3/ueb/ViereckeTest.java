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
    private static final double DELTA = 0.1;
    private List<Figur> allQuadlats = new ArrayList<Figur>();

    @Test public void testViereck() {
        Figur test
            = Viereck.newSidesAngleInstance(3.00, 7.28, 7.21, 3.16, 1.8925);
        assertEquals(21.50, test.flaeche(), DELTA);
        assertEquals(20.65, test.umfang(), DELTA);

        allQuadlats.add(test);
    }

    @Test public void testTrapez() {
        double[][] values = {
            {  35.0 ,  25.0,  17.0 , 0.3  * Math.PI, 412.60  ,   90.75},
            {   3.8 ,   5.2,   0.8 , 0.1  * Math.PI, 1.11    ,   11.97},
            { 290.0 , 660.0, 165.0 , 0.55 * Math.PI, 77410.07, 1495.82},
            {   1.45,   4.5,   0.65, 0.8  * Math.PI, 1.14    ,    9.15}
        };

        for (double[] trapez : values) {
            Figur test = Trapez.newSidesAngleInstance(
                               trapez[0],
                               trapez[1],
                               trapez[2],
                               trapez[3]
                           );
            assertEquals(trapez[4], test.flaeche(), DELTA);
            assertEquals(trapez[5], test.umfang(),  DELTA);

            allQuadlats.add(test);
        }
    }

    @Test public void testParallelogramm() {
        Figur test
            = Parallelogramm.newSidesAngleInstance(12.4, 1.1, 0.05 * Math.PI);
        assertEquals(2.13, test.flaeche(), DELTA);
        assertEquals(27.0, test.umfang(), DELTA);

        allQuadlats.add(test);
    }

    @Test public void testRechteck() {
        Figur test = Rechteck.newSidesInstance(1.1, 0.5);
        assertEquals(0.55, test.flaeche(), DELTA);
        assertEquals(3.2, test.umfang(), DELTA);

        allQuadlats.add(test);
    }

    @Test public void testQuadrat() {
        Figur test = Quadrat.newSideInstance(37.0);
        assertEquals(1369.0, test.flaeche(), DELTA);
        assertEquals(148.0, test.umfang(), DELTA);

        allQuadlats.add(test);
    }

    @AfterClass @Test public void testSorting() {
        Collections.sort(allQuadlats);

        for (int i = 0; i < allQuadlats.size() - 1; ++i) {
            assertTrue(
                allQuadlats.get(i).flaeche() <= allQuadlats.get(i+1).flaeche()
            );
        }
    }
}

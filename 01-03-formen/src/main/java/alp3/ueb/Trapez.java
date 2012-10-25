package alp3.ueb;

import java.awt.geom.Point2D;
import java.lang.Math;

/**
 * A convex trapezoid.
 */
public class Trapez extends Viereck {

    /**
     * See {@link newFourPointsInstance}.
     */
    private Trapez(Point2D a, Point2D b, Point2D c, Point2D e) {
        super(a, b, c, d);
    }

    /**
     * Creates a new trapezoid specified by its cornerpoints. Cornerpoints
     * must be given in counter-clockwise order, a and b specifying the
     * first parallel and c and d the second. Inputs not compliant with this
     * may result in wrong outputs.
     *
     *     d       c
     *      +-----+
     *     /        \
     *    /           \
     *   +--------------+
     *  a                b
     */
    public Trapez newFourPointsInstance(Point2D a, Point2D b, Point2D c,
                                         Point2D d) {
        return new Trapez(a, b, c, d);
    }


    /*
     * Calculating the perimeter stays the same as for an ordinary
     * quadrilateral. Calculating the area could be different, but not
     * simpler, as we would have to calculate the height first. Thus we
     * inherit both methods.
     */
}

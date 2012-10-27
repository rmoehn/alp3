package alp3.ueb;

import java.lang.Math;

/**
 * A convex trapezium.
 */
public class Trapez extends Viereck {
    /**
     * See {@link newSidesAngleInstance}.
     */
    protected Trapez(double a, double c, double d, double alpha) {
        super(a, Math.sqrt(a*a + c*c + d*d + 2*d*Math.cos(alpha) * (c - a)
                           - 2*a*c), c, d, alpha);
    }

    /**
     * Creates a new trapezium specified by three of its sides and one angle.
     *
     *         c
     *      +-----+
     *   d /        \  b
     *    /alpha      \
     *   +--------------+
     *         a
     */
    public static Trapez newSidesAngleInstance(double a, double c, double d,
                                         double alpha) {
        return new Trapez(a, c, d, alpha);
    }

    /*
     * Calculating the perimeter stays the same as for an ordinary
     * quadrilateral.
     */

    /**
     * Calculate the area of this trapezium.
     */
    @Override public double flaeche() {
        return 0.5 * (a + c) * d*Math.sin(alpha);
    }
}

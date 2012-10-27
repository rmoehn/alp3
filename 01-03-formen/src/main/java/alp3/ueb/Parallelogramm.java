package alp3.ueb;

import java.lang.Math;

/**
 * A rhomboid.
 */
public class Parallelogramm extends Trapez {
    /**
     * See {@link newSidesAngleInstance}.
     */
    protected Parallelogramm(double a, double d, double alpha) {
        super(a, a, d, alpha);
    }

    /**
     * Creates a new rhomboid specified by two of its sides and the angle
     * between them.
     *
     *             c
     *      +--------------+
     *  d  /              /  b
     *    /alpha         /
     *   +--------------+
     *          a
     */
    public static Parallelogramm newSidesAngleInstance(double a, double d,
                                                 double alpha) {
        return new Parallelogramm(a, d, alpha);
    }

    /**
     * Returns the perimeter of this rhomboid.
     */
    @Override public double umfang() {
        return 2 * (a+d);
    }

    /**
     * Returns the area of this rhomboid.
     */
    @Override public double flaeche() {
        return a * d * Math.sin(alpha);
    }
}

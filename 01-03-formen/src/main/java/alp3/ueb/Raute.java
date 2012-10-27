package alp3.ueb;

import java.lang.Math;

/**
 * A rhomb.
 */
public class Raute extends Drachenviereck {
    /**
     * See {@link newSideAngleInstance}.
     */
    protected Raute(double a, double alpha) {
        super (a, a, alpha);
    }

    /**
     * Creates a new rhomb specified by a side and an angle.
     *
     *            +
     *           / \
     *          /   \
     *         /     \
     *        /       \
     *       /         \
     *      + alpha     +
     *       \         /
     *        \       /
     *       a \     /
     *          \   /
     *           \ /
     *            +
     */
    public static Raute newSideAngleInstance(double a, double alpha) {
        return new Raute(a, alpha);
    }

    /**
     * Returns the perimeter of this rhomb.
     */
    @Override public double umfang() {
        return 4 * a;
    }

    /**
     * Returns the area of this rhomb.
     */
    @Override public double flaeche() {
        return a*a * Math.sin(alpha);
    }
}

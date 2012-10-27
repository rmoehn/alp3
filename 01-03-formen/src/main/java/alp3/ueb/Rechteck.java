package alp3.ueb;

import java.lang.Math;

/**
 * A rectangle.
 */
public class Rechteck extends Parallelogramm {
    /**
     * See {@link newSidesInstance}.
     */
    protected Rechteck(double a, double b) {
        super(a, b, 0.5 * Math.PI);
    }

    /**
     * Creates a new rectangle specified by two of its sides.
     *
     *   +--------------+
     *   |              | b
     *   |              |
     *   +--------------+
     *         a
     */
    public static Rechteck newSidesInstance(double a, double b) {
        return new Rechteck(a, b);
    }

    /*
     * Perimeter stays the same as in {@link Parallelogramm}.
     */

    /**
     * Returns the area of this rectangle.
     */
    @Override public double flaeche() {
        return a * b;
    }
}

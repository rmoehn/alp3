package alp3.ueb;

import java.lang.Math;

/**
 * A square.
 */
public class Quadrat extends Rechteck {
    /**
     * See {@link newSideInstance}.
     */
    protected Quadrat(double a) {
        super(a, a);
    }

    /**
     * Creates a new rectangle specified one side.
     *
     *   +------+
     *   |      |
     *   |      |
     *   +------+
     *      a
     */
    public static Quadrat newSideInstance(double a) {
        return new Quadrat(a);
    }

    /**
     * Returns the perimeter of this square.
     */
    @Override public double umfang() {
        return 4 * a;
    }

    /**
     * Returns the area of this square.
     */
    @Override public double flaeche() {
        return a * a;
    }
}

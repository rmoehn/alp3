package alp3.ueb;

/**
 * A convex kite.
 */
public class Drachenviereck extends Viereck {
    /**
     * See {@link newSidesAngleInstance}.
     */
    protected Drachenviereck(double a, double d, double alpha) {
        super (a, a, d, d, alpha);
    }

    /**
     * Creates a new convex kite specified by two sides and the angle between
     * them.
     *
     *            +
     *       d  /   \
     *        /       \
     *      + alpha     +
     *       \         /
     *        \       /
     *       a \     /
     *          \   /
     *           \ /
     *            +
     *
     * @throws IllegalArgumentException if the given angle is
     * {@literal <= 0.5 * PI}
     */
    public static Drachenviereck newSidesAngleInstance(double a, double d,
                                                       double alpha) {
        // Make sure that the is not too small
        if (alpha - 0.5 * Math.PI < ANGLE_TOLERANCE) {
            throw new IllegalArgumentException(
                "Angle must be larger than 0.5 * PI.");
        }

        return new Drachenviereck(a, d, alpha);
    }

    /**
     * Returns the perimeter of this kite.
     */
    @Override public double umfang() {
        return 2 * (a + d);
    }

    /*
     * Calculating the area wouldn't be much simpler than with a generic
     * quadrilateral, so we don't override the superclass method.
     */
}

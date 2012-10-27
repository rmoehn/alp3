package alp3.ueb;

import java.awt.geom.Point2D;
import java.lang.Math;
/**
 * A convex quadrilateral.
 */
public class Viereck implements Figur {
    protected final double a;
    protected final double b;
    protected final double c;
    protected final double d;
    protected final double alpha;

    // Set an angle resolution of 0.001°
    private final static double ANGLE_TOLERANCE = Math.PI / 18000;

    /**
     * See {@link newFourPointsInstance}.
     */
    protected Viereck(double a, double b, double c, double d, double alpha) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.alpha = alpha;
    }

    /**
     * Creates a new convex quadrilateral specified by its four sides and one
     * angle. The angle must be the one between the sides given first and the
     * side given last.
     *
     *             c
     *       +-----------+
     *       |           |
     *     d |           | b
     *       |alpha      |
     *       +-----------+
     *             a
     *
     * (Contort the figure to your needs.)
     *
     * @throws IllegalArgumentException if the angle is {@literal >=} PI
     */
    public static Viereck newSidesAngleInstance(double a, double b, double c,
                                          double d, double alpha) {
        // Check for bad input
        if (Math.PI - alpha < ANGLE_TOLERANCE) {
            throw new IllegalArgumentException("Angle too large.");
        }
        if (a * b * c * d == 0) {
            throw new IllegalArgumentException(
                "Lengths of sides must be > 0.");
        }

        return new Viereck(a, b, c, d, alpha);
    }

    /**
     * Returns the perimeter of this quadrilateral.
     */
    public double umfang() {
        return a + b + c + d; // Yeah!
    }

    /**
     * Returns the area of this quadrilateral.
     */
    public double flaeche() {
        // Calculate the square of the diagonal opposite the given angle
        double fsquare = a*a + d*d - 2*a*d*Math.cos(alpha);

        // Calculate the angle opposite the given angle
        double gamma = Math.acos( (fsquare - c*c - b*b) / (-2*c*b) );

        return 0.5 * (a*d*Math.sin(alpha) + b*c*Math.sin(gamma));
    }

    /**
     * Compares two {@code Figur}s by their areas.
     */
    public int compareTo(Figur that) {
        double thatArea = that.flaeche();
        double thisArea = this.flaeche();

        if (thisArea > thatArea) {
            return 1;
        }
        if (thisArea < thatArea) {
            return -1;
        }
        return 0;
    }
}

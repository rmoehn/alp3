package alp3.ueb;

import java.awt.geom.Point2D;
import java.lang.Math;
/**
 * A convex quadrilateral.
 */
public class Viereck implements Form {
    protected final Point2D a;
    protected final Point2D b;
    protected final Point2D c;
    protected final Point2D d;

    /**
     * See {@link newFourPointsInstance}.
     */
    private Viereck(Point2D a, Point2D b, Point2D c, Point2D e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Creates a new quadrilateral specified by its cornerpoints. It is
     * assumed that the cornerpoints indeed specify a convex quadrilateral.
     * Wrong calculations will result from wrong inputs.
     */
    public Viereck newFourPointsInstance(Point2D a, Point2D b, Point2D c,
                                         Point2D d) {
        return new Viereck(a, b, c, d);
    }

    /**
     * Returns the perimeter of this quadrilateral.
     */
    public double umfang() {
        return a.distance(b) + b.distance(c) + c.distance(d) + d.distance(a);
    }

    /**
     * Returns the area of this quadrilateral.
     */
    public double flaeche() {
        // Using a formula by old Gauss, see
        // http://de.wikipedia.org/wiki/Viereck (2012-10-25).
        return 0.5 * Math.abs(
                         (a.getY() - c.getY()) * (d.getX() - b.getX())
                       + (b.getY() - d.getY()) * (a.getX() - c.getX())
                     );
    }
}

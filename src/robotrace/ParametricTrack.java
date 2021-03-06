
package robotrace;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) {
        double x = 10*cos(2*PI*t);
        double y = 14*sin(2*PI*t);
        Vector p = new Vector(x, y, 1);
        return p;

    }

    @Override
    protected Vector getTangent(double t) {
        double x = -20*PI*sin(2*PI*t);
        double y = 28*PI*cos(2*PI*t);
        Vector T = new Vector(x, y, 0);
        return T;
    }
    
}

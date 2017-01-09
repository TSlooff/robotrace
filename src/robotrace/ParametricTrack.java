
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
        Vector p = new Vector(10*cos(2*PI*t), 14*sin(2*PI*t), 1);
        return p;

    }

    @Override
    protected Vector getTangent(double t) {
        Vector T = new Vector(-20*PI*sin(2*PI*t), 28*PI*cos(2*PI*t), 0);
        return T;

    }
    
}

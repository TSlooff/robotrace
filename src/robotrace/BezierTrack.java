
package robotrace;


/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    
    final private Vector[] controlPoints;
    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
    }
    
    @Override
    protected Vector getPoint(double t) {
        t %= 1;
        int NSegment = controlPoints.length/4;
        int currentSegment = (int)Math.floor(t*NSegment)*4;
        t = t * NSegment;
        t = t % 1;
        return getCubicBezierPnt(t, controlPoints[currentSegment], controlPoints[currentSegment + 1], controlPoints[currentSegment + 2], controlPoints[currentSegment + 3]);
    }

    @Override
    protected Vector getTangent(double t) {
        t %= 1;
        int NSegment = controlPoints.length / 4;
        int currentSegment = (int)Math.floor(t * NSegment)*4;
        t = t * NSegment;
        t = t % 1;
        return getCubicBezierTng(t, controlPoints[currentSegment], controlPoints[currentSegment + 1], controlPoints[currentSegment + 2], controlPoints[currentSegment + 3]);

    }
    
    private Vector getCubicBezierPnt(double t, Vector P0, Vector P1, Vector P2, Vector P3) {

        //P(t)= (1-t)^3 * P0 + 3t(1-t)^2P1 + (1-t)3t^2P2 + t^3P3
        Double x = Math.pow((1 - t), 3) * P0.x + 3 * t * Math.pow((1 - t), 2) * P1.x + 3 * Math.pow(t, 2) * (1 - t) * P2.x + Math.pow(t, 3) * P3.x;
        Double y = Math.pow((1 - t), 3) * P0.y + 3 * t * Math.pow((1 - t), 2) * P1.y + 3 * Math.pow(t, 2) * (1 - t) * P2.y + Math.pow(t, 3) * P3.y;
        Double z = Math.pow((1 - t), 3) * P0.z + 3 * t * Math.pow((1 - t), 2) * P1.z + 3 * Math.pow(t, 2) * (1 - t) * P2.z + Math.pow(t, 3) * P3.z;

        return new Vector(x, y, z);
    }
    
    private Vector getCubicBezierTng(double t, Vector P0, Vector P1, Vector P2, Vector P3) {

        //P'(t) = 3 * (1-t)^2 * (P1 - P0) + 6 * (1-t) * t * (P2 - P1) + 3 * t^2 * (P3 - P2)
        Double x = 3 * Math.pow(1 - t, 2) * (P1.x - P0.x) + 6 * (1 - t) * t * (P2.x - P1.x) + 3 * Math.pow(t, 2) * (P3.x - P2.x);
        Double y = 3 * Math.pow(1 - t, 2) * (P1.y - P0.y) + 6 * (1 - t) * t * (P2.y - P1.y) + 3 * Math.pow(t, 2) * (P3.y - P2.y);
        Double z = 3 * Math.pow(1 - t, 2) * (P1.z - P0.z) + 6 * (1 - t) * t * (P2.z - P1.z) + 3 * Math.pow(t, 2) * (P3.z - P2.z);

        return new Vector(x, y, z);
    }

}

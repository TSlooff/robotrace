package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    int parametric;
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
        setTrack();
    }

    public void setTrack() {
        parametric = 0;
    }
    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, Texture track, Texture brick) {
        
    }
    
    private void setMaterial(Material material, GL2 gl) {
        //Set material determined by given robot type
        gl.glMaterialf(GL_FRONT, GL_SHININESS, material.shininess);
        gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, material.specular, 0);
    }
    
    public void setLaneColors(int lane_number, GL2 gl) {
        switch (lane_number) {
            case 0:
                setMaterial(Material.GOLD, gl);
                break;
            case 1:
                setMaterial(Material.SILVER, gl);
                break;
            case 2:
                setMaterial(Material.WOOD, gl);
                break;
            case 3:
                setMaterial(Material.ORANGE, gl);
                break;
            default:
                setMaterial(Material.GOLD, gl);
                break;
        }
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){

        return Vector.O;

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        return Vector.O;

    }
    
    private Vector getCubicBezierPnt(double t, Vector P0, Vector P1, Vector P2, Vector P3) {

        //P(u)= (1-u)^3 * P0 + 3u(1-u)^2P1 + (1-u)3u^2P2 + u^3P3
        Double x = Math.pow((1 - t), 3) * P0.x + 3 * t * Math.pow((1 - t), 2) * P1.x + 3 * Math.pow(t, 2) * (1 - t) * P2.x + Math.pow(t, 3) * P3.x;
        Double y = Math.pow((1 - t), 3) * P0.y + 3 * t * Math.pow((1 - t), 2) * P1.y + 3 * Math.pow(t, 2) * (1 - t) * P2.y + Math.pow(t, 3) * P3.y;
        Double z = Math.pow((1 - t), 3) * P0.z + 3 * t * Math.pow((1 - t), 2) * P1.z + 3 * Math.pow(t, 2) * (1 - t) * P2.z + Math.pow(t, 3) * P3.z;

        return new Vector(x, y, z);
    }
    
    private Vector getCubicBezierTng(double t, Vector P0, Vector P1, Vector P2, Vector P3) {

        //To get the tangent we take the derivative
        //P'(u) = 3 * (1-u)^2 * (P1 - P0) + 6 * (1-u) * u * (P2 - P1) + 3 * u^2 * (P3 - P2)
        Double x = 3 * Math.pow(1 - t, 2) * (P1.x - P0.x) + 6 * (1 - t) * t * (P2.x - P1.x) + 3 * Math.pow(t, 2) * (P3.x - P2.x);
        Double y = 3 * Math.pow(1 - t, 2) * (P1.y - P0.y) + 6 * (1 - t) * t * (P2.y - P1.y) + 3 * Math.pow(t, 2) * (P3.y - P2.y);
        Double z = 3 * Math.pow(1 - t, 2) * (P1.z - P0.z) + 6 * (1 - t) * t * (P2.z - P1.z) + 3 * Math.pow(t, 2) * (P3.z - P2.z);

        return new Vector(x, y, z);
    }
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}

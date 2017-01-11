package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        double stepsize = 0.01;
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p2 = new Vector(getPoint(t).x + changeLane.x, getPoint(t).y + changeLane.y, 1);
            gl.glVertex3d(p2.x, p2.y, p2.z);
            Vector p3 = new Vector(getPoint(t).x + 2*changeLane.x, getPoint(t).y + 2*changeLane.y, 1);
            gl.glVertex3d(p3.x, p3.y, p3.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d); //normalize it
            Vector p3 = new Vector(getPoint(t).x + 2*changeLane.x, getPoint(t).y + 2*changeLane.y, 1);
            gl.glVertex3d(p3.x, p3.y, p3.z);
            gl.glVertex3d(p3.x, p3.y, 0d);
        }
        gl.glEnd();
                
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(0, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            gl.glVertex3d(p.x, p.y, p.z);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p2 = new Vector(getPoint(t).x + changeLane.x, getPoint(t).y + changeLane.y, 1);
            gl.glVertex3d(p2.x, p2.y, p2.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(0, 0, 255);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            gl.glVertex3d(p.x, p.y, p.z);
            Vector p4 = new Vector(getPoint(t).x - changeLane.x, getPoint(t).y - changeLane.y, 1);
            gl.glVertex3d(p4.x, p4.y, p4.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p4 = new Vector(getPoint(t).x - changeLane.x, getPoint(t).y - changeLane.y, 1);
            gl.glVertex3d(p4.x, p4.y, p4.z);
            Vector p5 = new Vector(getPoint(t).x - 2*changeLane.x, getPoint(t).y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p5 = new Vector(getPoint(t).x - 2*changeLane.x, getPoint(t).y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
            gl.glVertex3d(p5.x, p5.y, 0d);
        }
        gl.glEnd();
        
        gl.glBegin(GL_LINE_STRIP);
        gl.glLineWidth(4);
        gl.glColor3f(0, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = getPoint(t);
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p5 = new Vector(getPoint(t).x - 2*changeLane.x, getPoint(t).y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_LINE_STRIP);
        gl.glLineWidth(4);
        gl.glColor3f(0, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector T = getTangent(t);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            Vector p3 = new Vector(getPoint(t).x + 2*changeLane.x, getPoint(t).y + 2*changeLane.y, 1);
            gl.glVertex3d(p3.x, p3.y, p3.z);
        }
        gl.glEnd();
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
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}

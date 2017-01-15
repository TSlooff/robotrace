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
        double stepsize = 0.01;
        
        drawSides(gl, glu, glut, track, brick);
        
        track.enable(gl);
        track.bind(gl);
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p1 = new Vector(p.x + changeLane.x, p.y + changeLane.y, 1);
            gl.glTexCoord2d(t, t);
            gl.glVertex3d(p1.x, p1.y, p1.z);
            Vector p2 = new Vector(p.x + 2*changeLane.x, p.y + 2*changeLane.y, 1);
            gl.glTexCoord2d(1-t, 1-t);
            gl.glVertex3d(p2.x, p2.y, p2.z);
        }
        gl.glEnd();
                
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(0, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            gl.glVertex3d(p.x, p.y, p.z);
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p3 = new Vector(p.x + changeLane.x, p.y + changeLane.y, 1);
            gl.glVertex3d(p3.x, p3.y, p3.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(0, 0, 255);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            gl.glVertex3d(p.x, p.y, p.z);
            Vector p4 = new Vector(p.x - changeLane.x, p.y - changeLane.y, 1);
            gl.glVertex3d(p4.x, p4.y, p4.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p4 = new Vector(p.x - changeLane.x, p.y - changeLane.y, 1);
            gl.glVertex3d(p4.x, p4.y, p4.z);
            Vector p5 = new Vector(p.x - 2*changeLane.x, p.y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
        }
        gl.glEnd();
                
        gl.glBegin(GL_LINE_STRIP);
        gl.glLineWidth(4);
        gl.glColor3f(0, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p5 = new Vector(p.x - 2*changeLane.x, p.y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
        }
        gl.glEnd();
        
        gl.glBegin(GL_LINE_STRIP);
        gl.glLineWidth(4);
        gl.glColor3f(0, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p3 = new Vector(p.x + 2*changeLane.x, p.y + 2*changeLane.y, 1);
            gl.glVertex3d(p3.x, p3.y, p3.z);
        }
        gl.glEnd();
        
        track.disable(gl);
    }
    
    public void drawSides(GL2 gl, GLU glu, GLUT glut, Texture track, Texture brick){
        double stepsize = 0.01;
        brick.enable(gl);
        brick.bind(gl);
        //draw inner side of track
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 0, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d); //normalize it
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p1 = new Vector(p.x + 2*changeLane.x, p.y + 2*changeLane.y, 1);
            gl.glVertex3d(p1.x, p1.y, p1.z);
            gl.glVertex3d(p1.x, p1.y, 0d);
        }
        gl.glEnd();
        
        //draw outer side of cube
        gl.glBegin(GL_TRIANGLE_STRIP);
        gl.glColor3f(255, 255, 0);
        for (double t = 0; t<=1.01; t = t + stepsize) {
            Vector p = new Vector(0,0,1);
            Vector T = new Vector(0,0,1);
            if (parametric == 1){
                p = getPoint(t);
                T = getTangent(t);
            } else if (parametric == 0) {
                    p = getCubicBezierPnt(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
                    T = getCubicBezierTng(t, new Vector(30,0,1), new Vector(30,20,1), new Vector(-30,20,1), new Vector(-30,0,1));
            }
            //calculate point in the next lane, use normal vector
            Vector changeLane = new Vector(-T.y, T.x, 0d);
            double length = Math.sqrt(changeLane.x * changeLane.x + changeLane.y * changeLane.y);
            changeLane = new Vector(changeLane.x/length, changeLane.y/length, 0d);
            changeLane = new Vector(changeLane.x * laneWidth, changeLane.y * laneWidth, changeLane.z * laneWidth);
            Vector p5 = new Vector(p.x - 2*changeLane.x, p.y - 2*changeLane.y, 1);
            gl.glVertex3d(p5.x, p5.y, p5.z);
            gl.glVertex3d(p5.x, p5.y, 0d);
        }
        gl.glEnd();
        
        brick.disable(gl);
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

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
    private final static float stepSize = 0.01f;
    private final static float laneHeight = 2f;
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {

    }
    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, Texture track, Texture brick) {
        
        // draw track
        track.enable(gl);
        track.bind(gl);
        drawTrack(gl, glu, glut);
        track.disable(gl);
        
        //draw sides of track
        brick.enable(gl);
        brick.bind(gl);
        drawSides(gl, glu, glut);
        brick.disable(gl);
    }
    
    public void drawTrack(GL2 gl, GLU glu, GLUT glut){
        
        for(int lane = 0; lane < 4; lane+=2) {
            gl.glBegin(GL_TRIANGLE_STRIP);	
            boolean top = false;
            for(float i = 0; i <= 1.0f; i+= stepSize) {
                Vector point = getLanePoint(lane, i);
                Vector tangent = getLaneTangent(lane, i);
                Vector normal = tangent.cross(new Vector(0,0,1)).normalized();
                Vector outer_p = point.add(normal.scale(laneWidth/2 + laneWidth));
                Vector inner_p = point.add(normal.scale(-laneWidth/2));
                gl.glTexCoord2d(0, top?1:0);
                gl.glVertex3d( inner_p.x, inner_p.y, inner_p.z ); 
                gl.glTexCoord2d(1, top?1:0);
                gl.glVertex3d( outer_p.x, outer_p.y, outer_p.z ); 
                top = !top;
            }
            gl.glEnd();
        }
    }
    
    public void drawSides(GL2 gl, GLU glu, GLUT glut){
        
        gl.glBegin(GL_TRIANGLE_STRIP);	
        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector point = getLanePoint(0, i);
            Vector tangent = getLaneTangent(0, i);
            Vector normal = tangent.cross(new Vector(0,0,1)).normalized();
            Vector inner_p = point.add(normal.scale(-laneWidth/2));
            gl.glTexCoord2f(i * 20 % 1, 0);
            gl.glVertex3d( inner_p.x, inner_p.y, inner_p.z ); 
            gl.glTexCoord2f(i * 20 % 1, 1);
            gl.glVertex3d( inner_p.x, inner_p.y, inner_p.z-laneHeight ); 

        }
        gl.glEnd();
        
        gl.glBegin(GL_TRIANGLE_STRIP);	

        for(float i = 0; i <= 1.0f; i+= stepSize) {
            Vector point = getLanePoint(3, i);
            Vector tangent = getLaneTangent(3, i);
            Vector normal = tangent.cross(new Vector(0,0,1)).normalized();
            Vector outer_p = point.add(normal.scale(laneWidth/2));
            gl.glTexCoord2f(i * 20 % 1, 0);
            gl.glVertex3d( outer_p.x, outer_p.y, outer_p.z ); 
            gl.glTexCoord2f(i * 20 % 1, 1);
            gl.glVertex3d( outer_p.x, outer_p.y,outer_p.z -laneHeight); 

        }
        gl.glEnd();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        
        Vector tangent = getLaneTangent(0, t);
        Vector normal = tangent.cross(Vector.Z).normalized();
        return getPoint(t).add(normal.scale(laneWidth * lane));    

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        
        if (lane == 0){ 
            return getTangent(t);
        }
        else {// on other lanes tangent is changed
            return getLanePoint(lane, t + 0.000001).subtract(getLanePoint(lane, t));
        }
    }
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}

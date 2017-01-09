package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
            
    ) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        //draw the robot here using a hierarchical model
        gl.glColor3i(0,0,0);
        drawLeg(gl, glu, glut, tAnim);
        gl.glPushMatrix();
         gl.glTranslated(2,0,0);
         drawLeg(gl, glu, glut, tAnim);
        gl.glPopMatrix();
    }
    
    public void drawTorso(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
    
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
    
    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
    
    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
         gl.glTranslated(0.5, 0, 0.5);
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawFoot(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
    
    public void drawHand(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
}

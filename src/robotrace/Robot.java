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
        drawArm(gl, glu, glut, tAnim);
            gl.glPushMatrix();
                gl.glTranslated(2,0,0);
                drawLeg(gl, glu, glut, tAnim);
            gl.glPopMatrix();
            gl.glPushMatrix();
                gl.glTranslated(-4,0,0);
                drawArm(gl, glu, glut, tAnim);
            gl.glPopMatrix();
        drawTorso(gl, glu, glut, tAnim);
        drawHead(gl, glu, glut, tAnim);
    }
    
    public void drawTorso(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
         gl.glTranslated(0, 0, 3);
         gl.glScaled(3,2,3.5);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim) {
         gl.glPushMatrix();
         gl.glTranslated(0, 0, 5.5);
         gl.glRotated(90,0,1,0);
         gl.glRotated(90,0,0,1);
         gl.glScaled(1.5,1.5,1.5);
         glut.glutSolidTeapot(1);
        gl.glPopMatrix();
    }
    
    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
         gl.glTranslated(2, 0, 3.25);
         gl.glScaled(1,1,3);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        gl.glPushMatrix();
         gl.glTranslated(-1, 0, 0.5);
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawFoot(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
    
    public void drawHand(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
    }
}

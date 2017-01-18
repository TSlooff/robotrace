package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static java.lang.Math.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL3.GL_TIMESTAMP;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);
    
    //Vector pointing upwards, used for crossproduct to calculate the upVector from the track
    public Vector XVec = new Vector(1,0,0);

    /** The material from which this robot is built. */
    private final Material material;
    
    public float diff = 0f;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material) {
        this.material = material;    
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        //draw the robot here using a hierarchical model
        float scaleFactor = 0.15f;
        gl.glPushMatrix();
        gl.glTranslated(position.x, position.y, position.z);
        direction = direction.normalized();
        
        Vector upwardFromTrack = XVec.cross(direction); //vector pointing upwards from track, normalized
        
        gl.glRotated(90 + Math.acos(XVec.dot(direction)) / Math.PI * 180, upwardFromTrack.x,upwardFromTrack.y,upwardFromTrack.z);
        //gl.glRotated(90, upwardFromTrack.x, upwardFromTrack.y, upwardFromTrack.z);
        gl.glScaled(scaleFactor, scaleFactor, scaleFactor);
        gl.glTranslated(0, 0, 7.5);
        drawHead(gl, glu, glut, tAnim, gs);
        gl.glPopMatrix();
    }
    
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(1, 0, 1 );
         gl.glPushMatrix();
         gl.glRotated(90,0,1,0);
         gl.glRotated(90,0,0,1);
         gl.glRotated(15*sin(5*gs.tAnim),0,0,1);
         gl.glScaled(1.5,1.5,1.5);
         glut.glutSolidTeapot(1);
        gl.glPopMatrix();
        drawTorso(gl, glu,glut,tAnim, gs);
    }
    
        
    public void drawTorso(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(0, 1, 1 );
        gl.glPushMatrix();
         gl.glTranslated(0, 0, -2.75);
         gl.glScaled(3,1,3.5);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawRightArm(gl, glu, glut, tAnim, gs);
        drawLeftUpperLeg(gl, glu, glut, tAnim, gs);
    }
    
    public void drawRightArm(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(0, 0, 1 );
        gl.glPushMatrix();
         gl.glTranslated(2, 0, -2.5);
         
         gl.glRotated(-20*sin(5*gs.tAnim)+20,1,0,0);
         gl.glTranslated(0,-0.1*sin(5*gs.tAnim)+0.1,0);
          gl.glTranslated(0,-0.3*sin(5*gs.tAnim)+0.3,0);
         
         gl.glScaled(1,1,3);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawLeftArm(gl, glu, glut, tAnim, gs);
        drawRightHand(gl, glu, glut, tAnim, gs);
    }
    
      public void drawLeftArm(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(0, 0, 1 );
        gl.glPushMatrix();
         gl.glTranslated(-2, 0.25, -3);
         gl.glRotated(-20*sin(-1)+20,1,0,0);
         gl.glTranslated(0,-0.1*sin(-1)+0.1,0);
          gl.glTranslated(0,-0.3*sin(-1)+0.3,0);
          
          gl.glRotated(20*sin(5*gs.tAnim)-20,1,0,0);
         gl.glTranslated(0,0.1*sin(5*gs.tAnim)-0.1,0);
          gl.glTranslated(0,0.3*sin(5*gs.tAnim)-0.3,0);
          
         gl.glScaled(1,1,3);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawLeftHand(gl, glu, glut, tAnim, gs);
    }
    
    public void drawLeftUpperLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(1, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(-1, 0, -5.25);
         gl.glRotated(-20*sin(5*gs.tAnim)+20,1,0,0);
         gl.glTranslated(0,-0.3*sin(5*gs.tAnim)+0.3,-0.05*sin(5*gs.tAnim)+0.05);
         gl.glTranslated(0,-0.1*sin(5*gs.tAnim)+0.1,0);
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawRightUpperLeg(gl, glu, glut, tAnim, gs);
        drawLeftUnderLeg(gl, glu, glut, tAnim, gs);
    }
    
      public void drawRightUpperLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(1, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(1, 0, -5.25);
         
         gl.glRotated(-20*sin(-1)+20,1,0,0);
         gl.glTranslated(0,-0.3*sin(-1)+0.3,-0.05*sin(-1)+0.05);
         gl.glTranslated(0,-0.1*sin(-1)+0.1,0);
         
         gl.glRotated(20*sin(5*gs.tAnim)-20,1,0,0);
         gl.glTranslated(0,0.275*sin(5*gs.tAnim)-0.275,0.2*sin(5*gs.tAnim)-0.2);
         gl.glTranslated(0,0.075*sin(5*gs.tAnim)+0.075,0);
         
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();

        drawRightUnderLeg(gl, glu, glut, tAnim, gs);
    }
    
    public void drawLeftUnderLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(1, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(-1, 0, -6.75);
         gl.glRotated(20*sin(5*gs.tAnim)-20,1,0,0);
         gl.glTranslated(0,-0.05*sin(5*gs.tAnim)+0.05,-0.5*sin(5*gs.tAnim)+0.5);
         gl.glTranslated(0,-0.1*sin(5*gs.tAnim)+0.1,0);
         gl.glTranslated(0,0,-0.1*sin(5*gs.tAnim)+0.1);
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawLeftFoot(gl, glu,glut, tAnim, gs);
    }
    
    public void drawRightUnderLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(1, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(1, 0, -6.75);
         gl.glRotated(20*sin(-1)-20,1,0,0);
         gl.glTranslated(0,-0.05*sin(-1)+0.05,-0.5*sin(-1)+0.5);
         gl.glTranslated(0,-0.2*sin(-1)+0.2,0);
         gl.glTranslated(0,0,-0.1*sin(-1)+0.1);
         
         gl.glRotated(-20*sin(5*gs.tAnim)+20,1,0,0);
         gl.glTranslated(0,0.22*sin(5*gs.tAnim)-0.22, 0.2*sin(5*gs.tAnim)-0.2);
         gl.glTranslated(0,0.33*sin(5*gs.tAnim)-0.33,0);
         gl.glTranslated(0,0,+0.1*sin(5*gs.tAnim)-0.1);
         
         gl.glScaled(1,1,2);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
        drawRightFoot(gl, glu,glut, tAnim, gs);
    }
    
    public void drawLeftFoot(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
           gl.glColor3f(0, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(-1, 0.75, -7.5);
         gl.glRotated(20*sin(5*gs.tAnim)-20,1,0,0);
         gl.glTranslated(0,-0.005*sin(5*gs.tAnim)+0.005,-0.2*sin(5*gs.tAnim)+0.2);
         gl.glTranslated(0,-0.005*sin(5*gs.tAnim)+0.005,0);
         gl.glTranslated(0,0,-0.1*sin(5*gs.tAnim)+0.1);
         gl.glScaled(1,0.5,0.5);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawRightFoot(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
           gl.glColor3f(0, 0, 0 );
        gl.glPushMatrix();
         gl.glTranslated(1, 0.75, -7.5);
         gl.glRotated(20*sin(-1)-20,1,0,0);
         gl.glTranslated(0,-0.01*sin(-1)+0.01,-0.2*sin(-1)+0.2);
         gl.glTranslated(0,-0.05*sin(-1)+0.05,0);
         gl.glTranslated(0,0,-0.1*sin(-1)+0.1);
         
         gl.glRotated(-20*sin(5*gs.tAnim)+20,1,0,0);
         gl.glTranslated(0,0.15*sin(5*gs.tAnim)-0.15,0.05*sin(5*gs.tAnim)-0.05);
         gl.glTranslated(0,0.05*sin(5*gs.tAnim)-0.05,0);
         gl.glTranslated(0,0,+0.1*sin(5*gs.tAnim)-0.1);
         
         gl.glScaled(1,0.5,0.5);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
    
    public void drawLeftHand(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(0, 0, 0 );
        gl.glPushMatrix();
        gl.glTranslated(-2, 1.25, -4.5);
        gl.glRotated(-20*sin(-1)+20,1,0,0);
        gl.glTranslated(0,-0.1*sin(-1)+0.1,0);
        gl.glTranslated(0,-0.25*sin(-1)+0.25,0);
          
        gl.glRotated(20*sin(5*gs.tAnim)-20,1,0,0);
        gl.glTranslated(0,0.1*sin(5*gs.tAnim)-0.1,0.1*sin(5*gs.tAnim)-0.1);
        gl.glTranslated(0,0.3*sin(5*gs.tAnim)-0.3,0);
        gl.glTranslated(0,0.5*sin(5*gs.tAnim)-0.5,0);
        gl.glScaled(0.5,1,1);
        gl.glRotated(90,0,1,0);
        gl.glRotated(90,0,0,1);
        glut.glutSolidTeapot(1);
        gl.glPopMatrix();
    }
    
      public void drawRightHand(GL2 gl, GLU glu, GLUT glut, float tAnim, GlobalState gs) {
        gl.glColor3f(0, 0, 0 );
        gl.glPushMatrix();
        gl.glTranslated(2, 0.25, -4.4);
        
        gl.glRotated(-20*sin(5*gs.tAnim)+20,1,0,0);
        gl.glTranslated(0,-0.1*sin(5*gs.tAnim)+0.1,0.1*sin(5*gs.tAnim)-0.1);
        gl.glTranslated(0,-0.3*sin(5*gs.tAnim)+0.3,0);
        gl.glTranslated(0,-0.5*sin(5*gs.tAnim)+0.5,0);
         
        gl.glScaled(0.5,1,1);
        gl.glRotated(90,0,1,0);
        gl.glRotated(90,0,0,1);
        glut.glutSolidTeapot(1);
        gl.glPopMatrix();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static java.lang.Math.sin;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author s142514
 */
public class Tree {
    
    /** The position of the tree. */
    public Vector position = new Vector(0, 0, 0);
    
    //Vector pointing upwards, used for crossproduct to calculate the upVector from the track
    public Vector XVec = new Vector(1,0,0);
    
    public float coneSize;
    public float trunkSize;
    
    public Tree(float conesize, float trunksize) {
        coneSize = conesize;
        trunkSize = trunksize;
    }
    

 public void draw(GL2 gl, GLU glu, GLUT glut, Vector position) {
     
     gl.glPushMatrix();
     gl.glTranslated(position.x, position.y, position.z);
     drawCone(gl,glu,glut);
     drawTrunk(gl,glu,glut);
     drawBranch(gl, glu, glut);
     gl.glPopMatrix();
    
}


public void drawCone(GL2 gl, GLU glu, GLUT glut) {
        gl.glColor3f(0, 1, 0 );
         gl.glPushMatrix();
         gl.glScaled(coneSize, coneSize, coneSize);
         gl.glScaled(1.5,1.5,1.5);
         glut.glutSolidCone(1,2,15,2);
        gl.glPopMatrix();
    }


public void drawTrunk(GL2 gl, GLU glu, GLUT glut) {
        gl.glColor3f(0.54509803921f, 0.27058823529f, 0.07450980392f );
         gl.glPushMatrix();
         gl.glScaled(1, 1, trunkSize);
         gl.glTranslated(0,0,-0.5);
         gl.glScaled(1,1,1);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }

public void drawBranch(GL2 gl, GLU glu, GLUT glut) {
        gl.glColor3f(0.7f, 0.27058823529f, 0.07450980392f );
         gl.glPushMatrix();
         gl.glTranslated(1,0,-0.5);
         gl.glScaled(1,0.5,0.5);
         glut.glutSolidCube(1);
        gl.glPopMatrix();
    }
}
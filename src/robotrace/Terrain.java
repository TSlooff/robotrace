package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL2.*;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        float stepSize = 0.35f;
        for(float x = -50; x < 50; x +=stepSize) {	
            gl.glBegin(GL_TRIANGLE_STRIP);	
            for(float y = -50; y <= 50; y +=stepSize) {
                gl.glVertex3f(x, y, 0 ); 
                gl.glVertex3f(x + stepSize, y, 0); 
            }
            gl.glEnd();
        }
        ShaderPrograms.defaultShader.useProgram(gl); //so nothing is done with the color
        gl.glBegin(GL_QUADS);
        gl.glColor4d(0.43921568627, 0.6862745098, 0.93725490196, 0.2);
         gl.glVertex3f(-50f,-50f, 0f);
         gl.glVertex3f(-50f,50f, 0f);
         gl.glVertex3f(50f,50f, 0f);
         gl.glVertex3f(50f,-50f, 0f);
        gl.glEnd();
    }
    
}
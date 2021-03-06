package robotrace;

import static javax.media.opengl.GL.GL_LINES;
import static javax.media.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 * 
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 * 
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 * 
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures. 
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 * 
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd(); 
 * 
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {
    
    /** Array of the four robots. */
    private final Robot[] robots;
    
    /** Instance of the camera. */
    private final Camera camera;
    
    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;
    
    /** Instance of the terrain. */
    private final Terrain terrain;
    
    private final Tree[] trees;
        
    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        
        // Create a new array of four robots
        robots = new Robot[4];
        
        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD, 1.5f
                
        );
        
        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER, 5f
              
        );
        
        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD, 3f
              
        );

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE, 2f
                
        );
        
        trees = new Tree[3];
        
        trees[0] = new Tree(3f, 5f
                
        );
        trees[1] = new Tree( 2f, 1f
        
        );
        
        trees[2] = new Tree(1f, 3f);
        // Initialize the camera
        camera = new Camera();
        
        // Initialize the race tracks
        raceTracks = new RaceTrack[2];
        
        // Track 1
        raceTracks[0] = new ParametricTrack();
        
        // Track 2
        raceTracks[1] = new BezierTrack(
                
                new Vector[]{
                    new Vector(1.85, -7.5, 1),
                    new Vector(18, -1.3, 2),
                    new Vector(-0.7, 17.1, 3),
                    new Vector(-11.65, 8.8, 3),
                    new Vector(-11.65, 8.8, 3),
                    new Vector(-23, 0, 2),
                    new Vector(-24.47,-17.6, 1),
                    new Vector(1.85, -7.5, 1)
                }
       
        );
        
        // Initialize the terrain
        terrain = new Terrain();
    }
    
    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {
		
        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                
        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);
		
        // Enable face culling for improved performance
        // gl.glCullFace(GL_BACK);
        // gl.glEnable(GL_CULL_FACE);
        
	    // Normalize normals.
        gl.glEnable(GL_NORMALIZE);
        
	// Try to load four textures, add more if you like in the Textures class         
        Textures.loadTextures();
        reportError("reading textures");
        
        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");
        
        //enable texturing
        gl.glEnable(GL_TEXTURE_2D);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        gl.glBindTexture(GL_TEXTURE_2D, 0);
        
        // Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0f,0f,0f,1f}, 0);
        //gl.glEnable(GL_LIGHT0);
    }
   
    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);
        
        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);
        
        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
               
        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        int lastRobot = 0;
        for(int i=1; i<robots.length; i++) {
            if (robots[i].diff < robots[lastRobot].diff){
                lastRobot = i;
            }
        }
        camera.update(gs, robots[lastRobot]);
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                      camera.center.x(), camera.center.y(), camera.center.z(),
                      camera.up.x(),     camera.up.y(),     camera.up.z());
    }
    
    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {
        
        gl.glUseProgram(defaultShader.getProgramID());
        reportError("program");
        
        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);
        
        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);
        
        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        

    // Draw hierarchy example.
        //drawHierarchy();
        
        // Draw the axis frame.
        if (gs.showAxes) {
            drawAxisFrame();
        }
        
        // Draw the (first) robot.
        gl.glUseProgram(robotShader.getProgramID()); 
        
        for (int i = 0; i < robots.length; i++) {
            float time = (gs.tAnim + robots[i].diff)/30f;
            robots[i].position = raceTracks[gs.trackNr].getLanePoint(i, time);
            robots[i].direction = raceTracks[gs.trackNr].getLaneTangent(i, time).cross(Vector.Z);
            robots[i].draw(gl, glu, glut, time, gs);
        }
        //robots[0].draw(gl, glu, glut, 0, gs);
        
        
        // Draw the race track.
        gl.glUseProgram(trackShader.getProgramID());
        raceTracks[gs.trackNr].draw(gl, glu, glut, Textures.track, Textures.brick); //put in texture here
        
        // Draw the terrain.
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut, gs.tAnim);
        reportError("terrain:");
        
        gl.glUseProgram(defaultShader.getProgramID());
        trees[0].draw(gl, glu, glut, new Vector(25,30,5));
        trees[1].draw(gl, glu, glut, new Vector(-25,-30,2));
        trees[2].draw(gl, glu, glut, new Vector(30,30,3));
    }
    
    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {
        //draw yellow sphere in origin
            gl.glPushMatrix();
             gl.glColor3f(255, 255, 0);
             glut.glutSolidSphere(0.1 , 50, 50);
            gl.glPopMatrix();
        
        //draw x-axis
            gl.glPushMatrix();
             gl.glRotated(90, 0, 1, 0);
             gl.glColor3f(255, 0, 0);
             drawArrow();
            gl.glPopMatrix();
     
        //draw y-axis
            gl.glPushMatrix(); 
             gl.glRotated(-90, 1, 0, 0);
             gl.glColor3f(0, 255, 0);
             drawArrow();
            gl.glPopMatrix();
     
        //draw z-axis
            gl.glPushMatrix(); 
             gl.glColor3f(0, 0, 255);
             drawArrow();
            gl.glPopMatrix();
    }
    
    /**
     * Draws a single arrow
     */
    public void drawArrow() {  
        //draws on the z-axis
        glut.glutSolidCylinder(0.05, 0.8, 50, 50);
        gl.glTranslated(0, 0, 0.8);
        glut.glutSolidCone(0.1, 0.2, 50, 50);
    }
 
    /**
     * Drawing hierarchy example.
     * 
     * This method draws an "arm" which can be animated using the sliders in the
     * RobotRace interface. The A and B sliders rotate the different joints of
     * the arm, while the C, D and E sliders set the R, G and B components of
     * the color of the arm respectively. 
     * 
     * The way that the "arm" is drawn (by calling {@link #drawSecond()}, which 
     * in turn calls {@link #drawThird()} imposes the following hierarchy:
     * 
     * {@link #drawHierarchy()} -> {@link #drawSecond()} -> {@link #drawThird()}
     */
    private void drawHierarchy() {
        gl.glColor3d(gs.sliderC, gs.sliderD, gs.sliderE);
        gl.glPushMatrix(); 
            gl.glScaled(2, 1, 1);
            glut.glutSolidCube(1);
            gl.glScaled(0.5, 1, 1);
            gl.glTranslated(1, 0, 0);
            gl.glRotated(gs.sliderA * -90.0, 0, 1, 0);
            drawSecond();
        gl.glPopMatrix();
    }
    
    private void drawSecond() {
        gl.glTranslated(1, 0, 0);
        gl.glScaled(2, 1, 1);
        glut.glutSolidCube(1);
        gl.glScaled(0.5, 1, 1);
        gl.glTranslated(1, 0, 0);
        gl.glRotated(gs.sliderB * -90.0, 0, 1, 0);
        drawThird();
    }
    
    private void drawThird() {
        gl.glTranslated(1, 0, 0);
        gl.glScaled(2, 1, 1);
        glut.glutSolidCube(1);
    }
    
    
    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}

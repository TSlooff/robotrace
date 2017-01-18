package robotrace;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        center = gs.cnt;
        double diagonal = gs.vDist * cos(gs.phi);
        double z = sin(gs.phi) * gs.vDist;
        double x = diagonal * cos(gs.theta);
        double y = diagonal * sin(gs.theta);
        
        eye = new Vector(x, y, z);
        
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        gs.vDist = 7;
        Vector dir = focus.direction.normalized().cross(new Vector(0,0,-1));
        this.eye = focus.position.add(new Vector(0,0,1.5));
        
        this.center = this.eye.add(dir.scale(10));   
    }
}


package robotrace;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import javax.media.opengl.glu.GLU;
/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    private Vector[] controlPoints;
    private double NSegment = 200d;
    private List<Vector> normals;
    private List<Vector> offset_points;
    private List<Vector> points;
    private float LANE_WIDTH = 1.22f;
    private final static int LANE_HEIGHT = 1;
    
    public void draw(GL2 gl, GLU glu, GLUT glut, Texture track, Texture brick) {
        Vector finishPoint = null;
        Vector finishOff = null;
        Vector finishPointNext = null;
        Vector finishOffNext = null;

        //Initialize arraylist of points
        points = new ArrayList();
        //Initialize arraylist of offset_points;
        offset_points = new ArrayList<>();

        //Initialize arraylist of normals
        normals = new ArrayList<>();

        Vector normal;
        
        //Loop through number of lanes.
            for (int i = 0; i < 4; i+=2) {
                //Loop through number of segements.
                for (int j = 0; j < NSegment + 1; j++) {

                    //If not first time creating list
                    if (i > 0 && j == 0) {
                        //Clear list of points
                        points.clear();

                        //Set list of points equal to list of offset points off previous lane
                        //We used stream because "=" gave the wrong results.
                        offset_points.stream().forEach((v) -> {
                            points.add(v);
                        });
                        //Clear list of offset points
                        offset_points.clear();
                    }

                    //Calculate t
                    double t = j / NSegment;

                    //Calulate point on lane with t
                    Vector point = getPoint(t);

                    //If point list is smaller then needed points add point.
                    //This means new points will only be added on first list generation.
                    if (points.size() <= j) {
                        points.add(point);
                    }

                    //Get Tangent
                    Vector tangent = getTangent(t);

                    //Calculate normal by cross with negative z-axis
                    normal = tangent.cross(new Vector(0, 0, 1));
                    normals.add(normal);

                    //Add normal to list of normals
                    //Add unit normal vector to point and scale to lane width
                    Vector off = point.add(normal.normalized().scale((LANE_WIDTH * (i + 2))));
                    //Add offset point
                    offset_points.add(off);

                    //If 1st segement and first lane
                    if (j == 0 && i == 0) {
                        //get inner and outer finish line point
                        finishPoint = point;
                        finishOff = point.add(normal.normalized().scale((LANE_WIDTH * (4))));
                    }
                    //If 5th segment and first lane
                    if (j == 5 && i == 0) {
                        //get inner and outer finish line point
                        finishPointNext = point;
                        finishOffNext = point.add(normal.normalized().scale((LANE_WIDTH * (4))));
                    }
                }
                //set lane colors
                setLaneColors(i, gl);

                //Draw the test track
                drawTrack(points, offset_points, NSegment, gl, track, brick);

            }
    }
    
    private void drawTrack(List<Vector> points, List<Vector> offset_points, Double NSegment, GL2 gl, Texture track, Texture brick) {
        // Start using track texture.
        track.enable(gl);
        track.bind(gl);
        //Draw the sides of the lane
        drawTrackLane(points, offset_points, NSegment, gl);
        track.disable(gl);
        //Draw the flat top race track
        brick.enable(gl);
        brick.bind(gl);
        drawTrackSides(points, offset_points, normals, NSegment, gl);
        brick.disable(gl);
    }
    
    private void drawTrackLane(List<Vector> points, List<Vector> offset_points, Double NSegment, GL2 gl) {
        // 2D array to store the line translations
        int[][] coordinates2d = new int[][]{
            {0, 0},
            {1, 0},
            {1, 1},
            {0, 1}};

        // Draw the top of the track.
        gl.glBegin(GL_QUADS);
        for (int i = 0; i < NSegment; i++) {
            double[][] coordinates3d = new double[][]{
                {points.get(i).x, points.get(i).y, points.get(i).z},
                {offset_points.get(i).x, offset_points.get(i).y, offset_points.get(i).z},
                {offset_points.get(i + 1).x, offset_points.get(i + 1).y, offset_points.get(i + 1).z},
                {points.get(i + 1).x, points.get(i + 1).y, points.get(i + 1).z}};

            gl.glNormal3d(0, 0, 1); // upwards pointing normal equal to z axis
            for (int j = 0; j < coordinates2d.length; j++) {
                gl.glTexCoord2f(coordinates2d[j][0], coordinates2d[j][1]);
                gl.glVertex3d(coordinates3d[j][0], coordinates3d[j][1], coordinates3d[j][2]);
            }
        }
        gl.glEnd();

        // Draw the bottom of the track.
        gl.glBegin(GL_QUADS);
        for (int i = 0; i < NSegment; i++) {
            double[][] coordinates3d = new double[][]{
                {points.get(i).x, points.get(i).y, points.get(i).z - LANE_HEIGHT},
                {offset_points.get(i).x, offset_points.get(i).y, offset_points.get(i).z - LANE_HEIGHT},
                {offset_points.get(i + 1).x, offset_points.get(i + 1).y, offset_points.get(i + 1).z - LANE_HEIGHT},
                {points.get(i + 1).x, points.get(i + 1).y, points.get(i + 1).z - LANE_HEIGHT}};

            gl.glNormal3d(0, 0, 1); // upwards pointing normal equal to z axis
            for (int j = 0; j < coordinates2d.length; j++) {
                gl.glTexCoord2f(coordinates2d[j][0], coordinates2d[j][1]);
                gl.glVertex3d(coordinates3d[j][0], coordinates3d[j][1], coordinates3d[j][2]);
            }
        }
        gl.glEnd();
    }    
    
    private void drawTrackSides(List<Vector> points, List<Vector> offset_points, List<Vector> normals, Double NSegment, GL2 gl) {
        // 2D array to store the line translations
        int[][] coordinates2d = new int[][]{
            {0, 0},
            {1, 0},
            {1, 1},
            {0, 1},
            {0, 0},
            {1, 0},
            {1, 1},
            {0, 1}};

        //Draw sides of lane with squads.
        gl.glBegin(GL_QUADS);
        //Loop trhough number of segements
        for (int i = 0; i < NSegment; i++) {
             // 3D array to store the line translations
             //Calculate points by point list and offset_list
             //We first draw the left side with lane height = LANE_HEIGHT;
             //NOTE: points.get(.).z - LANE_HEIGHT can be changed to min height to get track with height z to MIN_HEIGHT
             //Then we draw the right side with lane height = LANE_HEIGHT
            double[][] coordinates3d = new double[][]{
                {points.get(i).x, points.get(i).y, points.get(i).z},
                {points.get(i + 1).x, points.get(i + 1).y, points.get(i + 1).z},
                {points.get(i + 1).x, points.get(i + 1).y, points.get(i + 1).z - LANE_HEIGHT},
                {points.get(i).x, points.get(i).y, points.get(i).z - LANE_HEIGHT},
                {offset_points.get(i).x, offset_points.get(i).y, offset_points.get(i).z},
                {offset_points.get(i + 1).x, offset_points.get(i + 1).y, offset_points.get(i + 1).z},
                {offset_points.get(i + 1).x, offset_points.get(i + 1).y, offset_points.get(i + 1).z - LANE_HEIGHT},
                {offset_points.get(i).x, offset_points.get(i).y, offset_points.get(i).z - LANE_HEIGHT}};

            //Start looping through multidimensinal array to draw track
            gl.glNormal3d(normals.get(i).x, normals.get(i).y, normals.get(i).z); // upwards pointing normal equal to normal
            for (int j = 0; j < coordinates2d.length; j++) {
                //Draw 2D coordinates
                gl.glTexCoord2f(coordinates2d[j][0], coordinates2d[j][1]);
                //Draw 3D coordinates
                gl.glVertex3d(coordinates3d[j][0], coordinates3d[j][1], coordinates3d[j][2]);
            }

        }
        gl.glEnd();

    }
    
    @Override
    protected Vector getPoint(double t) {
        double x = 10*cos(2*PI*t);
        double y = 14*sin(2*PI*t);
        Vector p = new Vector(x, y, 1);
        return p;

    }

    @Override
    protected Vector getTangent(double t) {
        double x = -20*PI*sin(2*PI*t);
        double y = 28*PI*cos(2*PI*t);
        Vector T = new Vector(x, y, 0);
        return T;
    }
    
}

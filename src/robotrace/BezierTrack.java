
package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import java.util.ArrayList;
import java.util.List;
import static javax.media.opengl.GL.GL_FRONT;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import javax.media.opengl.glu.GLU;
import static javax.media.opengl.GL.GL_LINES;
import static javax.media.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    
    private Vector[] controlPoints;
    private double NSegment = 200d;
    private List<Vector> normals;
    private List<Vector> offset_points;
    private List<Vector> points;
    private float LANE_WIDTH = 1.22f;
    private final static int LANE_HEIGHT = 1;
    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
        


    }
    @Override
    public void draw(GL2 gl, GLU glu, GLUT glut, Texture track, Texture brick) {
        Vector finishPoint = null;
        Vector finishOff = null;
        Vector finishPointNext = null;
        Vector finishOffNext = null;
        
        points = new ArrayList();
        
        offset_points = new ArrayList<>();
        
        normals = new ArrayList<>();
        
        Vector normal; 
        
        //Loop through number of controlpoints with step of 3 (bezier curve)
            for (int k = 0; k < controlPoints.length - 1; k = k + 3) {
                //Loop through number of lanes
                for (int i = 0; i < 4; i++) {
                    //Loop through number of segments
                    for (int j = 0; j < NSegment + 1; j++) {

                        //Calculate t
                        double t = j / NSegment;

                        //If not first time creating list
                        if (i > 0 && j == 0) {
                            //Clear list of points
                            points.clear();

                            //Set list of points equal to list of offset points off previous lane
                            offset_points.stream().forEach((v) -> {
                                points.add(v);
                            });
                            //Clear list of offset points
                            offset_points.clear();
                        }

                        //Add point to list of points
                        Vector point = getCubicBezierPnt(t, controlPoints[k], controlPoints[k + 1], controlPoints[k + 2], controlPoints[k + 3]);

                        //If point list is smaller then needed points add point
                        if (points.size() <= j) {
                            points.add(point);
                        }

                        //Get Tangent
                        Vector tangent = getCubicBezierTng(t, controlPoints[k], controlPoints[k + 1], controlPoints[k + 2], controlPoints[k + 3]);

                        //Calculate normal by cross with z-axis
                        normal = tangent.cross(new Vector(0, 0, -1));
                        normals.add(normal);

                        //Add unit normal vector to point and scale to lane width
                        Vector off = point.add(normal.normalized().scale((LANE_WIDTH * (i + 1))));

                        //Add offset point
                        offset_points.add(off);

                        //if 1st controlpoint and 1st land and 1st segment.
                        if (j == 0 && i == 0 && k == 0) {
                            //Calculate finish line points
                            finishPoint = point;
                            finishOff = finishPoint.add(normal.normalized().scale(LANE_WIDTH * 4));
                        }
                        if (j == (controlPoints.length / 3) * 5 && i == 0 & k == 0) {
                            //Calculate finish line point
                            finishPointNext = point;
                            finishOffNext = finishPointNext.add(normal.normalized().scale(LANE_WIDTH * 4));
                        }
                    }

                    //Set the lane colors
                    setLaneColors(i, gl);
                    //Draw track
                    drawTrack(points, offset_points, NSegment, gl, track, brick);
                }
                //Clear list of points
                points.clear();
                //Clear list of offset_points
                offset_points.clear();
            }
    }
    
    private void drawTrack(List<Vector> points, List<Vector> offset_points, Double N, GL2 gl, Texture track, Texture brick) {
        // Start using track texture.
        track.enable(gl);
        track.bind(gl);
        //Draw the sides of the lane
        drawTrackLane(points, offset_points, N, gl);
        track.disable(gl);
        //Draw the flat top race track
        brick.enable(gl);
        brick.bind(gl);
        drawTrackSides(points, offset_points, normals, N, gl);
        brick.disable(gl);
    }
        
    private void drawTrackLane(List<Vector> points, List<Vector> offset_points, Double N, GL2 gl) {
        // 2D array to store the line translations
        int[][] coordinates2d = new int[][]{
            {0, 0},
            {1, 0},
            {1, 1},
            {0, 1}};

        // Draw the top of the track.
        gl.glBegin(GL_QUADS);
        for (int i = 0; i < N; i++) {
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
        for (int i = 0; i < N; i++) {
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
    
    private void drawTrackSides(List<Vector> points, List<Vector> offset_points, List<Vector> normals, Double N, GL2 gl) {
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
        for (int i = 0; i < N; i++) {
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

        return Vector.O;

    }

    @Override
    protected Vector getTangent(double t) {

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

}

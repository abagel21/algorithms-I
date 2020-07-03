import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        ArrayList<Point2D> a = new ArrayList<Point2D>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                a.add(point);
            }
        }
        return a;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        Point2D candidate = null;
        for (Point2D point : points) {
            if (candidate == null) candidate = point;
            if (point.distanceSquaredTo(p) < candidate.distanceSquaredTo(p)) {
                candidate = point;
            }
        }
        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

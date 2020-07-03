import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints {
    private LineSegment[] lines; // number of lines segments memory
    private int index;
    private int count;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Array of points must not be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("No point can be null");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException("No point can be null");
                }
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("All points must be distinct");
                }
            }
        }
        index = 0;
        count = 0;
        lines = new LineSegment[2];
        Point[] unslopedPoints = points.clone();
        Arrays.sort(unslopedPoints);
        for (int i = 0; i < unslopedPoints.length; i++) {
            // n squared logn time
            Point[] allPoints = unslopedPoints.clone();
            Arrays.sort(allPoints, unslopedPoints[i].slopeOrder());
            for (int j = 0; j < allPoints.length; j++) {
                int collinearPointIncrement = 1;
                double collinearSlope = unslopedPoints[i].slopeTo(allPoints[j]);
                // checking if j, j + increment, and j + increment + 1 have the same slope
                while ((j + collinearPointIncrement)< allPoints.length && unslopedPoints[i].slopeTo(allPoints[j + collinearPointIncrement])== collinearSlope) {
                    collinearPointIncrement++;
                }
                // if increment is 2 or larger, it has successfully confirmed 3 collinear points. Including the point sorted by, 4
                if (collinearPointIncrement >= 3) {
                    if (index == lines.length - 1) {
                        resize();
                    }
                    Point max= allPoints[j + collinearPointIncrement - 1];
                    // trying to include point that is sorted by because it will be sorted to the very first spot (slope negative infinity)
                    Point min = unslopedPoints[i];
                    for (int n = j; n < j + collinearPointIncrement; n++) {
                        if (allPoints[n].compareTo(max) > 0) {
                            max = allPoints[n];
                        }
                        if (allPoints[n].compareTo(min) < 0) {
                            min = allPoints[n];
                        }
                    }
                    // trying to only add segments once by only adding a segment when the outer loop is on the minimum point of the segment
                    if(unslopedPoints[i].compareTo(min) == 0) {
                        this.push(new LineSegment(min, max));
                    }
                }
                j = j + collinearPointIncrement - 1;
            }
        }

    }
    // the number of line segments
    public           int numberOfSegments() {
        return count;
    }
    // the line segments
    public LineSegment[] segments()        {
        LineSegment[] a = new LineSegment[index];
        for (int i = 0; i < a.length; i++) {
            a[i] = lines[i];
        }
        return a;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    private void resize() {
        LineSegment[] oldStack = lines;
            lines = new LineSegment[oldStack.length * 2];
            for (int i = 0; i < oldStack.length; i++) {
                lines[i] = oldStack[i];
            }
    }
    private void push(LineSegment line) {
        lines[index] = line;
        index++;
        count++;
    }
}
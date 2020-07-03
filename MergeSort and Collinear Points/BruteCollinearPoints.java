import java.util.Arrays;
public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int index;
    private int count;
    //  finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // checking if any point is null or any point is the same
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


        Point[] allPoints = points.clone();
        Arrays.sort(allPoints);
        this.count = 0;
        this.lines = new LineSegment[2];
        this.index = 0;
        for (int i = 0; i < allPoints.length; i++) {
            for (int j = i + 1; j < allPoints.length; j++) {
                for (int k = j + 1; k < allPoints.length; k++) {
                    for (int m = k + 1; m < allPoints.length; m++) {
                        if (allPoints[i].slopeTo(allPoints[j]) == allPoints[j].slopeTo(allPoints[k]) && allPoints[j].slopeTo(allPoints[k]) == allPoints[k].slopeTo(allPoints[m])) {
                            if (index == lines.length) {
                                resize();
                            }
                            this.push(new LineSegment(allPoints[i], allPoints[m]));
                        }
                    }
                }
            }
        }

    }
    // the number of line segments
    public           int numberOfSegments() {
        return count;
    }
    // the line segments
    public LineSegment[] segments()  {
        LineSegment[] a = new LineSegment[index];
        for (int i = 0; i < a.length; i++) {
            a[i] = lines[i];
        }
        return a;
    }
    private void push(LineSegment line) {
        lines[index] = line;
        index++;
        count++;
    }
//    private LineSegment pop() {
//        // implementation unecessary, stack will not pop any line segments
//    }
    private void resize() {
        LineSegment[] oldStack = lines;
            lines = new LineSegment[oldStack.length * 2];
            for (int i = 0; i < oldStack.length; i++) {
                lines[i] = oldStack[i];
            }

    }
}





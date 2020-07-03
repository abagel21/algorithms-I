import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node root;

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    private enum Dimension {
        X,
        Y
    }

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private int size;
        private Dimension dimension;
        private RectHV rect;

        public Node(Point2D p, Dimension dimension, RectHV rect) {
            this.point = p;
            this.left = left;
            this.right = right;
            this.dimension = dimension;
            this.size = 1;
            this.rect = rect;
        }

        public int compareTo(Point2D point) {
            if (dimension == Dimension.X) {
                if (this.point.x() > point.x()) return 1;
                else if (this.point.x() < point.x()) return -1;
                else return 0;
            } else {
                if (this.point.y() > point.y()) return 1;
                else if (this.point.y() < point.y()) return -1;
                else return 0;
            }
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (root == null) return 0;
        return root.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        root = insert(p, root, true, null, false);
    }

    private Node insert(Point2D p, Node x, boolean xdimension, Node parent, boolean left) {
        if (x == null) {
            if (parent == null) return new Node(p, xdimension ? Dimension.X : Dimension.Y, new RectHV(0, 0, 1, 1));
            return new Node(p, xdimension ? Dimension.X : Dimension.Y, newRectangle(parent, left));
        }

        if (!p.equals(x.point)) {
            int cmp = x.compareTo(p);
            if (cmp < 0) x.right = insert(p, x.right, !xdimension, x, false);
            else x.left = insert(p, x.left, !xdimension, x, true);

            x.size = 1 + size(x.right) + size(x.left);
        }
        return x;
    }

    private RectHV newRectangle(Node n, boolean left) {
        if (n.dimension == Dimension.X) {
            if (left) {
                return new RectHV(n.rect.xmin(), n.rect.ymin(), n.point.x(), n.rect.ymax());
            } else {
                return new RectHV(n.point.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
            }
        } else {
            if (left) {
                return new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.point.y());
            } else {
                return new RectHV(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.rect.ymax());
            }

        }
    }

    private int size(Node n) {
        if (n == null) return 0;
        return n.size;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node n) {
        if (n == null) return false;
        else if (p.equals(n.point)) return true;
        int cmp = n.compareTo(p);
        if (cmp < 0) return contains(p, n.right);
        else return contains(p, n.left);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node n) {
        if (n != null) {
            n.point.draw();
            //draw line
            draw(n.left);
            draw(n.right);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        ArrayList<Point2D> a = new ArrayList<Point2D>();
        //iterate through tree
        //if node.rect intersects rect
        //add to lsit
        range(rect, a, root);
        return a;
    }

    private void range(RectHV rect, ArrayList<Point2D> a, Node n) {
        if (n != null) {
            if (n.rect.intersects(rect)) {
                if (rect.contains(n.point)) {
                    a.add(n.point);
                }
                range(rect, a, n.left);
                range(rect, a, n.right);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
        Point2D candidate = null;
        return nearest(p, root, candidate);
    }

    private Point2D nearest(Point2D p, Node n, Point2D candidate) {
        if (n == null) return candidate;
        if (candidate == null) candidate = n.point;
        if (candidate.distanceSquaredTo(p) > n.point.distanceSquaredTo(p)) {
            candidate = n.point;
        }
        Point2D otherCandidate = null;
        if (candidate.distanceSquaredTo(p) > n.rect.distanceSquaredTo(p)) {
            int cmp = n.compareTo(p);
            if (cmp < 0) {
                otherCandidate = nearest(p, n.right, candidate);
                candidate = nearest(p, n.left, otherCandidate);
            } else {
                otherCandidate = nearest(p, n.left, candidate);
                candidate = nearest(p, n.right, otherCandidate);
            }
        }
        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

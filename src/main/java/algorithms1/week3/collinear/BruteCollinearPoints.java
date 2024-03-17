package algorithms1.week3.collinear;

import algorithms1.week3.LineSegment;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> collinearSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        // check that point is not null and there are no duplicate points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        collinearSegments = new ArrayList<>();
        findCollinearSegments(points);
    }

    // the number of line segments
    public int numberOfSegments() {
        return collinearSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return collinearSegments.toArray(new LineSegment[numberOfSegments()]);
    }

    private void findCollinearSegments(Point[] points) {
        Point[] pointsClone = points.clone();
        Arrays.sort(pointsClone);
        int N = pointsClone.length;
        for (int i = 0; i < N; i++) {
            Point p = pointsClone[i];
            for (int j = i + 1; j < N; j++) {
                Point q = pointsClone[j];
                double slopePQ = p.slopeTo(q);
                for (int k = j + 1; k < N; k++) {
                    Point r = pointsClone[k];
                    double slopePR = p.slopeTo(r);
                    if (slopePQ != slopePR) continue;
                    for (int l = k + 1; l < N; l++) {
                        Point s = pointsClone[l];
                        double slopePS = p.slopeTo(s);
                        if (slopePQ == slopePS) {
                            LineSegment segment = new LineSegment(p, s);
                            if (!collinearSegments.contains(segment))
                                collinearSegments.add(segment);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
//        In in = new In(args[0]);
        In in = new In("src/test/java/algorithms1/week3/collinear/input8.txt");
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

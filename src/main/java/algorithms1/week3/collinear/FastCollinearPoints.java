package algorithms1.week3.collinear;

import algorithms1.week3.LineSegment;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> collinearSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        // check that point is not null and there are no duplicate points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < i; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        collinearSegments = new ArrayList<>();
//        collinearSegments = new LinkedList<>();
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


//    private void findCollinearSegments(Point[] points) {
//        LinkedList<Point> segment = new LinkedList<>();
//        List<Point> skipList = new LinkedList<>();
//        Arrays.sort(points);
//        for (Point p : points) {
//            Point[] pointsClone = points.clone();
//
//            if (shouldSkip(skipList, p)) continue;
//
//            Arrays.sort(pointsClone, p.slopeOrder());
//
//            // pointsClone[0] is p
//            for (int j = 1; j < pointsClone.length - 2; j++) {
//                Point q = pointsClone[j];
//                Point r = pointsClone[j + 1];
//                Point s = pointsClone[j + 2];
//
//                double slopePQ = p.slopeTo(q);
//                double slopePR = p.slopeTo(r);
//                double slopePS = p.slopeTo(s);
//
//                if (slopePQ == slopePR && slopePQ == slopePS) {
//                    j += 2;
//                    segment.addLast(p);
//                    segment.addLast(q);
//                    segment.addLast(r);
//                    segment.addLast(s);
//
//                    while (j + 1 < pointsClone.length && slopePQ == p.slopeTo(pointsClone[j + 1])) {
//                        segment.addLast(pointsClone[++j]);
//                    }
//                    j++;
//                    collinearSegments.add(new LineSegment(segment.pollFirst(), segment.pollLast()));
//
//                    // add all points that are belong to the segment and skip them for the next segment
//                    skipList.addAll(segment);
//                    segment.clear();
//                }
//            }
//
//        }
//    }
//    private void findCollinearSegments(Point[] points) {
//        for (Point p: points) {
//            Point[] pointsClone = points.clone();
//            Arrays.sort(pointsClone, p.slopeOrder());
//            // pointClone[0] is p since p.slopeTo(p) is negative infinity
//
//            for (int j = 1; j < pointsClone.length - 2; j++) {
//                Point q = pointsClone[j];
//                Point r = pointsClone[j + 1];
//                Point s = pointsClone[j + 2];
//
//                double slopePQ = p.slopeTo(q);
//                double slopePR = p.slopeTo(r);
//                double slopePS = p.slopeTo(s);
//
//                if (slopePQ == slopePR && slopePR == slopePS) {
//                    j += 2;
//                    while (j + 1 < pointsClone.length && slopePQ == p.slopeTo(pointsClone[j + 1])) {
//                        j++;
//                    }
//                    LineSegment segment = new LineSegment(p, pointsClone[j]);
//                    if (!collinearSegments.contains(segment))
//                        collinearSegments.add(segment);
//                }
//            }
//        }
// }

    private void findCollinearSegments(Point[] points) {

        Point[] pointsNaturalOrder = Arrays.copyOf(points, points.length);
        Point[] pointsSlopeOrder = Arrays.copyOf(points, points.length);

        LinkedList<Point> collinearPoints = new LinkedList<>();

        for (Point point : pointsNaturalOrder) {
            Arrays.sort(pointsSlopeOrder, point.slopeOrder());
            double previousSlope = 0.0;

            for (int i = 0; i < pointsSlopeOrder.length; i++) {
                double currentSlope = point.slopeTo(pointsSlopeOrder[i]);
                if (i == 0 || currentSlope != previousSlope) {
                    if (collinearPoints.size() >= 3) {
                        if (collinearPoints.getFirst().compareTo(point) > 0)
                            collinearSegments.add(new LineSegment(point,
                                    collinearPoints.getLast()));
                    }

                    collinearPoints.clear();
                }
                collinearPoints.add(pointsSlopeOrder[i]);
                previousSlope = currentSlope;

                if (i == pointsSlopeOrder.length - 1 && collinearPoints.size() >= 3
                        && collinearPoints.getFirst().compareTo(point) > 0) {
                    collinearSegments.add(new LineSegment(point, collinearPoints.getLast()));
                    collinearPoints.clear();
                }
            }
        }
    }

//    private void findCollinearSegments(Point[] points) {
//        int numOfPoints = points.length;
//        List<Point> skipList = new LinkedList<>();
//        Point[] workingSetPoints = points.clone();
//        for (int pid = 0; pid < numOfPoints; pid++) {
//            Point originPoint = points[pid];
//            if (shouldSkip(skipList, originPoint)) {
//                continue;
//            }
//
//            Arrays.sort(workingSetPoints, 0, numOfPoints, originPoint.slopeOrder());
//            int collinearSetSize = 1;
//            boolean startedEqualSegment = false;
//            int segmentIndex = 1;
//            for (int k = segmentIndex; k < numOfPoints - 1; k++) {
//                if (originPoint.slopeTo(workingSetPoints[k]) == originPoint.slopeTo(workingSetPoints[k + 1])) {
//                    collinearSetSize++;
//                    if (!startedEqualSegment) {
//                        startedEqualSegment = true;
//                        segmentIndex = k;
//                    }
//                } else if (startedEqualSegment) {
//                    break;
//                }
//            }
//            if (collinearSetSize >= 3) {
//                Point[] collinearSet = new Point[collinearSetSize + 1];
//                collinearSet[0] = originPoint;
//                for (int i = 0; i < collinearSetSize; i++) {
//                    collinearSet[i + 1] = workingSetPoints[i + segmentIndex];
//                }
//
//                Arrays.sort(collinearSet, 0, collinearSetSize + 1);
//                collinearSegments.add(new LineSegment(collinearSet[0], collinearSet[collinearSetSize]));
//                for (int i = 0; i <= collinearSetSize; i++) {
//                    skipList.add(collinearSet[i]);
//                }
//            }
//        }
//    }

    private boolean shouldSkip(List<Point> skipList, Point p) {
        return skipList
                .stream()
                .anyMatch(point -> point.compareTo(p) == 0);
    }

    public static void main(String[] args) {

        // read the n points from a file
//        In in = new In(args[0]);
        In in = new In("src/test/java/algorithms1/week3/collinear/rs1423.txt");
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


}

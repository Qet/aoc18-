import InputReader.InputReader;

import java.awt.*;
import java.util.ArrayList;


class PointStats{
    public PointStats(Point point){
        this.point = point;
        infinite = false;
        closeSquareCount = 0;
    }
    Point point;
    boolean infinite;
    int closeSquareCount;
}


public class Day6 {

    static final int GRID_SIZE = 500;

    public static void main(String[] args) {
        InputReader ir = new InputReader(6);
        ArrayList<Point> points = parseAllData(ir);
        ArrayList<PointStats> pointStats = createPointStatsArray(points);
        fillPointStats(pointStats);
        PointStats bestRegion = findLargestNoninfiniteRegion(pointStats);

        System.out.println("[Part 1] Largest non-infinite region is " + bestRegion.closeSquareCount + " squares");

        System.out.println("[Part 2] Largest safe region: " + calcSafeRegionSize(pointStats));

    }

    public static PointStats findLargestNoninfiniteRegion(ArrayList<PointStats> pointStats){
        PointStats largestRegion = new PointStats(new Point(0,0));
        for(PointStats curStats : pointStats){
            if (!curStats.infinite && curStats.closeSquareCount > largestRegion.closeSquareCount)
                largestRegion = curStats;
        }
        return largestRegion;
    }

    public static int manhattanDistance(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static int findClosestPoint(Point gridSquare, ArrayList<PointStats> pointStats){
        //For a grid square, find the index of the closest point, and return it.

        int smallestDistance = Integer.MAX_VALUE;
        int smallestIndex = 0;
        for (int i = 0; i < pointStats.size(); i++) {
            PointStats ps = pointStats.get(i);
            if (manhattanDistance(ps.point, gridSquare) < smallestDistance){
                smallestDistance = manhattanDistance(ps.point, gridSquare);
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    public static int calcDistFromAllPoints(Point currentPoint, ArrayList<PointStats> pointStats){
        int sum = 0;
        for(PointStats ps : pointStats){
            sum += manhattanDistance(currentPoint, ps.point);
        }
        return sum;
    }


    public static int calcSafeRegionSize(ArrayList<PointStats> pointStats){

        int numSafeSquares = 0;
        final int MAX_SAFE_DIST = 10000;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // For each square: add up the number of squares for which the
                // sum of its manhattan distances from all points is less than 10000
                if (calcDistFromAllPoints(new Point(i, j), pointStats) < MAX_SAFE_DIST){
                    numSafeSquares++;
                }
            }
        }
        return numSafeSquares;
    }

    public static void fillPointStats(ArrayList<PointStats> pointStats){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // For each grid square, find the closest point.
                int idx = findClosestPoint(new Point(i, j), pointStats);

                // Increment the close square count for that point.
                pointStats.get(idx).closeSquareCount++;

                // If the grid square is an edge, then that point is an infinite region.
                if (i == 0 || j == 0 || i == (GRID_SIZE - 1) || j == (GRID_SIZE - 1)){
                    pointStats.get(idx).infinite = true;
                }

            }
        }
    }

    public static ArrayList<PointStats> createPointStatsArray(ArrayList<Point> points){
        ArrayList<PointStats> ret = new ArrayList<>();
        for(Point p : points){
            ret.add(new PointStats(p));
        }
        return ret;
    }

    public static ArrayList<Point> parseAllData(InputReader ir){
        ArrayList<Point> points = new ArrayList<>();
        for(String line : ir.getLines()){
            points.add(parseLine(line));
        }
        return points;
    }

    public static Point parseLine(String line){
        String[] spl = line.split(", ");
        Point ret = new Point(Integer.parseInt(spl[0]),
                Integer.parseInt(spl[1]));
        return ret;
    }
}

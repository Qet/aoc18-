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

        System.out.println("[Part 1] Largest region is " + bestRegion.closeSquareCount + " squares");

    }

    public static PointStats findLargestNoninfiniteRegion(ArrayList<PointStats> pointStats){
        PointStats largestRegion = new PointStats(new Point(0,0));
        for(PointStats curStats : pointStats){
            if (!curStats.infinite && curStats.closeSquareCount > largestRegion.closeSquareCount)
                largestRegion = curStats;
        }
        return largestRegion;
    }

    public static void fillPointStats(ArrayList<PointStats> pointStats){
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {

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

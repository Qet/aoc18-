package Day15;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DistanceComparatorTest {

    private Grid grid;
    private DistanceComparator distanceComparator;

    @Before
    public void setup(){

    }

    public void initialiseGrid(String[] gridmap){
        int rows, cols;
        rows = gridmap.length;
        cols = gridmap[0].length();
        grid = new Grid(rows, cols);

        int rownum = 0, colnum = 0;

        for(String row: gridmap){
            colnum = 0;
            for(Character cell: row.toCharArray()){
                grid.populateGrid(rownum, colnum, cell);
                colnum++;
            }
            rownum++;
        }

        Being being = new Being(new Coords(0,0));
        distanceComparator = new DistanceComparator(being, grid);

    }

    @Test
    public void testWithWall(){
        initialiseGrid(new String[] {
                "...",
                ".#.",
                ".#.",
                ".#."
            });

        assertEquals(8, distanceComparator.compare(new Coords(3,0),
                new Coords(3,2)));

        assertEquals(1, distanceComparator.compare(new Coords(3,0),
                new Coords(2,0)));

        assertEquals(6, distanceComparator.compare(new Coords(1,2),
                new Coords(3,0)));


    }

}
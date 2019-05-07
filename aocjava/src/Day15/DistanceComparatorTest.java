package Day15;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceComparatorTest {


    @Test
    public void testDistComparator(){
        int rows, cols;
        rows = cols = 2;
        Grid grid = new Grid(2, 2);
        Being being = new Being(new Coords(0,0));
        DistanceComparator d = new DistanceComparator(being, grid);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.populateGrid(i, j, '.');
            }
        }

        assertEquals(2, d.compare(new Coords(0,0), new Coords(1,1)));

    }

}
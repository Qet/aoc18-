package Day15;

public class Grid {
/*
Represents the whole grid of terrain squares.
 */

    private TerrainSquare[][] grid;

    public Grid(int rows, int cols){
        grid = new TerrainSquare[rows][cols];
    }

    public void populateGrid(int row, int col, char val){
        grid[row][col].initialise(val);
    }

}

package Day15;

public class Grid {
/*
Represents the whole grid of terrain squares.
 */

    private TerrainSquare[][] grid;

    private int numRows;
    private int numCols;

    public Grid(int rows, int cols){
        grid = new TerrainSquare[rows][cols];
        numCols = cols;
        numRows = rows;
    }

    public void populateGrid(int row, int col, char val){
        grid[row][col].initialise(val);
    }

    private TerrainSquare inGrid(Coords location){
        return grid[location.row][location.col];
    }

    public void putBeing(Coords location, Being being){
        inGrid(location).setOccupant(being);
    }

    public int coordReadingOrder(Coords coord){
        return coord.row * numCols + coord.col;
    }

}

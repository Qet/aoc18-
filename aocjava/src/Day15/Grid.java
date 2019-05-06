package Day15;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Grid {
/*
Represents the whole grid of terrain squares.
 */

    private TerrainSquare[][] grid;

    private int numRows;
    private int numCols;

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public List<Being> getAdjBeings(Coords centre){
        List<Coords> adjCoords = getAdjSquares(centre);
        return adjCoords.stream()
                .map(coord -> inGrid(coord).occupant)
                .filter(being -> being != null)
                .collect(Collectors.toList());
    }

    public List<Coords> getAdjSquares(Coords centre){
        int startRow = Math.max(centre.row - 1, 0);
        int endRow = Math.min(centre.row + 2, numRows);

        int startCol = Math.max(centre.col - 1, 0);
        int endCol = Math.min(centre.col + 2, numCols);

        List<Coords> ret = new ArrayList<>();

        for(int row = startRow; row != endRow; row++){
            Coords newCoords = new Coords(row, centre.col);
            if (!newCoords.equals(centre))
                ret.add(newCoords);
        }

        for(int col = startCol; col != endCol; col++){
            Coords newCoords = new Coords(centre.row, col);
            if (!newCoords.equals(centre))
                ret.add(newCoords);
        }

        return ret;
    }

    public Grid(int rows, int cols){
        grid = new TerrainSquare[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new TerrainSquare();
            }
        }
        numCols = cols;
        numRows = rows;
    }

    public void populateGrid(int row, int col, char val){
        grid[row][col].initialise(val);
    }

    private TerrainSquare inGrid(Coords location){
        return grid[location.row][location.col];
    }

    public void putBeing(Being being, Coords location){
        inGrid(location).occupant = being;
    }

    public int coordReadingOrder(Coords coord){
        return coord.row * numCols + coord.col;
    }

    public void removeBeing(Being toRemove){
        inGrid(toRemove.coords).occupant = null;
    }
    public void moveBeing(Being mover, Coords dest){
        removeBeing(mover);
        putBeing(mover, dest);
    }

}

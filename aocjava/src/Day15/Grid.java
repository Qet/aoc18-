package Day15;

public class Grid {

    private GridSquare[][] grid;

    public Grid(int rows, int cols){
        grid = new GridSquare[rows][cols];
    }

    public void populateGrid(int row, int col, char val){
        grid[row][col].initialise(val);
    }

}

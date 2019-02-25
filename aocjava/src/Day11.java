
class Grid{

    public Grid(int serial, int dimensions) {
        this.serial = serial;
        this.dimensions = dimensions + 1; //one-based indexing system.
        theGrid = new int[this.dimensions][this.dimensions];
        populateGrid();
    }

    private void populateGrid(){
        for(int i = 1; i < dimensions; i++){
            for(int j = 1; j < dimensions; j++) {
                theGrid[i][j] = unitCellPower(i, j);
            }
        }
    }

    private int unitCellPower(int x, int y){
        int rackID = x + 10;
        int powerLevel = rackID * y;
        powerLevel += serial;
        powerLevel *= rackID;
        powerLevel = (powerLevel / 100) % 10;  //Hundreds digit
        powerLevel -= 5;
        return powerLevel;
    }

    public int fuelCellPower(int x, int y){
        final int subGridDimensions = 3;

        if (x < 1 || y < 1 ||
                (x + subGridDimensions) >= dimensions ||
                (y + subGridDimensions) >= dimensions)
            return 0;

        int sum = 0;
        for(int i=x; i < x + subGridDimensions ; i++){
            for(int j=x; j < x + subGridDimensions ; j++) {
                sum += theGrid[i][j];
            }
        }
        return sum;
    }

    private int[][] theGrid;
    private final int serial;
    private final int dimensions;

}

class GridResult{
    public GridResult(int x, int y, int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public int x;
    public int y;
    public int val;
}



public class Day11 {
    public static void main(String[] args) {

        final int dimensions = 300;
        final int serial = 18;
        Grid g = new Grid(serial, dimensions);

        GridResult bestResult = new GridResult(0,0,0);
        for(int i=1; i <= dimensions; i++){
            for(int j=1;j <= dimensions; j++){
                int curPower = g.fuelCellPower(i, j);
                if (curPower > bestResult.val){
                    bestResult = new GridResult(i, j, curPower);
                }
            }
        }

        System.out.println("[Part 1]: Most powerful grid at: (" +
                bestResult.x + ", " + bestResult.y + ")" +
                " Power: " + bestResult.val);

    }
}


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

    public int fuelCellPower(int x, int y, int subGridDimensions){

        if (x < 1 || y < 1 ||
                (x + subGridDimensions) >= dimensions ||
                (y + subGridDimensions) >= dimensions)
            return 0;

        int sum = 0;
        for(int i=x; i < x + subGridDimensions ; i++){
            for(int j=y; j < y + subGridDimensions ; j++) {
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
    public GridResult(int x, int y, int val, int size) {
        this.x = x;
        this.y = y;
        this.val = val;
        this.size = size;
    }
    public int size;
    public int x;
    public int y;
    public int val;
}



public class Day11 {

    public static void doPart1(){
        final int dimensions = 300;
        final int serial = 2866;
        Grid g = new Grid(serial, dimensions);

        GridResult bestResult = new GridResult(0,0,0, 0);
        for(int i=1; i <= dimensions; i++){
            for(int j=1;j <= dimensions; j++){
                int curPower = g.fuelCellPower(i, j, 3);
                if (curPower > bestResult.val){
                    bestResult = new GridResult(i, j, curPower, 3);
                }
            }
        }

        System.out.println("[Part 1]: Most powerful grid at: (" +
                bestResult.x + ", " + bestResult.y + ")" +
                " Power: " + bestResult.val);

    }

    public static void doPart2(){
        final int dimensions = 300;
        final int serial = 2866;
        Grid g = new Grid(serial, dimensions);

        GridResult bestResult = new GridResult(0,0,0, 0);
        for(int sz=1; sz <= dimensions; sz++) {
            System.out.println("Processing size: " + sz);
            for (int i = 1; i <= dimensions - sz + 1; i++) {
                for (int j = 1; j <= dimensions - sz + 1; j++) {
                    int curPower = g.fuelCellPower(i, j, sz);
                    if (curPower > bestResult.val) {
                        bestResult = new GridResult(i, j, curPower, sz);
                    }
                }
            }
        }

        System.out.println("[Part 2]: Most powerful grid at: (" +
                bestResult.x + ", " + bestResult.y + ")" +
                " Power: " + bestResult.val +
                " Size: " + bestResult.size);

    }

    public static void main(String[] args) {
        doPart2();

    }
}

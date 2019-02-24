
class Grid{
    public Grid(int serial) {
        this.serial = serial;
        this.dimensions = 300 + 1; //one-based indexing system.
        theGrid = new int[dimensions][dimensions];
        populateGrid();
    }

    private void populateGrid(){
        for(int i = 1; i < dimensions; i++){
            for(int j = 1; j < dimensions; j++) {
                theGrid[i][j] = unitCellPower(i, j);
            }
        }
    }

    private int digitValue(int arg, int digit){
        //digit: 1 = first digit, 2 = second, etc.
        return (arg / (1 << (digit - 1))) % 10;
    }

    private int unitCellPower(int x, int y){
        int rackID = x + 10;
        int powerLevel = rackID * y;
        powerLevel += serial;
        powerLevel *= rackID;
        powerLevel = digitValue(powerLevel, 3);  //Hundreds digit
        powerLevel -= 5;
        return powerLevel;
    }

    private int fuelCellPower(int x, int y){
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



public class Day11 {
    public static void main(String[] args) {
        Grid g = new Grid(8);

    }
}

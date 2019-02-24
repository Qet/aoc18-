

public class Day11 {

    class Grid{
        public Grid(int serial) {
            this.serial = serial;
            this.dimensions = 300 + 1; //one-based indexing system.
            theGrid = new int[dimensions][dimensions];
        }

        private void populateGrid(){

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


    public static void main(String[] args) {

    }




}

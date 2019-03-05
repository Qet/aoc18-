import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Mine{

    /*

    \
    / = S, E
    - = W, E
    | = N, S
    + = NESW
    < = cart, facing W
    > = cart, facing E
    ^ = cart, facing N
    v = cart, facing S

     */

    final int SIZE = 200;  //Width and height (it's square)

    private void basicGridBoundsCheck(int row, int col){
        if (row < 0 || row >= SIZE ||
            col < 0 || col >= SIZE){
            throw new IllegalArgumentException("row or col out of range!");
        }
    }

    private Square getSquareNorthOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (row == 0){
            throw new IllegalArgumentException("Cannot request north of the top most row");
        }
        return grid[row - 1][col];
    }

    private Square getSquareEastOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (col == SIZE - 1){
            throw new IllegalArgumentException("Cannot request east of the right most column");
        }
        return grid[row][col + 1];
    }

    private Square getSquareSouthOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (row == SIZE - 1){
            throw new IllegalArgumentException("Cannot request south of the bottom most row");
        }
        return grid[row + 1][col];
    }

    private Square getSquareWestOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (col == 0){
            throw new IllegalArgumentException("Cannot request west of the left most column");
        }
        return grid[row][col - 1];
    }

    private void LinkParseLine(String line, int currentGridRow){
        for (int j = 0; j < line.length(); j++) {
            char curChar = line.charAt(j);
            Square curSquare = grid[currentGridRow][j];
            switch (curChar){
                case '+':
                    Square northSq = grid[currentGridRow - 1][j];
                    Square southSq = grid[currentGridRow + 1][j];
                    Square eastSq = grid[currentGridRow][j + 1];
                    Square westSq = grid[currentGridRow][j - 1];

                case '/':
                case '\\':
                case '-':
                case '|':
                case '<':
                case '>':
                case '^':
                case 'v':
                default: throw new IllegalArgumentException("Unrecognised character to parse: " + curChar);
            }
        }
    }


    final String validChars = "+-|/\\<>v^";
    // Create squares wherever there's a piece of track in the mine.
    void initialiseGrid(ArrayList<String> lines){
        for (int i = 0; i < lines.size(); i++) {
            String curLine = lines.get(i);
            for (int j = 0; j < curLine.length(); j++) {
                String curChar = curLine.substring(j, j + 1);
                if (validChars.contains(curChar)){
                    grid[i][j] = new Square(false);
                }
            }
        }
    }

    public Mine(){
        InputReader ir = new InputReader(13);

        ArrayList<String> lines = ir.getLines();

        initialiseGrid(lines);

    }

    //these will all be null at first
    private Square[][] grid = new Square[SIZE][SIZE];

}

class Cart{

    private void incrementNextTurn(){
        nextTurnQuarters++;
        if (nextTurnQuarters > 3){
            nextTurnQuarters = 1;
        }
    }

    int nextTurnQuarters = 1;

    //where the cart is
    Square currentSquare;

    //where the cart came from
    Square entrySquare;
}

enum Directions{
    North,
    East,
    South,
    West;

    Directions nextClockwiseDir(Directions currentDir){
        Directions[] values = Directions.values();
        int currentValue = currentDir.ordinal();
        int ret;
        if (currentValue == values.length - 1){
            ret = 0;
        }
        else{
            ret = currentValue + 1;
        }
        return values[ret];
    }
}

class Square{

    public Square(boolean intersection) {
        this.intersection = intersection;
        this.exits = new HashMap<>();
    }

    public void addExit(Square exit, Directions direction){
        exits.put(direction, exit);
    }

    public Square getExit(Directions direction){
        return exits.get(direction);
    }

    //intersections are marked with a "+"
    public boolean isIntersection(){
        return intersection;
    }

    //all the entries and exits from this square
    Map<Directions, Square> exits;

    boolean intersection;
}


public class Day13 {
    /*
    carts on the top row move first (acting from left to right), then carts on the second row move (again from left to right),

    Each time a cart has the option to turn (by arriving at any intersection), it turns left the first time,
    goes straight the second time, turns right the third time, and then repeats those directions

        you'd like to know the location of the first crash.


     */

    public static void main(String[] args) {
        Mine mine = new Mine();
    }
}

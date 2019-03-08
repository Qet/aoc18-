import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class SquareExitResult{
    public SquareExitResult(Square exitSquare, Directions direction) {
        this.exitSquare = exitSquare;
        this.direction = direction;
    }

    public Square exitSquare;
    public Directions direction;
}


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

    private SquareExitResult getSquareNorthOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (row == 0){
            throw new IllegalArgumentException("Cannot request north of the top most row");
        }
        return new SquareExitResult(grid[row - 1][col], Directions.North);
    }

    private SquareExitResult getSquareEastOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (col == SIZE - 1){
            throw new IllegalArgumentException("Cannot request east of the right most column");
        }
        return new SquareExitResult(grid[row][col + 1], Directions.East);
    }

    private SquareExitResult getSquareSouthOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (row == SIZE - 1){
            throw new IllegalArgumentException("Cannot request south of the bottom most row");
        }
        return new SquareExitResult(grid[row + 1][col], Directions.South);
    }

    private SquareExitResult getSquareWestOf(int row, int col){
        basicGridBoundsCheck(row, col);
        if (col == 0){
            throw new IllegalArgumentException("Cannot request west of the left most column");
        }
        return new SquareExitResult(grid[row][col - 1], Directions.West);
    }


    private boolean isSouthReflector(int row, int col){
        char reflector = rawData[row][col];

        /*

        /a
        b

        b
        \a

        where a could be: + < > ^ v -
        where b could be: + < > ^ v |

         */

        String possibleAnySquares = "+<>^v";
        String possibleSideSquares = possibleAnySquares + "-";
        String possibleVertSquares = possibleAnySquares + "|";

        String sideString = String.valueOf(rawData[row][col + 1]);
        String vertString = "";
        if (reflector == '/'){
            vertString = String.valueOf(rawData[row + 1][col]);
            if (possibleSideSquares.contains(sideString) && possibleVertSquares.contains(vertString)) {
                return true;
            }
            else{
                return false;
            }
        }
        else if (reflector == '\\'){
            if (row == 0)
                return true;
            vertString = String.valueOf(rawData[row - 1][col]);
            if (possibleSideSquares.contains(sideString) && possibleVertSquares.contains(vertString)) {
                return false;
            }
            else{
                return true;
            }
        }
        else {
            throw new RuntimeException("Shouldn't get here...");
        }
    }

    private void parseRawData(){
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                char curChar = rawData[row][col];
                Square curSquare = grid[row][col];
                switch (curChar) {
                    case '+':
                        curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                        curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                        curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        break;
                    case '/':
                        if (isSouthReflector(row, col)) {
                            curSquare.addDirectionalExit(getSquareEastOf(row, col));
                            curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                        }
                        else{
                            curSquare.addDirectionalExit(getSquareWestOf(row, col));
                            curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                        }
                        break;
                    case '\\':
                        if (isSouthReflector(row, col)) {
                            curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                            curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        }
                        else {
                            curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                            curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        }
                        break;

                /*
                There a slight issue here, because a '<' or a '>' could occur on a corner or an intersection.
                But it doesn't for the input data at least, so we're fine. Ditto on '^' and 'v'
                 */
                    case '<':
                    case '>':
                    case '-':
                        curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        break;
                    case '^':
                    case 'v':
                    case '|':
                        curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                        curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                        break;
                    case ' ':
                        break;
                    default:
                        //throw new IllegalArgumentException("Unrecognised character to parse: " + curChar);
                        break;
                }
            }
        }
    }


    final String validChars = "+-|/\\<>v^";
    // Create squares wherever there's a piece of track in the mine.
    void initialiseGridAndRawData(ArrayList<String> lines){
        for (int row = 0; row < lines.size(); row++) {
            String curLine = lines.get(row);
            for (int col = 0; col < curLine.length(); col++) {
                String curCharStr = curLine.substring(col, col + 1);
                char curChar = curLine.charAt(col);
                if (validChars.contains(curCharStr)){
                    grid[row][col] = new Square(false);
                    rawData[row][col] = curChar;
                }
            }
        }
    }



    public Mine(){
        InputReader ir = new InputReader(13);

        ArrayList<String> lines = ir.getLines();

        initialiseGridAndRawData(lines);

        parseRawData();
    }

    //these will all be null at first
    private Square[][] grid = new Square[SIZE][SIZE];
    private char[][] rawData = new char[SIZE][SIZE];

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

    public void addDirectionalExit(SquareExitResult exitDetails){
        exits.put(exitDetails.direction, exitDetails.exitSquare);
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

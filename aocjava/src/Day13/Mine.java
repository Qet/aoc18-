package Day13;

import InputReader.InputReader;

import java.util.ArrayList;
import java.util.Collections;

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



    public static final int SIZE = 200;  //Width and height (it's square)
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

    private void makeCart(Square curSq, Square prevSq, int row, int col, Directions facing){
        Cart c = new Cart(curSq, prevSq);
        c.row = row;
        c.col = col;
        c.facing = facing;
        carts.add(c);
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
                        curSquare.intersection = true;
                        break;
                    case '/':
                        curSquare.forwardslash = true;
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
                        curSquare.backslash = true;
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
                        makeCart(curSquare, getSquareEastOf(row, col).exitSquare, row, col, Directions.West);
                        curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        break;
                    case '>':
                        makeCart(curSquare, getSquareWestOf(row, col).exitSquare, row, col, Directions.East);
                        curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        break;
                    case '-':
                        curSquare.addDirectionalExit(getSquareEastOf(row, col));
                        curSquare.addDirectionalExit(getSquareWestOf(row, col));
                        break;
                    case '^':
                        makeCart(curSquare, getSquareSouthOf(row, col).exitSquare, row, col, Directions.North);
                        curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                        curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                        break;
                    case 'v':
                        makeCart(curSquare, getSquareNorthOf(row, col).exitSquare, row, col, Directions.South);
                        curSquare.addDirectionalExit(getSquareSouthOf(row, col));
                        curSquare.addDirectionalExit(getSquareNorthOf(row, col));
                        break;
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

        SortCarts();
    }

    private void SortCarts(){
        // sort the carts in the order in which they should be processed.
        /*
        They do this based on their current location: carts on the top row move first (acting from left to right),
        then carts on the second row move (again from left to right), then carts on the third row, and so on.
        Once each cart has moved one step, the process repeats; each of these loops is called a tick.
         */

        Collections.sort(carts);
    }




    public void stepCarts(){
        // step all the carts forward.

        /*

        all carts have a direction. look at the square ahead in that direction, if it's not straight then the cart
        will have to turn.

        either it will be a forced run (such as a right angle \ or / ) or it will be an intersection, in which
        case the special turning rules will need to be applied.

        Each time a cart has the option to turn (by arriving at any intersection), it turns left the first time,
        goes straight the second time, turns right the third time

         */

        for (Cart c : carts){
            Square nextSquare = c.currentSquare.exits.get(c.facing);

            if (nextSquare.forwardslash){  //  " / "
                if (c.facing == Directions.North){
                    c.facing = Directions.East;
                }
                else if (c.facing == Directions.South){
                    c.facing = Directions.West;
                }
                else if (c.facing == Directions.East){
                    c.facing = Directions.North;
                }
                else if (c.facing == Directions.West){
                    c.facing = Directions.South;
                }
                else{
                    throw new IllegalStateException();
                }

            }
            else if (nextSquare.backslash){ // " \ "
                if (c.facing == Directions.North){
                    c.facing = Directions.West;
                }
                else if (c.facing == Directions.South){
                    c.facing = Directions.East;
                }
                else if (c.facing == Directions.East){
                    c.facing = Directions.South;
                }
                else if (c.facing == Directions.West){
                    c.facing = Directions.North;
                }
                else{
                    throw new IllegalStateException();
                }
            }
            else if (nextSquare.intersection){
                c.nextTurnQuarters
            }


        }

    }




    //these will all be null at first
    private Square[][] grid = new Square[SIZE][SIZE];
    private char[][] rawData = new char[SIZE][SIZE];
    private ArrayList<Cart> carts = new ArrayList<>();

}

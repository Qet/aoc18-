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

    private void LinkParseLine(String line, int currentGridRow){
        for (int i = 0; i < line.length(); i++) {
            char curChar = line.charAt(i);
            switch (curChar){
                case '+':
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

    private void FirstParseLine(String line, Square[] currentGridRow){
        for (int i = 0; i < line.length(); i++) {
            char curChar = line.charAt(i);
            Square currentSquare = null;
            switch (curChar){
                case '+':
                    currentSquare = new Square(true);
                    break;
                case '/':
                case '\\':
                case '-':
                case '|':
                case '<':
                case '>':
                case '^':
                case 'v':
                    currentSquare = new Square(true);
                    break;

                default: throw new IllegalArgumentException("Unrecognised character to parse: " + curChar);

            }
            currentGridRow[i] = currentSquare;
        }
    }

    public Mine(){
        InputReader ir = new InputReader(13);

        ArrayList<String> lines = ir.getLines();

        for (int i = 0; i < lines.size(); i++) {
            ParseLine(lines.get(i), grid[i]);
        }

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
}

package Day13;

import InputReader.InputReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public final class Day13Quick {

    enum Turn{
        LEFT,
        STRAIGHT,
        RIGHT;

        public static Turn increment(Turn value){
            switch (value){
                case LEFT: return STRAIGHT;
                case STRAIGHT: return RIGHT;
                case RIGHT: return LEFT;
                default: return LEFT;
            }
        }
    }

    enum Facing{
        EAST,
        NORTH,
        SOUTH,
        WEST;

        public static Facing fromChar(char input){
            switch (input) {
                case '>':
                    return Facing.EAST;
                case '<':
                    return Facing.WEST;
                case '^':
                    return Facing.NORTH;
                case 'v':
                    return Facing.SOUTH;
                default: return Facing.NORTH; //whatever...
            }
        }

        public static char toChar(Facing input){
            switch (input){
                case EAST: return '>';
                case WEST: return '<';
                case NORTH: return '^';
                case SOUTH: return 'v';
            }
            return '?';
        }

        public static Facing applyTurn(Turn turn, Facing currentFacing){
            if (turn == Turn.STRAIGHT){
                return currentFacing;
            }

            switch (currentFacing){
                case NORTH:
                    switch (turn){
                        case LEFT: return Facing.WEST;
                        case RIGHT: return Facing.EAST;
                    }
                    break;
                case EAST:
                    switch (turn){
                        case LEFT: return Facing.NORTH;
                        case RIGHT: return Facing.SOUTH;
                    }
                    break;
                case SOUTH:
                    switch (turn){
                        case LEFT: return Facing.EAST;
                        case RIGHT: return Facing.WEST;
                    }
                    break;
                case WEST:
                    switch (turn){
                        case LEFT: return Facing.SOUTH;
                        case RIGHT: return Facing.NORTH;
                    }
                    break;
            }
            System.out.println("apply turn error.");
            return Facing.NORTH;
        }
    }

    class Cart{
        public int row;
        public int col;
        public Turn nextTurn;
        public Facing currentFacing;

        public Cart(int row, int col, Facing facing) {
            this.row = row;
            this.col = col;
            this.nextTurn = Turn.LEFT;
            this.currentFacing = facing;
        }

        public void rotateIntersectionDirection(){
            nextTurn = Turn.increment(nextTurn);
        }


    }

    class CartComparator implements Comparator<Cart>{
        @Override
        public int compare(Cart o1, Cart o2) {
            if (o1.row != o2.row){
                return (o1.row - o2.row) * 200;
            }
            else{
                return o1.col - o2.col;
            }
        }
    }


    final int SIZE = 200;
    char[][] rawData = new char[SIZE][SIZE];

    List<Cart> carts = new ArrayList<>();


    int maxRows;
    int maxCols;

    boolean isCart(char input){
        char c = Character.toLowerCase(input);
        return c == '>' || c == '<' || c == 'v' || c == '^';
    }



    void loadData(){
        InputReader ir = new InputReader(13);
        //InputReader ir = new InputReader("input13test.txt");

        ArrayList<String> lines = ir.getLines();
        maxRows = lines.size();
        for (int row = 0; row < lines.size(); row++) {
            String curLine = lines.get(row);
            maxCols = Math.max(maxCols, curLine.length());
            for (int col = 0; col < curLine.length(); col++) {
                char curChar = curLine.charAt(col);
                if (isCart(curChar)) {
                    carts.add(new Cart(row, col, Facing.fromChar(curChar)));

                    //replace the cart character with a normal piece of mine in the grid.
                    switch (curChar) {

                        //the carts always start on a straight section of track.
                        case '>':
                        case '<':
                            rawData[row][col] = '-';
                            break;
                        case '^':
                        case 'v':
                            rawData[row][col] = '|';
                            break;
                    }
                }
                else{
                    rawData[row][col] = curChar;
                }
            }
        }
    }

    void printData(){

        char[][] cartOverlay = new char[SIZE][SIZE];

        for(Cart c : carts){
            cartOverlay[c.row][c.col] = Facing.toChar(c.currentFacing);
        }

        for(int row = 0; row < maxRows; row++){
            for(int col = 0; col < maxCols; col++){
                if (cartOverlay[row][col] != '\0')
                    System.out.print(cartOverlay[row][col]);

                else
                    System.out.print(rawData[row][col]);
            }
            System.out.println();
        }
    }

    //returns true if there was no crash (i.e. can keep going). false otherwise.
    boolean moveCarts() {
        Collections.sort(carts, new CartComparator());

        List<Cart> toRemove = new ArrayList<>();

        for(Cart c : carts) {
            switch (c.currentFacing) {
                case WEST:
                    c.col--;
                    if (rawData[c.row][c.col] == '/')
                        c.currentFacing = Facing.SOUTH;
                    if (rawData[c.row][c.col] == '\\')
                        c.currentFacing = Facing.NORTH;
                    break;
                case EAST:
                    c.col++;
                    if (rawData[c.row][c.col] == '/')
                        c.currentFacing = Facing.NORTH;
                    if (rawData[c.row][c.col] == '\\')
                        c.currentFacing = Facing.SOUTH;
                    break;
                case NORTH:
                    c.row--;
                    if (rawData[c.row][c.col] == '/')
                        c.currentFacing = Facing.EAST;
                    if (rawData[c.row][c.col] == '\\')
                        c.currentFacing = Facing.WEST;
                    break;
                case SOUTH:
                    c.row++;
                    if (rawData[c.row][c.col] == '/')
                        c.currentFacing = Facing.WEST;
                    if (rawData[c.row][c.col] == '\\')
                        c.currentFacing = Facing.EAST;
                    break;
                default:
                    System.out.println("move carts error");
            }

            if (rawData[c.row][c.col] == '+') {
                c.currentFacing = Facing.applyTurn(c.nextTurn, c.currentFacing);
                c.rotateIntersectionDirection();
            }

            toRemove.addAll(checkCrashes(c));

        }

        carts.removeAll(toRemove);

        return true;
    }

    boolean checkNumCarts(){
        if (carts.size() == 1){
            Cart c = carts.get(0);
            System.out.println("One cart left at: (" + c.col + ", " + c.row + ')');
            return true;
        }
        return false;
    }

    //returns true if there's a crash.
    List<Cart> checkCrashes(Cart checkAgainst){

        List<Cart> toRemove = new ArrayList<>();

        for(Cart c : carts) {
            if (c != checkAgainst){
                if (c.col == checkAgainst.col && c.row == checkAgainst.row){
                    System.out.println("Crash at (" + c.col + ", " + c.row + ')');
                    toRemove.add(c);
                    toRemove.add(checkAgainst);
                    break;
                }
            }
        }

        return toRemove;
    }

    void initialiseData(){
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                rawData[i][j] = ' ';
            }
        }
    }

    public static void main(String[] args) {
        Day13Quick d = new Day13Quick();
        d.initialiseData();
        d.loadData();
        //d.printData();
        while (d.moveCarts()) {
          //  d.printData();
            if (d.checkNumCarts())
                break;
        }
        d.printData();
    }
}



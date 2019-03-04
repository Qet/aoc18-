import java.util.HashMap;
import java.util.Map;

class Mine{

    int size;  //Width and height (it's square)

}

class Cart{

    

    Directions nextTurn = Directions.East;

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

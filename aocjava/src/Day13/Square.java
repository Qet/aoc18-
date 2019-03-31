package Day13;

import java.util.HashMap;
import java.util.Map;

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

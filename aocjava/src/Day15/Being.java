package Day15;

import java.util.List;

public class Being {
    public Coords getCoords() {
        return coords;
    }

    public List<Coords> getAdjacentSquares(){

    }

    private Coords coords;

    public Being(Coords coords) {
        this.coords = coords;
    }
}
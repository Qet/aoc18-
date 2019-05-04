package Day15;

import java.util.Comparator;

class DistanceComparator implements Comparator<Coords> {
/*
implements the distance of the source to a particular coord
 */

    @Override
    public int compare(Coords o1, Coords o2) {
        return 0;
    }

    Being source;
    Grid grid;

    public DistanceComparator(Being source, Grid grid) {
        this.source = source;
        this.grid = grid;
    }

}

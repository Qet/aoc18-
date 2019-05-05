package Day15;

import java.util.Comparator;

class DistanceComparator implements Comparator<Coords> {
/*
implements the distance of the source to a particular coord
 */

    class Node{
        public Node bestNode = null;
        public int bestDist = Integer.MAX_VALUE;
    }

    Node[][] nodes;

    @Override
    public int compare(Coords o1, Coords o2) {
        clearNodes();

        nodes[o1.row][o1.col].bestDist = 0;
    }

    Being source;
    Grid grid;
    int rows;
    int cols;

    void clearNodes(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                nodes[i][j] = new Node();
            }
        }
    }

    public DistanceComparator(Being source, Grid grid) {
        this.source = source;
        this.grid = grid;
        rows = grid.getNumRows();
        cols = grid.getNumCols();
        nodes = new Node[grid.getNumRows()][grid.getNumCols()];
    }

}

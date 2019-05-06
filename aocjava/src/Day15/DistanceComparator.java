package Day15;

import java.util.*;

class DistanceComparator implements Comparator<Coords> {
/*
implements the distance of the source to a particular coord
 */

    class Node{
        public Node(Coords coord, Node bestNode, int bestDist) {
            this.coord = coord;
            this.bestNode = bestNode;
            this.bestDist = bestDist;
        }

        public Coords coord;
        public Node bestNode = null;
        public int bestDist = Integer.MAX_VALUE;
    }

    @Override
    public int compare(Coords o1, Coords o2) {

        Queue<Coords> remainingCoords = new PriorityQueue<>();
        Map<Coords, Node> nodeMap = new HashMap<>();
        Set<Coords> doneCoords = new HashSet<>();

        boolean done = false;

        Node curNode = new Node(o1, null, 0);
        nodeMap.put(o1, curNode);

        while(!done){
            List<Coords> adjCoords = grid.getAdjSquares(curNode.coord);
            for(Coords curCoord: adjCoords){
                if (!doneCoords.contains(curCoord)) {
                    Node newNode = new Node(curCoord, curNode, 1 + curNode.bestDist);
                    nodeMap.put(curCoord, newNode);
                    remainingCoords.add(curCoord);
                }
            }
            doneCoords.add(curNode.coord);

            if (remainingCoords.size() > 0){
                curNode = nodeMap.get(remainingCoords.remove());
            }
            else{
                done = true;
            }
        }

        return nodeMap.get(o2).bestDist;
    }

    Being source;
    Grid grid;
    int rows;
    int cols;


    public DistanceComparator(Being source, Grid grid) {
        this.source = source;
        this.grid = grid;
        rows = grid.getNumRows();
        cols = grid.getNumCols();
    }

}

package Day15;

import java.util.*;

class DistanceComparator implements Comparator<Coords> {
    private Set<Coords> doneCoords;
    private Map<Coords, Node> nodeMap;
    private Deque<Coords> remainingCoords;


    Being source;
    Grid grid;
    int rows;
    int cols;
    final int UNPASSABLE = Integer.MAX_VALUE / 2;
    private Node curNode;

    @Override
    public int compare(Coords o1, Coords o2) {

        remainingCoords = new ArrayDeque<>();
        nodeMap = new HashMap<>();
        doneCoords = new HashSet<>();

        boolean done = false;

        curNode = new Node(o1, null, 0);
        nodeMap.put(o1, curNode);

        while(!done){
            List<Coords> adjCoords = grid.getAdjSquares(curNode.coord);
            for(Coords curCoord: adjCoords){
                if (!doneCoords.contains(curCoord)) {
                    addNewNode(curCoord);
                }
            }
            doneCoords.add(curNode.coord);

            if (remainingCoords.size() > 0){
                curNode = nodeMap.get(remainingCoords.pop());
            }
            else{
                done = true;
            }
        }

        return nodeMap.get(o2).bestDist;
    }

    private void addNewNode(Coords curCoord) {
        int dist = 0;
        if (grid.isPassable(curCoord)) {
            dist = 1;
        }
        else{
            dist = UNPASSABLE;
        }
        int thisDist = dist + curNode.bestDist;
        
        Node newNode = new Node(curCoord, curNode, );
        nodeMap.put(curCoord, newNode);
        remainingCoords.add(curCoord);
    }

    public DistanceComparator(Being source, Grid grid) {
        this.source = source;
        this.grid = grid;
        rows = grid.getNumRows();
        cols = grid.getNumCols();
    }

}

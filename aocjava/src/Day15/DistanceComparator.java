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
    private Node curNode;


    void setUpNodeMap(){
        nodeMap = new HashMap<>();

        int rows = grid.getNumRows();
        int cols = grid.getNumCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coords curCoords = new Coords(i, j);
                if (grid.isPassable(curCoords)){
                    Node newNode = new Node(curCoords, null, Integer.MAX_VALUE);
                    nodeMap.put(curCoords, newNode);
                }
            }
        }
    }

    List<Node> getAdjNodes(Node curNode){
        List<Coords> adjCoords = grid.getAdjSquares(curNode.coord);
        List<Node> ret = new ArrayList<>();
        for (Coords coord : adjCoords){
            if (nodeMap.containsKey(coord)){
                ret.add(nodeMap.get(coord));
            }
        }
        return ret;
    }

    @Override
    public int compare(Coords o1, Coords o2) {

        remainingCoords = new ArrayDeque<>();
        doneCoords = new HashSet<>();
        setUpNodeMap();
        boolean done = false;

        Node curNode = nodeMap.get(o1);
        while(!done) {
            List<Node> adjNodes = getAdjNodes(curNode);
            int bestDist = Integer.MAX_VALUE;
            Node bestNode = null;
            for (Node adjNode: adjNodes) {
                int proposedDist = curNode.bestDist + 1;
                if (proposedDist < adjNode.bestDist){
                    adjNode.bestDist = proposedDist;
                    adjNode.bestNode = curNode;
                }
            }

            doneCoords.add(curNode.coord);

            if (remainingCoords.size() > 0) {
                curNode = nodeMap.get(remainingCoords.pop());
            } else {
                done = true;
            }
        }

        return nodeMap.get(o2).bestDist;
    }

    private int getDist(Node curNode, Coords curCoord, Coords targetCoord){
        if (!grid.isPassable(curCoord) || !grid.isPassable(targetCoord))
            return UNPASSABLE;
        return 1 + nodeMap.get(curCoord).bestDist;
    }

    private void addNewNode(Coords curCoord) {
        Node newNode = new Node(curCoord, null, UNPASSABLE);
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

package Day15;

import java.util.*;

class DistanceComparator implements Comparator<Coords> {
    private Map<Coords, Node> nodeMap;

    Being source;
    Grid grid;
    int rows;
    int cols;

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
        Deque<Node> remainingNodes = new ArrayDeque<>();
        Set<Node> visitedNodes = new HashSet<>();
        setUpNodeMap();
        boolean done = false;

        Node curNode = nodeMap.get(o1);
        curNode.bestDist = 0; //Starting node has 0 dist to itself.

        while(!done) {
            List<Node> adjNodes = getAdjNodes(curNode);
            for (Node adjNode: adjNodes) {
                int proposedDist = curNode.bestDist + 1;
                if (proposedDist < adjNode.bestDist){
                    adjNode.bestDist = proposedDist;
                    adjNode.bestNode = curNode;
                }

                visitedNodes.add(curNode);

                if (!visitedNodes.contains(adjNode) &&
                        !remainingNodes.contains(adjNode)){
                    remainingNodes.push(adjNode);
                }
            }

            if (remainingNodes.size() > 0) {
                curNode = remainingNodes.pop();
            } else {
                done = true;
            }
        }

        return nodeMap.get(o2).bestDist;
    }

    public DistanceComparator(Being source, Grid grid) {
        this.source = source;
        this.grid = grid;
        rows = grid.getNumRows();
        cols = grid.getNumCols();
    }

}

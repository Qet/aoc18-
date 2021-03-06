package Day15;

import java.util.*;

class DistanceComparator implements Comparator<Coords> {
    private Map<Coords, Node> nodeMap;

    Being source;
    Grid grid;
    int rows;
    int cols;

    Node makeInitialNode(Coords fromCoords){
        return new Node(fromCoords, null, Integer.MAX_VALUE);
    }
    void setUpNodeMap(Coords o1, Coords o2){
        nodeMap = new HashMap<>();

        int rows = grid.getNumRows();
        int cols = grid.getNumCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coords curCoords = new Coords(i, j);
                if (grid.isPassable(curCoords)){
                    Node newNode = makeInitialNode(curCoords);
                    nodeMap.put(curCoords, newNode);
                }
            }
        }

        //o1 and o2 must be in the map regardless of passability,
        // otherwise we can't calculate the distance between them
        nodeMap.put(o1, makeInitialNode(o1));
        nodeMap.put(o2, makeInitialNode(o2));
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
        setUpNodeMap(o1, o2);
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

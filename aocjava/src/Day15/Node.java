package Day15;

class Node {
    public Node(Coords coord, Node bestNode, int bestDist) {
        this.coord = coord;
        this.bestNode = bestNode;
        this.bestDist = bestDist;
    }

    public Coords coord;
    public Node bestNode = null;
    public int bestDist = Integer.MAX_VALUE;
}

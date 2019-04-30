package Day15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pathing {

    class Node{
        public Node(String ID){
            this.ID = ID;
            bestPath = null;
            bestDist = Integer.MAX_VALUE;
        }
        String ID;
        Node bestPath;
        int bestDist;

        @Override
        public String toString() {
            return ID;
        }
    }

    //for each node (key), there is a map of it's distance to each other node
    Map<Node, Map<Node, Integer>> vertices = new HashMap<>();
    Map<String, Node> nodes = new HashMap<>();

    void initVertices(){

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        nodes.put(A.ID, A);
        nodes.put(B.ID, B);
        nodes.put(C.ID, C);
        nodes.put(D.ID, D);

        Map<Node, Integer> nodeAVerts = new HashMap<>();
        nodeAVerts.put(B, 1);
        nodeAVerts.put(C, 2);
        vertices.put(A, nodeAVerts);

        Map<Node, Integer> nodeBVerts = new HashMap<>();
        nodeBVerts.put(A, 1);
        nodeBVerts.put(D, 3);
        vertices.put(B, nodeBVerts);

        Map<Node, Integer> nodeCVerts = new HashMap<>();
        nodeCVerts.put(A, 2);
        nodeCVerts.put(D, 1);
        vertices.put(C, nodeCVerts);

        Map<Node, Integer> nodeDVerts = new HashMap<>();
        nodeDVerts.put(B, 3);
        nodeDVerts.put(C, 1);
        vertices.put(D, nodeDVerts);
    }

    void dijkstra(){
        Node startNode = nodes.get("A");
        startNode.bestDist = 0;

        Set<Node> nodeset = new HashSet<>();
        nodeset.addAll(nodes.values());
        nodeset.remove(startNode);

        for(Node curSourceNode : nodes.values()) {
            for (Map.Entry<Node, Integer> curEntry : vertices.get(curSourceNode).entrySet()) {
                Node curAdjNode = curEntry.getKey();
                int edgeDist = curEntry.getValue();
                System.out.println("curDist = " + edgeDist);
                System.out.println("curAdjNode = " + curAdjNode.ID);

                //is the distance to the current source better than the alternative?
                int comparisonDist = edgeDist + curSourceNode.bestDist;
                if (comparisonDist < curAdjNode.bestDist) {
                    System.out.println("New best distance of: " + comparisonDist + " via node: " + curSourceNode.ID);
                    curAdjNode.bestDist = comparisonDist;
                    curAdjNode.bestPath = curSourceNode;
                }
            }
        }
    }

    void bestPath(){
        //from D to A (reverse back to the source, which was A)
        Node dest = nodes.get("D");
        Node source = nodes.get("A");

        Node curHop = dest;
        while(curHop != source){
            System.out.println("From node " + curHop + " to node " + curHop.bestPath + "(" + curHop.bestDist + ")");
            curHop = curHop.bestPath;
        }
        
    }

    public Pathing(){
        initVertices();
        dijkstra();
        bestPath();
    }

    public static void main(String[] args) {
        Pathing p = new Pathing();

        /*
            1
         /1-b-3\ 4
        a      d
        \2-c-1/  6
           2

        a->b 1
        a->c 2
        b->d 3
        c->d 1

        - each node has a distance from the start.
        - each edge has a distance.



        dist[b] = 1
        dist[c] = 2
         */





    }
}

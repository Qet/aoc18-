import java.util.*;

class Edge{
    public Edge(char src, char dest){
        this.src = src;
        this.dest = dest;
    }
    public char src;
    public char dest;
}

public class Day7 {
    public static void main(String[] args) {
        Map<Character, Set<Character>> data = parseLines(new InputReader(7));

        StringBuilder result = new StringBuilder();
        Set<Character> doneSteps = new HashSet<>();
        while(doneSteps.size() < data.keySet().size()){
            Character nextStep = findNextStep(data, doneSteps);
            doneSteps.add(nextStep);
            result.append(nextStep);
            System.out.println("Adding " + nextStep);
        }

        System.out.println(result.toString());


    }

    public static Character findNextStep(Map<Character, Set<Character>> data, Set<Character> doneSteps){
        ArrayList<Character> canBeginSteps = new ArrayList<>();

        for (Map.Entry<Character, Set<Character>> entry : data.entrySet()){
            // take the subset of non-done steps. if it's empty then this step can begin

            if (doneSteps.containsAll(entry.getValue())){
                if (!doneSteps.contains(entry.getKey())){
                    canBeginSteps.add(entry.getKey());
                }
            }
        }

        canBeginSteps.sort((a, b) -> a.compareTo(b));
        return canBeginSteps.get(0);
    }


    public static Edge parseLine(String line){
        String[] spl = line.split(" ");
        String src = spl[1];
        String dst = spl[7];
        return new Edge(src.charAt(0), dst.charAt(0));
    }

    public static Set<Edge> createEdgeSet(ArrayList<String> lines){
        Set<Edge> edges = new HashSet<>();

        for(String line : lines){
            Edge parsedEdge = parseLine(line);
            edges.add(parsedEdge);
        }
        return edges;
    }

    public static Set<Character> createNodeSet(Set<Edge> edges){
        Set<Character> nodes = new HashSet<>();

        for(Edge e : edges){
            nodes.add(e.src);
        }
        return nodes;
    }

    public static Set<Character> findAllEdgesMatchingNode(Character node, Set<Edge> edgeSet){
        Set<Character> ret = new HashSet<>();
        for(Edge e : edgeSet){
            if (e.src == node)
                ret.add(e.dest);
        }
        return ret;
    }

    public static Map<Character, Set<Character>> parseLines(InputReader ir){
        Set<Edge> edges = createEdgeSet(ir.getLines());
        Set<Character> nodes = createNodeSet(edges);
        return createDependencyMap(nodes, edges);
    }

    public static Map<Character, Set<Character>> createDependencyMap(Set<Character> nodes, Set<Edge> edges){
        // Returns the map of node to steps that have to be completed first for that node to begin.

        Map<Character, Set<Character>> ret = new HashMap<>();

        for(Character curNode : nodes){
            Set<Character> depSet = new HashSet<>();
            for(Edge curEdge : edges){
                if (curEdge.dest == curNode){
                    depSet.add(curEdge.src);
                }
            }
            ret.put(curNode, depSet);
        }
        return ret;
    }



}

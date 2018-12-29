import java.util.*;

class Edge{
    public Edge(char src, char dest){
        this.src = src;
        this.dest = dest;
    }
    public char src;
    public char dest;
}


class Worker{

    public enum WorkerState{
        IDLE,
        WORKING,
        FINISHED
    }

    public Worker(){
        state = WorkerState.IDLE;
    }

    public void startWork(Character step){
        this.step = step;
        state = WorkerState.WORKING;
        startTime = timeNow;
    }

    public void stepTime(int now){
        timeNow = now;
        if (isComplete()){
            state = WorkerState.FINISHED;
        }
    }

    public boolean isIdle(){ return state == WorkerState.IDLE; }
    public boolean isFinished(){ return state == WorkerState.FINISHED; }

    public Character collectResult(){
        state = WorkerState.IDLE;
        return step;
    }

    private Character step;
    private int startTime;
    private int timeNow;
    private WorkerState state;

    private boolean isComplete(){
        return (state == WorkerState.WORKING) && ((timeNow - startTime) >= calcTaskDuration(step));
    }

    private static int calcTaskDuration(Character task){
        return (int)((char)(task) - 'A') + 61;
    }


}

public class Day7 {
    public static void main(String[] args) {
        //doPart1();

        doPart2();


    }

    public static final int NUM_WORKERS = 5;

    public static void doPart2(){

        ArrayList<Worker> workers = createWorkers();
        int time = 0;
        Map<Character, Set<Character>> data = parseLines(new InputReader(7));
        Set<Character> doneSteps = new HashSet<>();
        Set<Character> workingAndDoneSteps = new HashSet<>();


        boolean foundIdleWorker = false;
        while(doneSteps.size() < data.keySet().size()){

            //Load up workers
            Character nextStep = findNextStep(data, workingAndDoneSteps);
            Worker nextFreeWorker = findIdleWorker(workers);
            if (nextStep != null && nextFreeWorker != null){
                if (nextFreeWorker.isIdle()){
                    nextFreeWorker.startWork(nextStep);
                    workingAndDoneSteps.add(nextStep);
                }
            }
            else{
                time++;
                incrementWorkerTime(workers, time);
            }

            //Check if workers are finished.
            for(Worker w : workers){
                if (w.isFinished()){
                    Character res = w.collectResult();
                    doneSteps.add(res);
                    workingAndDoneSteps.add(res);
                }
            }
        }

        System.out.println("[Part 2] All done in " + time + " seconds.");

    }

    public static void incrementWorkerTime(ArrayList<Worker> workers, int time){
        for(Worker w : workers){
            w.stepTime(time);
        }
    }

    public static ArrayList<Worker> createWorkers(){
        ArrayList<Worker> workers = new ArrayList<>();
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers.add(new Worker());
        }
        return workers;
    }

    public static Worker findIdleWorker(ArrayList<Worker> workers){
        for(Worker w : workers){
            if (w.isIdle()){
                return w;
            }
        }
        return null;
    }

    public static void doPart1(){
        Map<Character, Set<Character>> data = parseLines(new InputReader(7));

        StringBuilder result = new StringBuilder();
        Set<Character> doneSteps = new HashSet<>();
        while(doneSteps.size() < data.keySet().size()){
            Character nextStep = findNextStep(data, doneSteps);
            doneSteps.add(nextStep);
            result.append(nextStep);
        }
        System.out.println("[Part 1]: " + result.toString());

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
        if (canBeginSteps.size() == 0) {
            return null;
        }
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
            nodes.add(e.dest); // Because there can be some nodes that are only present as a dest, not a src.
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

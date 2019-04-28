import java.util.ArrayList;
import java.util.List;

public class Day14 {

    List<Integer> data = new ArrayList<>();
    StringBuilder dataStringBuilder = new StringBuilder();

    int p1;
    int p2;
    int lastCheckIndex = 0;

    public Day14() {
        p1 = 0;
        p2 = 1;
        addData(3);
        addData(7);
    }

    void addData(int val){
        data.add(val);
        dataStringBuilder.append(val);
    }

    void iterate(){
        //printData();
        addDigitSumToData();
        p1 = incrementPointer(p1);
        p2 = incrementPointer(p2);

    }

    void printData(){
        System.out.print("[");
        for(int i=0;i<data.size(); i++){
            System.out.print(data.get(i));
        }
        System.out.print("] ");
        System.out.print("  p1 = " + p1);
        System.out.print("  p2 = " + p2);
        System.out.println();
    }

    void addDigitSumToData(){
        int recipeSum = data.get(p1) + data.get(p2);

        int tens = recipeSum / 10;
        int ones = recipeSum - 10 * tens;

        if (tens > 0)
            addData(tens);

        addData(ones);
    }

    boolean gotEnoughData(int target){
        if (data.size() >= target + 10){
            System.out.print("After " + target + " recipes, the next 10 digits are: [");
            for(int i=target;i<target + 10; i++){
                System.out.print(data.get(i));
            }
            System.out.println("]");
            return true;
        }
        return false;
    }

    int incrementPointer(int pointer){
        int incrementAmount = data.get(pointer) + 1;
        int newPosition = pointer + incrementAmount;
        int dataSize = data.size();
        int removeRollovers = newPosition % dataSize;
        return removeRollovers;
    }

    boolean hasThisSequence(List<Integer> seq){

        boolean found = false;
        int maxCheckStart = data.size() - seq.size();
        int curCheckIndex;
        for(curCheckIndex = lastCheckIndex; curCheckIndex <= maxCheckStart; curCheckIndex++){
            int j;
            for(j = 0; j < seq.size(); j++)
                if (seq.get(j) != data.get(curCheckIndex + j)) break;
            if (j == seq.size()) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Found sequence [" + seq.toString() + "] starting at " + curCheckIndex);
            return true;
        }

        lastCheckIndex = Math.max(data.size() - seq.size() - 1, 0);
        return false;
    }

    public static void runSim(int target){
        Day14 d = new Day14();

        while (!d.gotEnoughData(target)){
            d.iterate();
        }

    }

    public static void runSimPart2(String target){
        Day14 d = new Day14();

        long t1 = 0;
        long t2 = 0;
        int runs = 0;

        List<Integer> targetIntList = new ArrayList<>();
        for(int i=0;i<target.length();i++){
            targetIntList.add(Integer.parseInt(target.substring(i, i + 1)));
        }

        while (!d.hasThisSequence(targetIntList)){
            boolean printing = (runs % 10000 == 0);

            t2 = System.nanoTime();
            if (printing) System.out.println("Has this sequence: " + (t2 - t1));

            t1 = System.nanoTime();
            d.iterate();
            t2 = System.nanoTime();
            if (printing) System.out.println("Iterate: " + (t2 - t1));
            runs++;

            if (printing) System.out.println("runs = " + runs);

            t1 = System.nanoTime();
        }

    }

    public static void main(String[] args) {
//        runSim(5);
//        runSim(18);
//        runSim(2018);
//        runSim(880751);

        runSimPart2("51589");
        runSimPart2("01245");
        runSimPart2("92510");
        runSimPart2("59414");
        runSimPart2("880751");

    }
}

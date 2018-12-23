import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class StringDifferResult{
    public boolean result;
    public String commonString;
}


public class Day2 {

    public static void main(String[] args){
        Part1();
        Part2();
    }

    static void Part2(){
        InputReader ir = new InputReader(2);

        ArrayList<String> lines = ir.getLines();
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                StringDifferResult res = doStringsDifferByOne(lines.get(i), lines.get(j));
                if (res.result){
                    System.out.println("[Part 2]: " + res.commonString);
                    return;
                }
            }
        }
    }


    static StringDifferResult doStringsDifferByOne(String a, String b){
        StringDifferResult ret = new StringDifferResult();
        boolean differByOne = false;
        int differIdx = -1;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)){
                if (!differByOne){
                    differByOne = true;
                    differIdx = i;
                }
                else{
                    ret.result = false;
                    return ret;
                }
            }
        }
        ret.result = true;
        ret.commonString = a.substring(0, differIdx) + a.substring(differIdx + 1, a.length());
        return ret;
    }








    static void Part1(){
        InputReader ir = new InputReader(2);

        int doubleSum = 0;
        int tripleSum = 0;

        for(String line : ir.getLines()){
            makeHashMap(line);
            if (hasDouble(line))
                doubleSum++;
            if (hasTriple(line))
                tripleSum++;

        }

        System.out.println("Checksum: " + doubleSum +
                " x " + tripleSum + " = " +
                doubleSum * tripleSum);
    }

    static Map<Character, Integer> freqs = new HashMap<Character, Integer>();

    static void makeHashMap(String input){
        freqs.clear();
        for (int i = 0; i < input.length(); i++) {
            int curFreq = freqs.getOrDefault(input.charAt(i), 0);
            freqs.put(input.charAt(i), curFreq + 1);
        }
    }

    static boolean hasDouble(String input){
        return hasNle(input, 2);
    }

    static boolean hasTriple(String input){
        return hasNle(input, 3);
    }

    static boolean hasNle(String input, int n){
        return freqs.containsValue(n);
    }


}

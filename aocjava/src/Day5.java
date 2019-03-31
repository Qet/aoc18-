import InputReader.InputReader;

class SinglePassResult{
    public SinglePassResult(){
        numReactions = 0;
        resultingString = "";
    }
    public String resultingString;
    public int numReactions;
}


public class Day5 {
    public static void main(String[] args) {
        InputReader ir = new InputReader(5);

        String input = ir.getLines().get(0);
        String result = completelyReact(input);

        System.out.println("Result is: " + result);
        System.out.println("[Part 1] Length: " + result.length());

        System.out.println("[Part 2] Shortest reacted length is: " + calcShortestRemovedPolymerLength(input));
    }

    public static int calcShortestRemovedPolymerLength(String input){

        int shortestLength = input.length();
        for(char c = 'a'; c <= 'z'; c++){
            String currentRemovedString = removeLowerAndUpperLetter(input, c);
            String reactedString = completelyReact(currentRemovedString);
            if (reactedString.length() < shortestLength)
                shortestLength = reactedString.length();
        }
        return shortestLength;
    }

    public static String removeLowerAndUpperLetter(String input, char lowerLetter){
        StringBuilder sb = new StringBuilder();
        String lowerInput = input.toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            if (lowerInput.charAt(i) != lowerLetter)
                sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    public static String completelyReact(String input){

        String currentString = input;

        SinglePassResult res = new SinglePassResult();
        do{
            res = scanAndReact(currentString);
            currentString = res.resultingString;
        } while (res.numReactions > 0);

        return currentString;
    }

    public static boolean reacts(String input){
        if (input.length() == 2){
            // aA or Aa: lower case is equal: (a == a), but normal case is not equal (a != A and A != a)
            return (input.toLowerCase().charAt(0) == input.toLowerCase().charAt(1) &&
                input.charAt(0) != input.charAt(1));
        }
        return false;
    }

    public static SinglePassResult scanAndReact(String input){
        SinglePassResult ret = new SinglePassResult();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i < input.length() - 1 && reacts(input.substring(i, i + 2))) {
                i += 1;
                ret.numReactions += 1;
            }
            else {
                sb.append(input.charAt(i));
            }
        }
        ret.resultingString = sb.toString();
        return ret;
    }

}

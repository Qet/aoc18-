import java.util.ArrayList;
import java.util.stream.Collectors;

class Rule {
    public Rule(String encodedRule){
        String[] res = encodedRule.split(" => ");
        from = res[0];
        to = res[1].charAt(0);
    }

    public String from;
    public Character to;

}

class PotLine{

    final int RULE_SIZE = 5;

    public PotLine(String initialState) {
        for (int i=0; i < initialState.length(); i++){
            currentPots.add(initialState.charAt(i));
        }
    }

    public long sumPotsWithPlants(){
        return currentPots.stream().filter(c -> (c == '#')).count();
    }

    @Override
    public String toString() {
        return currentPots.stream().map(Object::toString).collect(Collectors.joining());
    }

    public void applyRules(ArrayList<Rule> rules){
        ensureZeroPadding();
        nextPots = (ArrayList<Character>)currentPots.clone();

        for(Rule r : rules){
            applyOneRule(r);
        }

        currentPots = (ArrayList<Character>)nextPots.clone();
    }

    private void applyOneRule(Rule rule){
        for(int i=0; i < currentPots.size() - RULE_SIZE; i++){
            String currentSubstring = getCurrentSubstring(i);
            if (rule.from.equals(currentSubstring)){
                int potIndex = i + 2;  //In the middle of the 5 pots.
                nextPots.set(potIndex, rule.to);
            }
        }
    }

    private String getCurrentSubstring(int start){
        StringBuilder sb = new StringBuilder();
        currentPots.subList(start, start + RULE_SIZE).stream().forEach(c -> sb.append(c));
        return sb.toString();
    }

    private void ensureZeroPadding(){
        //pad 2 dots at start and end minimum
        while(currentPots.get(0) != '.' || currentPots.get(1) != '.'){
            currentPots.add(0, '.');
            startIndex--;
        }

        while(currentPots.get(currentPots.size() - 2) != '.' ||
                currentPots.get(currentPots.size() - 1) != '.'){
            currentPots.add('.');
        }
    }

    private int startIndex = 0;
    private ArrayList<Character> currentPots = new ArrayList<>();
    private ArrayList<Character> nextPots = new ArrayList<>();

}


public class Day12 {
/*
# = has plant
. = no plant

 */

    void doPart1(){
        InputReader ir = new InputReader(12);
        ArrayList<String> lines = ir.getLines();

        String initialState = lines.get(0).replace("initial state: ", "");

        //Remove the top 2 lines.
        lines.remove(0);
        lines.remove(0);

        ArrayList<Rule> rules = parseRules(lines);

        PotLine potLine = new PotLine(initialState);
        final int GENERATIONS = 20;
        for(int i=0;i <= GENERATIONS ; i++) {
            System.out.printf("%02d: %s%n", i, potLine);
            potLine.applyRules(rules);
        }

        System.out.println("[Part 1]: Num pots with plants after " + GENERATIONS  +
                " generations: " + potLine.sumPotsWithPlants());

    }

    private ArrayList<Rule> parseRules(ArrayList<String> lines) {
        ArrayList<Rule> rules = new ArrayList<>();
        for (String l : lines){
            rules.add(new Rule(l));
        }
        return rules;
    }

    public static void main(String[] args) {
        Day12 day12 = new Day12();
        day12.doPart1();
    }

}

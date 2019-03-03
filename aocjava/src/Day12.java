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

    public int sumPotPlantIndices(){
        int sum = 0;
        for(int i=0;i<currentPots.size();i++){
            int potIndex = i + firstPotIndex;
            if (currentPots.get(i) == '#')
                sum += potIndex;
        }
        return sum;
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
        final int PAD_AMOUNT = 5;
        while(currentPots.subList(0, PAD_AMOUNT).contains('#')){
            currentPots.add(0, '.');
            firstPotIndex--;
        }


        while(currentPots.subList(currentPots.size() - PAD_AMOUNT, currentPots.size() - 1)
            .contains('#')){
            currentPots.add('.');
        }
    }

    public int getFirstPotIndex() {
        return firstPotIndex;
    }

    private int firstPotIndex = 0;
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
            System.out.printf("[d %02d] [st %02d] [pl: %d]: %s%n", i, potLine.getFirstPotIndex(),
                    potLine.sumPotsWithPlants(), potLine);
            if (i != GENERATIONS)
                potLine.applyRules(rules);
        }

        System.out.println("[Part 1]: Sum indices of pots with plants after " + GENERATIONS  +
                " generations: " + potLine.sumPotPlantIndices());

    }


    void doPart2(){
        InputReader ir = new InputReader(12);
        ArrayList<String> lines = ir.getLines();

        String initialState = lines.get(0).replace("initial state: ", "");

        //Remove the top 2 lines.
        lines.remove(0);
        lines.remove(0);

        ArrayList<Rule> rules = parseRules(lines);

        PotLine potLine = new PotLine(initialState);
        final int GENERATIONS = 200;
        for(int i=0;i <= GENERATIONS ; i++) {
            System.out.printf("[d %02d] [st %02d] [pl: %d]: %s%n", i, potLine.getFirstPotIndex(),
                    potLine.sumPotsWithPlants(), potLine);
            if (i != GENERATIONS)
                potLine.applyRules(rules);
        }

        System.out.println("[Part 2]: Sum of indices after 50,000,000,000 generations: " +
                ((long)potLine.sumPotPlantIndices() +
                (50_000_000_000l - (long)GENERATIONS) * (long)potLine.sumPotsWithPlants()));


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
        day12.doPart2();
    }

}

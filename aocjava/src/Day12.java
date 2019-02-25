import java.util.ArrayList;

class Rule {
    public Rule(String encodedRule){
        String[] res = encodedRule.split(" => ");
        from = res[0];
        to = res[1];
    }

    public String from;
    public String to;

}


public class Day12 {
/*
# = has plant
. = no plant

 */

    void readInput(){
        InputReader ir = new InputReader(12);
        ArrayList<String> lines = ir.getLines();

        String initialState = lines.get(0);

        //Remove the top 2 lines.
        lines.remove(0);
        lines.remove(0);

        ArrayList<Rule> rules = new ArrayList<>();
        for (String l : lines){
            rules.add(new Rule(l));
        }



    }

}

import java.util.Scanner;

class MarbleCircle{

    public int playMarble(int value){
        //returns the points for the play

        int points = 0;

        if (marbles.size() <= 1){
            marbles.add(value, marbles.size());
            currentMarbleIndex = marbles.size() - 1;
        }
        else if (value % 23 == 0){
            points += value;  // keep the current marble and add it to your score.
            int extraMarble = calcIndex(currentMarbleIndex, -7);
            points += marbles.get(extraMarble);
            marbles.remove(extraMarble);
            currentMarbleIndex = extraMarble;

        }
        else{
            int insertedPosition = calcIndex(currentMarbleIndex, 2);
            marbles.add(insertedPosition, value);

            currentMarbleIndex = insertedPosition;
        }

        return points;

    }

    public int calcIndex(int currentIndex, int offset){
        int ret = currentIndex + offset;

        if (ret > marbles.size()){
            int wrapAround = (ret / marbles.size()) * marbles.size();
            return ret - wrapAround;
        }
        else if (ret < 0){
            return ret + marbles.size();
        }
        else {
            return ret;
        }
    }

    public int getCurrentMarbleValue(){
        return marbles.get(currentMarbleIndex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<marbles.size();i++){
            String s;
            if (i == currentMarbleIndex){
                s = "(" + marbles.get(i).toString() + ")";
            }
            else{
                s = marbles.get(i).toString();
            }
            sb.append(String.format("%1$-" + 12 + "s", s));
        }
        return sb.toString();
    }

    private int currentMarbleIndex;
    //private LinkedList<Integer> marbles = new LinkedList<>();
    private TowerList marbles = new TowerList(10);

}


public class Day9 {

    //Marble #23:
    // remove and add from score,
    // and remove marble 7 marbles counter-clockwise, and add to score
    // next clockwise marble from that one becomes the current marble.

    //Lowest numbered remaining marble is played each turn
    // placed between 1 and 2 marbles clockwise of the current marble
    // that one becomes the current marble.

    public static void main(String[] args) {

        while(true) {
            MarbleCircle m = new MarbleCircle();
            Scanner s = new Scanner(System.in);

            System.out.print("Players? ");
            int PLAYERS = s.nextInt();
            System.out.println("");
            System.out.print("Last marble? ");
            int LAST_MARBLE = s.nextInt();
            System.out.println("");

            System.out.println("Calc with " + PLAYERS + " players, and " + LAST_MARBLE + " marbles.");

            long[] scores = new long[PLAYERS];
            int currentMarbleValue = 0;
            int currentPlayer = 0;

            for (currentMarbleValue = 0; currentMarbleValue <= LAST_MARBLE; currentMarbleValue++) {
                scores[currentPlayer] += m.playMarble(currentMarbleValue);

                currentPlayer++;
                if (currentPlayer >= PLAYERS)
                    currentPlayer = 0;

                //System.out.println("[" + currentMarbleValue + "]: " + m);
                if (currentMarbleValue % 10000 == 0){
                    System.out.println(Math.round(100d * currentMarbleValue / LAST_MARBLE) + " % complete");

                }
            }

            long maxScore = 0;
            for (int i = 0; i < PLAYERS; i++) {
                if (scores[i] > maxScore)
                    maxScore = scores[i];
            }
            System.out.println("[Part 1]: Best score: " + maxScore);
        }
    }




}

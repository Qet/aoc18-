package Day15;

import InputReader.InputReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class World {

    Grid grid;

    List<Goblin> goblins;
    List<Elf> elves;
    List<Being> beings;

    public World(){
        initGrid();
        goblins = new ArrayList<>();
        elves = new ArrayList<>();
        beings = new ArrayList<>();
    }

    void parseChar(int row, int col, char ch){
        grid.populateGrid(row, col, ch);

        if (ch == 'E') {
            Coords newCoords = new Coords(row, col);
            Elf newElf = new Elf(newCoords);
            elves.add(newElf);
            grid.putBeing(newCoords, newElf);
        }
        else if (ch == 'G') {
            Coords newCoords = new Coords(row, col);
            Goblin newGoblin = new Goblin(newCoords);
            goblins.add(newGoblin);
            grid.putBeing(newCoords, newGoblin);
        }

    }

    void initGrid(){
        InputReader ir = new InputReader(15);
        List<String> lines = ir.getLines();
        grid = new Grid(lines.size(), lines.get(0).length());

        for (int i = 0; i < lines.size(); i++) {
            String curLine = lines.get(i);
            for (int j = 0; j < curLine.length(); j++) {
                char curChar = curLine.charAt(j);
                parseChar(i, j, curChar);
            }
        }

        beings.addAll(elves);
        beings.addAll(goblins);
    }

    void sortBeings(){
        //Sort the beings by reading order.

        Comparator<Being> readingOrder = new Comparator<Being>() {
            @Override
            public int compare(Being o1, Being o2) {
                return grid.coordReadingOrder(o1.getCoords()) -
                        grid.coordReadingOrder(o2.getCoords());
            }
        };

        beings.sort(readingOrder);

    }

    boolean isInRangeOfEnemy(Being being){
        return false;
    }

    void move(Being mover){

    }

    void attack(Being attacker){

    }

    void run(){
        //For units in order:
        //- if not in range of any enemies, then try to move in range to array
        //- if in range, then attack.
        sortBeings();

        for(Being b: beings){
            if (isInRangeOfEnemy(b)){
                move(b);
            }
            else{
                attack(b);
            }
        }


    }
}

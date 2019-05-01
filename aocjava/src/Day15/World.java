package Day15;

import InputReader.InputReader;

import java.util.ArrayList;
import java.util.List;

public class World {

    Grid grid;

    List<Goblin> goblins;
    List<Elf> elves;

    public World(){
        initGrid();
        goblins = new ArrayList<>();
        elves = new ArrayList<>();
    }

    void parseChar(int row, int col, char ch){
        grid.populateGrid(row, col, ch);

        if (ch == 'E') {
            Elf newElf = new Elf(new Coords(row, col));
            elves.add(newElf);
            grid.
        }
        else if (ch == 'G')
            goblins.add(new Goblin(new Coords(row, col)));

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

    }
}

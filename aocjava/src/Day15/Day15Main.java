package Day15;

import InputReader.InputReader;

import java.util.List;

public class Day15Main {

    Grid grid;

    List<Coords> goblins;
    List<Coords> elves;

    class Coords{
        public Coords(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int row;
        public int col;
    }

    void parseChar(int row, int col, char ch){
        grid.populateGrid(row, col, ch);

        if (ch == 'E')
            elves.add(new Coords(row, col));
        else if (ch == 'G')
            goblins.add(new Coords(row, col));

    }
    
    void go(){
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

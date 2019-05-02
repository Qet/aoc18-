package Day15;

import InputReader.InputReader;

import java.util.*;

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
        List<Coords> adj = being.getAdjacentSquares();
        for(Coords sq: adj){
            if (grid.containsEnemy(sq))
                return true;
        }
        return false;
    }

    void move(Being mover){
        /*
        try to move in range:
        - find all targets
        - find all adjacent squares to those targets
        - filter only the reachable squares
        - sort nearest by distance (may be more than one)
        - sort nearest by reading order.
        - choose the first.
         */

        List<Being> enemies = getEnemyList(mover);
        Set<Coords> adjacentSquares = getAdjacentSquares(enemies);

        Optional<Coords> optTargetSquare =
            adjacentSquares.
                stream().
                filter(sq -> isReachable(sq, mover)).
                sorted(distanceComparator).
                sorted(readingOrderComparator).
                findFirst();

        if (optTargetSquare.isPresent()) {
            mover.moveTo(optTargetSquare.get());
        }

    }

    void attack(Being attacker){
        List<Being> enemies = getEnemyList(attacker);

        Optional<Being> optTargetEnemy =
            enemies.
                stream()
                .filter(enemy -> isAdjacent(enemy, attacker))
                .sorted(fewestHitpointsComparator)
                .findFirst();

        if (optTargetEnemy.isPresent()) {
            Being targetEnemy = optTargetEnemy.get();
            targetEnemy.takeDamage(attacker.getPower());
        }
    }

    void run(){
        List<Being> targets;
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

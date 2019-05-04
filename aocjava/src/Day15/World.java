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
            grid.putBeing(newElf, newCoords);
        }
        else if (ch == 'G') {
            Coords newCoords = new Coords(row, col);
            Goblin newGoblin = new Goblin(newCoords);
            goblins.add(newGoblin);
            grid.putBeing(newGoblin, newCoords);
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
                return grid.coordReadingOrder(o1.coords) -
                        grid.coordReadingOrder(o2.coords);
            }
        };

        beings.sort(readingOrder);

    }

    boolean isInRangeOfEnemy(Being being){
        List<Coords> adj = grid.getAdjSquares(being.coords);
        for(Coords sq: adj){
            for(Being adjBeing: grid.getAdjBeings(sq)){
                if (areEnemies(being, adjBeing)){
                    return true;
                }
            }
        }
        return false;
    }

    boolean areEnemies(Being A, Being B){
        return A.getClass() != B.getClass();
    }

    List<? extends Being> getEnemyList(Being fromSource){
        if (fromSource instanceof Elf)
            return goblins;
        return elves;
    }

    Set<Coords> getAdjacentSquares(Being fromSource){

    }

    boolean isReachable(Coords coords, Being mover){

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

        List<? extends Being> enemies = getEnemyList(mover);
        Set<Coords> adjacentSquares = getAdjacentSquares(mover);
        DistanceComparator distanceComparator = new DistanceComparator(mover, grid);
        ReadingOrderComparator readingOrderComparator =
                new ReadingOrderComparator(grid.getNumRows(), grid.getNumCols());

        Optional<Coords> optTargetSquare =
            adjacentSquares.
                stream().
                filter(sq -> isReachable(sq, mover)).
                sorted(distanceComparator).
                sorted(readingOrderComparator).
                findFirst();

        if (optTargetSquare.isPresent()) {
            grid.moveBeing(mover, optTargetSquare.get());
        }

    }

    void moveTo(Being mover, Coords dest){
        mover.coords = dest;
    }

    boolean isAdjacent(Being A, Being B){

    }

    void attack(Being attacker){
        List<Being> enemies = getEnemyList(attacker);
        FewestHitpointsComparator fewestHitpointsComparator = new FewestHitpointsComparator();

        Optional<Being> optTargetEnemy =
            enemies.
                stream()
                .filter(enemy -> isAdjacent(enemy, attacker))
                .sorted(fewestHitpointsComparator)
                .findFirst();

        if (optTargetEnemy.isPresent()) {
            Being targetEnemy = optTargetEnemy.get();
            targetEnemy.takeDamage(attacker.attackPower);
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

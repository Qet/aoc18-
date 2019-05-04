package Day15;

public class TerrainSquare {
/*
Represents a single square of terrain.
 */

    private boolean passableTerrain;

    public Being occupant = null;

    public TerrainSquare(){ }

    public void initialise(char ch){
        switch (ch){
            case '.':
                passableTerrain = true;
                break;
            case '#':
                passableTerrain = false;
                break;
            default: //gob or elf, but still passable. 
                passableTerrain = true;
                break;
        }
    }

    public boolean isOccupied(){
        return occupant != null;
    }

    public boolean isPassable(){
        return !isOccupied() && passableTerrain;
    }
}

package Day15;

public class TerrainSquare {
/*
Represents a single square of terrain.
 */

    private boolean passableTerrain;
    private Being occupant = null;

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

    public void setOccupant(Being occupant) {
        this.occupant = occupant;
    }

    public boolean isOccupied(){
        return occupant != null;
    }

    public boolean isPassable(){
        return !isOccupied() && passableTerrain;
    }
}

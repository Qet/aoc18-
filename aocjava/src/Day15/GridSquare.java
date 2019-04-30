package Day15;

public class GridSquare {

    private boolean occupied;
    private boolean passableTerrain;

    public GridSquare(){ }

    public void initialise(char ch){
        occupied = false;
        switch (ch){
            case '.':
                passableTerrain = true;
                break;
            case '#':
                passableTerrain = false;
                break;
            default: //gob or elf
                passableTerrain = true;
                occupied = true;
                break;
        }
    }

    public void setOccupied(boolean value){
        occupied = value;
    }

    public boolean isPassable(){
        return !occupied && passableTerrain;
    }
}

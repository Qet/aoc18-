package Day15;

public class Being {
    public Coords coords;

    public int HP;
    public int attackPower;

    protected String name = "";

    public void takeDamage(int damage){
        HP -= damage;
    }

    public Being(Coords coords) {
        this.coords = coords;
        HP = 200;
        attackPower = 3;
    }

    @Override
    public String toString() {
        return name + " @ " + "(" + coords.row + ", " + coords.col + ")";
    }
}
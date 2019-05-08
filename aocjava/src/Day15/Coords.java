package Day15;

class Coords {
    public Coords(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row;
    public int col;

    public boolean isAdjacent(Coords other){
        return  Math.abs(row - other.row) <= 1 &&
                Math.abs(col - other.col) <= 1;
    }

    private boolean equals(Coords other){
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return 21 * row + col;
    }

    @Override
    public boolean equals(Object obj) {
        return equals((Coords)obj);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

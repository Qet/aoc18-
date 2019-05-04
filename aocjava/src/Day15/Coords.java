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

    public boolean equals(Coords other){
        return this.row == other.row && this.col == other.col;
    }
}

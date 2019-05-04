package Day15;

import java.util.Comparator;

class ReadingOrderComparator implements Comparator<Coords> {

    private int rows, cols;

    public ReadingOrderComparator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int compare(Coords o1, Coords o2) {
        return (o1.row - o2.row) * 1000 + o1.col - o2.col;
    }
}

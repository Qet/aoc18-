package Day13;

class Cart implements Comparable{

    public Cart(Square curSq, Square entrySq){
        this.currentSquare = curSq;
        this.entrySquare = entrySq;
        this.facing = facing;
    }

    private void incrementNextTurn(){
        nextTurn++;
        if (nextTurn > 3){
            nextTurn = 1;
        }
    }

    int nextTurn = 1;

    //where the cart is
    Square currentSquare;

    //where the cart came from
    Square entrySquare;

    public int row;
    public int col;

    public Directions facing;

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Cart))
            throw new IllegalArgumentException("Expected to compare to a cart.");

        Cart c2 = (Cart)o;

        return Mine.SIZE * (this.row - c2.row) +
                (this.col - c2.col);

    }
}

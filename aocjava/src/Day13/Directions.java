package Day13;

enum Directions{
    North,
    East,
    South,
    West;

    Directions nextClockwiseDir(Directions currentDir){
        Directions[] values = Directions.values();
        int currentValue = currentDir.ordinal();
        int ret;
        if (currentValue == values.length - 1){
            ret = 0;
        }
        else{
            ret = currentValue + 1;
        }
        return values[ret];
    }
}

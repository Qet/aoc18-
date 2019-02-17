import java.util.Random;

public class Day9Test {
    public static void main(String[] args) {

        TowerList t = new TowerList(3);
        Random r = new Random();
        t.add(0, 0);
        for(int i=1;i<10;i++){
            t.add(r.nextInt(i), i);
        }

    }
}

import java.util.LinkedList;

public class Day9Test {
    public static void main(String[] args) {
        LinkedList<Integer> l = new LinkedList<>();
        l.add(1);
        l.add(2);
        l.add(3);

        l.add(3, 55);

        System.out.println(l);
    }
}

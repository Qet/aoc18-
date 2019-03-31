import InputReader.InputReader;

import java.util.ArrayList;

class Claim{
    public int id;
    public int left;
    public int top;
    public int width;
    public int height;
}

public class Day3 {

    public static void main(String[] args)
    {
        InputReader ir = new InputReader(3);
        ArrayList<Claim> claims = new ArrayList<>();
        for(String line : ir.getLines()){
            claims.add(parseLine(line));
        }

        initialiseFabric();
        fillFabric(claims);
        System.out.println("[Part 1] Overlapping squares: " + countOverlapSquares());

        System.out.println("[Part 2] Non-overlapping claim: " + findNonOverlapSquare(claims));
    }

    private static int[][] fabric = new int[1000][1000];

    private static final int FABRIC_SIZE = 1000;

    private static void initialiseFabric(){
        for (int i = 0; i < FABRIC_SIZE; i++) {
            for (int j = 0; j < FABRIC_SIZE; j++) {
                fabric[i][j] = 0;
            }
        }
    }

    private static void fillFabric(ArrayList<Claim> claims){
        for(Claim c : claims){
            for (int i = c.left; i < c.left + c.width; i++) {
                for (int j = c.top; j < c.top + c.height; j++) {
                    fabric[i][j]++;
                }
            }
        }
    }

    private static int countOverlapSquares(){
        int overlapCount = 0;
        for (int i = 0; i < FABRIC_SIZE; i++) {
            for (int j = 0; j < FABRIC_SIZE; j++) {
                if (fabric[i][j] >= 2)
                    overlapCount++;
            }
        }
        return overlapCount;
    }


    private static boolean doesClaimOverlap(Claim c){
        for (int i = c.left; i < c.left + c.width; i++) {
            for (int j = c.top; j < c.top + c.height; j++) {
                if (fabric[i][j] >= 2){
                    return true;
                }
            }
        }
        return false;
    }

    private static int findNonOverlapSquare(ArrayList<Claim> claims){
        for(Claim c : claims){
            if (!doesClaimOverlap(c))
                return c.id;
        }
        return -1;
    }

    private static Claim parseLine(String line){
        //#12 @ 763,913: 18x12
        line = line.replace(" ", "");
        line = line.replace('@', '~');
        line = line.replace(',', '~');
        line = line.replace('x', '~');
        line = line.replace("#", "");
        line = line.replace(':', '~');

        //System.out.println(line);

        String[] items = line.split("~");
        Claim ret = new Claim();
        ret.id = Integer.parseInt(items[0]);
        ret.left = Integer.parseInt(items[1]);
        ret.top = Integer.parseInt(items[2]);
        ret.width = Integer.parseInt(items[3]);
        ret.height = Integer.parseInt(items[4]);
        return ret;
     }



}


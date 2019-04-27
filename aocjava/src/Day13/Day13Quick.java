package Day13;

import InputReader.InputReader;

import java.util.ArrayList;

public final class Day13Quick {

    static final int SIZE = 200;
    static char[][] rawData = new char[SIZE][SIZE];
    static char[][] carts = new char[SIZE][SIZE];

    static int maxRows;
    static int maxCols;

    static void loadData(){
        InputReader ir = new InputReader(13);
        ArrayList<String> lines = ir.getLines();
        maxRows = lines.size();
        maxCols = lines.get(0).length();
        for (int row = 0; row < lines.size(); row++) {
            String curLine = lines.get(row);
            for (int col = 0; col < curLine.length(); col++) {
                char curChar = curLine.charAt(col);
                rawData[row][col] = curChar;
            }
        }
    }

    static void printData(){
        for(int row = 0; row < maxRows; row++){
            for(int col = 0; col < maxCols; col++){
                System.out.print(rawData[row][col]);
            }
            System.out.println();
        }
    }

    static void moveCarts() {
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                char c = rawData[row][col];

                switch (c){
                    case '>':
                    case '<':
                    case '^':
                    case 'v':
                }
            }
        }
    }




    public static void main(String[] args) {
        loadData();
        printData();
    }
}



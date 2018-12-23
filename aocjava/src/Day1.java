import java.util.ArrayList;


public class Day1 {
    public static void main(String[] args){
        InputReader ir = new InputReader(1);
        PopulateDataList(ir);
        DoPart1();
        DoPart2();

    }

    public static void DoPart1(){
        int result = AddLines();
        System.out.println("[Part 1] Sum of frequencies is: " + result);
    }

    private static void DoPart2(){
        int currentSum = 0;
        ArrayList<Integer> previousSums = new ArrayList<Integer>();
        previousSums.add(0);
        int loops = 0;
        while(true){
            for (Integer i :
                    data) {
                currentSum += i;
                if (previousSums.contains(currentSum)){
                    System.out.println("[Part 2] " + currentSum + " was reached twice, in " + loops + " loops of the input file.");
                    return;
                }
                previousSums.add(currentSum);
            }
            loops++;
        }
    }

    private static void DoPart2_Fast(){
        int currentSum = 0;
        int[] previousSums = new int[1000000];
        int maxPrevSums = 0;
        previousSums[maxPrevSums++] = 0;
        int loops = 0;
        while(true){
            for (Integer i :
                    data) {
                currentSum += i;
                boolean found = false;
                for (int j = 0; j < maxPrevSums; j++) {
                    if (previousSums[j] == currentSum){
                        found = true;
                        break;
                    }
                }
                if (found){
                    System.out.println("[Part 2] " + currentSum + " was reached twice, in " + loops + " loops of the input file.");
                    return;
                }
                previousSums[maxPrevSums++] = currentSum;
            }
            loops++;
        }
    }


    private static void PopulateDataList(InputReader inputReader){
        ArrayList<String> lines =  inputReader.getLines();
        for (String item : lines) {
            data.add(Integer.parseInt(item));
        }
    }

    private static int AddLines(){
        int result = 0;
        for (Integer i :
                data) {
            result += i;
        }
        return result;
    }

    private static ArrayList<Integer> data = new ArrayList<Integer>();
}

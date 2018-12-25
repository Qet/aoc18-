import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

enum Action{
    BEGIN,
    SLEEP,
    WAKE
}

class Line{
    public Line(){}
    public Line(Date date, Action action, int ID, int min){
        this.date = date;
        this.action = action;
        this.ID = ID;
        this.min = min;
    }
    public Date date;
    public Action action;
    public int ID;
    public int min;
}

class Guard{
    public int ID;
    public int maxSleepSize;
    public int maxSleepMinute;
}

class SleepResult{
    public SleepResult(){
        ID = 0;
        sleepMinsCount = 0;
        for (int i = 0; i < 60; i++) {
            sleepCount[i] = 0;
        }
    }
    public int ID;
    public int sleepMinsCount;
    public int[] sleepCount = new int[60];
}

public class Day4 {
    public static void main(String[] args){
        InputReader ir = new InputReader(4);
        ArrayList<Line> lines = new ArrayList<>();

        for(String line : ir.getLines()){
            lines.add(parseLine(line));
        }
        sortLines(lines);
        Map<Integer, SleepResult> sleepResultMap = calcAllSleepResults(lines);

        for (Map.Entry<Integer, SleepResult> entry : sleepResultMap.entrySet()){
            System.out.println("ID " + entry.getKey() + " Mins " + entry.getValue());
        }

        SleepResult result = findSleepiestGuard(sleepResultMap);

        System.out.println("[Part 1] Guard " + result.ID + " spends " + result.sleepMinsCount + " mins asleep.");
        int sleepiestMin = findSleepiestMinute(result);
        System.out.println("Sleeps the longest during minute #" + sleepiestMin);
        System.out.println("ID x min = " + result.ID * sleepiestMin);

        Guard g = findSleepiestMinuteGuard(sleepResultMap);
        System.out.println("[Part 2] Guard " + g.ID  + " spends " +
                g.maxSleepSize + " minutes asleep during minute #" +
                g.maxSleepMinute);
        System.out.println("ID x minute# = " + g.ID  * g.maxSleepMinute);
    }

    public static void sortLines(ArrayList<Line> lines){
        lines.sort((Line a, Line b) -> a.date.compareTo(b.date));
    }

    public static int findSleepiestMinute(SleepResult sleepResult){
        int maxIndex = 0;
        int maxValue = 0;
        for (int i = 0; i < sleepResult.sleepCount.length; i++) {
            if (sleepResult.sleepCount[i] > maxValue){
                maxIndex = i;
                maxValue = sleepResult.sleepCount[i];
            }
        }
        return maxIndex;
    }

    public static Guard findSleepiestMinuteGuard(Map<Integer, SleepResult> sleepResultsMap){
        Guard ret = new Guard();
        ret.ID = 0;
        ret.maxSleepSize = 0;
        ret.maxSleepMinute = 0;

        for(Map.Entry<Integer, SleepResult> entry : sleepResultsMap.entrySet()){
            int[] arr = entry.getValue().sleepCount;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > ret.maxSleepSize){
                    ret.maxSleepMinute = i;
                    ret.maxSleepSize = arr[i];
                    ret.ID = entry.getKey();
                }
            }
        }
        return ret;
    }

    public static SleepResult findSleepiestGuard(Map<Integer, SleepResult> sleepResultsMap){
        int maxMinsAsleep = 0;
        int guardID = 0;
        for (Map.Entry<Integer, SleepResult> entry : sleepResultsMap.entrySet()) {
            if (entry.getValue().sleepMinsCount > maxMinsAsleep){
                guardID = entry.getKey();
                maxMinsAsleep = entry.getValue().sleepMinsCount;
            }
        }

        return sleepResultsMap.get(guardID);
    }


    public static SleepResult calcSleepResult(int startSleepMin, int endSleepMin, int ID, SleepResult previousResult){
        int timeSlept = endSleepMin - startSleepMin;

        previousResult.ID = ID;
        previousResult.sleepMinsCount += timeSlept;

        for (int i = startSleepMin; i < endSleepMin; i++) {
            previousResult.sleepCount[i]++;
        }

        return previousResult;
    }

    public static Map<Integer, SleepResult> calcAllSleepResults(ArrayList<Line> lines){
        int startSleepMin = 0;
        Map<Integer, SleepResult> ret = new HashMap<>();

        int curID = 0;
        for(Line l : lines){
            if (l.action == Action.BEGIN) {
                curID = l.ID;
            }

            else if (l.action == Action.SLEEP) {
                startSleepMin = l.min;
            }

            else if (l.action == Action.WAKE) {
                ret.put(curID, calcSleepResult(startSleepMin, l.min, curID,
                        ret.getOrDefault(curID, new SleepResult())));
            }
        }
        return ret;
    }

    public static Line parseLine(String line){
        //[1518-09-26 00:35] falls asleep
        //[1518-07-15 00:52] wakes up
        //[1518-06-08 00:36] wakes up
        //[1518-04-23 23:47]Guard #607 begins shift

        Line ret = new Line();

        if (line.contains("falls asleep"))
            ret.action = Action.SLEEP;
        else if (line.contains("wakes up"))
            ret.action = Action.WAKE;
        else if (line.contains("begins shift"))
            ret.action = Action.BEGIN;

        line = line.replace("[15", "20");
        line = line.replace("]", "~");
        line = line.replace(" #", "~");
        line = line.replace(" begins", "~");

        String[] tokens = line.split("~");
        try{
            ret.date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(tokens[0]);
        }
        catch (ParseException ex){
            System.out.println("Parse exception!: " + ex);
        }

        if (ret.action == Action.BEGIN)
            ret.ID = Integer.parseInt(tokens[2]);

        ret.min = ret.date.getMinutes();
        System.out.println(ret.min);

        return ret;

    }
}

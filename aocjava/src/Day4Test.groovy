class Day4Test extends groovy.util.GroovyTestCase {
    void testParseLine() {

    }

    void testFindSleepiestGuard(){
        Map<Integer, Integer> data = new HashMap<>()
        data.put(1, 5);
        data.put(2, 4);
        data.put(3, 6);
        SleepResult res = Day4.findSleepiestGuard(data);
        assertEquals(3, res.ID);
        assertEquals(6, res.sleepMinsCount);
        assertEquals(18, res.multResult);
    }

    void testCountMinsAsleep(){
        ArrayList<Line> data = new ArrayList<>();

        data.add(new Line(new Date(2000, 1, 1, 1, 1),
                    Action.BEGIN, 1, 1));
        data.add(new Line(new Date(2000, 1, 1, 1, 2),
                Action.SLEEP, 1, 2));
        data.add(new Line(new Date(2000, 1, 1, 1, 3),
                Action.WAKE, 1, 3));

        Map<Integer, Integer> res = Day4.countMinsAsleep(data);
        assertEquals(1, res.get(1));

    }

    void testSortLines(){
        ArrayList<Line> data = new ArrayList<>();

        Line line1 = new Line(new Date(100, 3, 1, 1, 1),
                Action.BEGIN, 1, 1);
        Line line2 = new Line(new Date(100, 1, 1, 1, 2),
                Action.SLEEP, 1, 2);
        Line line3 = new Line(new Date(100, 2, 1, 1, 3),
                Action.WAKE, 1, 3);

        data.add(line1);
        data.add(line2);
        data.add(line3);

        Day4.sortLines(data)

        assertEquals(line2.date, data.get(0).date);
        assertEquals(line3.date, data.get(1).date);
        assertEquals(line1.date, data.get(2).date);


    }
}

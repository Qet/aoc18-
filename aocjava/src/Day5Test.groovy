class Day5Test extends GroovyTestCase {
    void testReacts() {
        assertFalse(Day5.reacts("aa"));
        assertFalse(Day5.reacts("AA"));
        assertTrue(Day5.reacts("aA"));
        assertTrue(Day5.reacts("Aa"));
        assertFalse(Day5.reacts("ab"));
        assertFalse(Day5.reacts("BA"));

    }

    void testScanAndReact(){

        SinglePassResult res = Day5.scanAndReact("abcCcdeFfFgghh");
        assertEquals(2, res.numReactions);
        assertEquals("abcdeFgghh", res.resultingString);

        res = Day5.scanAndReact("aabbcC");
        assertEquals(1, res.numReactions);
        assertEquals("aabb", res.resultingString);


    }

    void testRemoveLowerAndUpper(){

        char arg = 'a';
        assertEquals("bB", Day5.removeLowerAndUpperLetter("aAbB", arg));
        assertEquals("bBcd", Day5.removeLowerAndUpperLetter("abABcd", arg));

    }

}

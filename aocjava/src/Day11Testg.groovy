class Day11Testg extends GroovyTestCase{

    void testUnitCell(){
        Grid g = new Grid(8, 5);
        assertEquals(4, g.unitCellPower(3, 5))
   }
    void testCell(){
        Grid g = new Grid(18, 300)
        assertEquals(29, g.fuelCellPower(33, 45))

        g = new Grid(42, 300)
        assertEquals(30, g.fuelCellPower(21, 61))

    }

}

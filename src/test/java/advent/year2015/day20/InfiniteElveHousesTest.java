/* Licensed under Apache-2.0 */
package advent.year2015.day20;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InfiniteElveHousesTest {

    @Test
    public void getLowestHouseNumber() {
        assertEquals(8, InfiniteElveHouses.getLowestHouseNumber(130));
        assertEquals(6, InfiniteElveHouses.getLowestHouseNumber(120));
        assertEquals(4, InfiniteElveHouses.getLowestHouseNumber(70));
        assertEquals(786240, InfiniteElveHouses.getLowestHouseNumber(34000000));
    }

    @Test
    public void getLowestHouseNumberAdjusted() {
        assertEquals(8, InfiniteElveHouses.getLowestHouseNumberAdjusted(133));
        assertEquals(6, InfiniteElveHouses.getLowestHouseNumberAdjusted(132));
        assertEquals(4, InfiniteElveHouses.getLowestHouseNumberAdjusted(70));
        assertEquals(831600, InfiniteElveHouses.getLowestHouseNumberAdjusted(34000000));
    }
}
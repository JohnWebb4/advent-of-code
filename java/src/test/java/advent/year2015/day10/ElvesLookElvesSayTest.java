/* Licensed under Apache-2.0 */
package advent.year2015.day10;

import static org.junit.Assert.*;

import org.junit.Test;

public class ElvesLookElvesSayTest {

    @Test
    public void playLookSayNTimes() {
        assertEquals("11", ElvesLookElvesSay.playLookSayNTimes("1", 1));
        assertEquals("21", ElvesLookElvesSay.playLookSayNTimes("1", 2));
        assertEquals("1211", ElvesLookElvesSay.playLookSayNTimes("1", 3));
        assertEquals("111221", ElvesLookElvesSay.playLookSayNTimes("1", 4));
        assertEquals("312211", ElvesLookElvesSay.playLookSayNTimes("1", 5));
        assertEquals(329356, ElvesLookElvesSay.getLookSayNTimesLength("3113322113", 40));
//        assertEquals(4666278, ElvesLookElvesSay.getLookSayNTimesLength("3113322113", 50));
    }
}
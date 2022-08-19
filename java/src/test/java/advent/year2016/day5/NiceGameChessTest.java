/* Licensed under Apache-2.0 */
package advent.year2016.day5;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NiceGameChessTest {

    @Test
    public void getDoorPassword() {
        assertEquals("18f47a30", NiceGameChess.getDoorPassword("abc"));
        assertEquals("4543c154", NiceGameChess.getDoorPassword("ojvtpuvg"));
    }

    @Test
    public void getDoorPasswordShifting() {
        assertEquals("05ace8e3", NiceGameChess.getDoorPasswordShifting("abc"));
        assertEquals("1050cbbd", NiceGameChess.getDoorPasswordShifting("ojvtpuvg"));
    }
}
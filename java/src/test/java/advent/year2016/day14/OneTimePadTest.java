/* Licensed under Apache-2.0 */
package advent.year2016.day14;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OneTimePadTest {

    @Test
    public void getIndexOfNthKey() {
        assertEquals(22728, OneTimePad.getIndexOfNthKey("abc", 64));
        assertEquals(25427, OneTimePad.getIndexOfNthKey("yjdafjpo", 64));
    }

    @Test
    public void getIndexONthKeyStretched() {
        assertEquals(22551, OneTimePad.getIndexOfNthKeyStretched("abc", 64, 2016));
        assertEquals(22045, OneTimePad.getIndexOfNthKeyStretched("yjdafjpo", 65, 2016));
    }
}
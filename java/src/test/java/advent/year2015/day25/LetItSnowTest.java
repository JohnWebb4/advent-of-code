/* Licensed under Apache-2.0 */
package advent.year2015.day25;

import static org.junit.Assert.*;

import org.junit.Test;

public class LetItSnowTest {

    @Test
    public void getCodeAtPosition() {
        assertEquals(27995004, LetItSnow.getCodeAtPosition(6, 6));
        assertEquals(2650453, LetItSnow.getCodeAtPosition(2978, 3083));
    }
}
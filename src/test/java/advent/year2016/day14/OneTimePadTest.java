package advent.year2016.day14;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OneTimePadTest {

    @Test
    public void getIndexOfNthKey() {
        assertEquals(22728, OneTimePad.getIndexOfNthKey("abc", 64));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2015.day4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdealStockingStufferTest {
    @Test
    public void getDecimalToHashZeros_609043() {
        assertEquals(609043, IdealStockingStuffer.getLongToHashZeros("abcdef", 5));
    }

    @Test
    public void getDecimalToHashZeros_1048970() {
        assertEquals(1048970, IdealStockingStuffer.getLongToHashZeros("pqrstuv", 5));
    }

    @Test
    public void getDecimalToHashZeros_346386() {
        assertEquals(346386, IdealStockingStuffer.getLongToHashZeros("iwrupvqb", 5));
    }

    @Test
    public void getDecimalToHashZeros_9958218() {
        assertEquals(9958218, IdealStockingStuffer.getLongToHashZeros("iwrupvqb", 6));
    }
}
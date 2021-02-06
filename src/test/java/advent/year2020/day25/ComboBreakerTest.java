/* Licensed under Apache-2.0 */
package advent.year2020.day25;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComboBreakerTest {
    @Test
    public void getEncryptionKey_14897079() {
        assertEquals(14897079, ComboBreaker.getEncryptionKey(5764801, 17807724, 7));
    }

    @Test
    public void getEncryptionKey_3803729() {
        assertEquals(3803729, ComboBreaker.getEncryptionKey(2959251, 4542595, 7));
    }
}
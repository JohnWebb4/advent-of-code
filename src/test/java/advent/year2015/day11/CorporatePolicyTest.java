/* Licensed under Apache-2.0 */
package advent.year2015.day11;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CorporatePolicyTest {
    @Test
    public void getNextPassword() {
        assertEquals("abcdffaa", CorporatePolicy.getNextPassword("abcdefgh"));
//        assertEquals("ghjaabcc", CorporatePolicy.getNextPassword("ghijklmn"));
//        assertEquals("vzbxxyzz", CorporatePolicy.getNextPassword("vzbxkghb"));
//       assertEquals("vzcaabcc", CorporatePolicy.getNextPassword("vzbxxyzz"));

    }
}

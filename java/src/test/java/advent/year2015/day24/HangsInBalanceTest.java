/* Licensed under Apache-2.0 */
package advent.year2015.day24;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HangsInBalanceTest {

    @Test
    public void getIdealQuantamEntanglement() {
        assertEquals(99, HangsInBalance.getIdealQuantamEntanglementForGroups("1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "10\n" +
                "11", 3));

        assertEquals(10723906903L, HangsInBalance.getIdealQuantamEntanglementForGroups("1\n" +
                "2\n" +
                "3\n" +
                "5\n" +
                "7\n" +
                "13\n" +
                "17\n" +
                "19\n" +
                "23\n" +
                "29\n" +
                "31\n" +
                "37\n" +
                "41\n" +
                "43\n" +
                "53\n" +
                "59\n" +
                "61\n" +
                "67\n" +
                "71\n" +
                "73\n" +
                "79\n" +
                "83\n" +
                "89\n" +
                "97\n" +
                "101\n" +
                "103\n" +
                "107\n" +
                "109\n" +
                "113", 3));

        assertEquals(74850409L, HangsInBalance.getIdealQuantamEntanglementForGroups("1\n" +
                "2\n" +
                "3\n" +
                "5\n" +
                "7\n" +
                "13\n" +
                "17\n" +
                "19\n" +
                "23\n" +
                "29\n" +
                "31\n" +
                "37\n" +
                "41\n" +
                "43\n" +
                "53\n" +
                "59\n" +
                "61\n" +
                "67\n" +
                "71\n" +
                "73\n" +
                "79\n" +
                "83\n" +
                "89\n" +
                "97\n" +
                "101\n" +
                "103\n" +
                "107\n" +
                "109\n" +
                "113", 4));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2020.day23;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class CrabCupsTest {
    public static Long test1;
    public static Long input;

    @BeforeClass
    public static void initialize() {
        test1 = 389125467l;
        input = 157623984l;
    }

    @Test
    public void getStateAfterXMoves_92658374() {
        assertEquals(92658374, CrabCups.getStateAfterXMoves(10, test1));
    }

    @Test
    public void getStateAfterXMoves_67384529() {
        assertEquals(67384529, CrabCups.getStateAfterXMoves(100, test1));
    }

    @Test
    public void getStateAfterXMoves_1() {
        assertEquals(58427369, CrabCups.getStateAfterXMoves(100, input));
    }

//    @Test
//    public void getProductOfStarCupsAfterXMoves_149245887792() {
//        assertEquals(149245887792l, CrabCups.getProductOfStarCupsAfterXMoves(10000000, test1));
//    }
//
//    @Test
//    public void getProductOfStarCupsAfterXMoves_1() {
//        assertEquals(111057672960l, CrabCups.getProductOfStarCupsAfterXMoves(10000000, input));
//    }
}
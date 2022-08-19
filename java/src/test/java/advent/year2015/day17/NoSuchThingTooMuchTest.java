/* Licensed under Apache-2.0 */
package advent.year2015.day17;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class NoSuchThingTooMuchTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "20\n15\n10\n5\n5";
        input = "33\n" +
                "14\n" +
                "18\n" +
                "20\n" +
                "45\n" +
                "35\n" +
                "16\n" +
                "35\n" +
                "1\n" +
                "13\n" +
                "18\n" +
                "13\n" +
                "50\n" +
                "44\n" +
                "48\n" +
                "6\n" +
                "24\n" +
                "41\n" +
                "30\n" +
                "42";
    }

    @Test
    public void getContainerCombinations() {
        assertEquals(4, NoSuchThingTooMuch.getCountContainerCombinations(25, test1));
        assertEquals(1304, NoSuchThingTooMuch.getCountContainerCombinations(150, input));
    }

    @Test
    public void getMinCountContainerCombinations() {
        assertEquals(3, NoSuchThingTooMuch.getMinCountContainerCombinations(25, test1));
        assertEquals(18, NoSuchThingTooMuch.getMinCountContainerCombinations(150, input));
    }
}
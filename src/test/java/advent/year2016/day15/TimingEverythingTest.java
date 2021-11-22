/* Licensed under Apache-2.0 */
package advent.year2016.day15;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TimingEverythingTest {
    public static String test1;
    public static String input;
    public static String input2;

    @BeforeClass
    public static void initialize() {
        test1 = "Disc #1 has 5 positions; at time=0, it is at position 4.\n" +
                "Disc #2 has 2 positions; at time=0, it is at position 1.";

        input = "Disc #1 has 7 positions; at time=0, it is at position 0.\n" +
                "Disc #2 has 13 positions; at time=0, it is at position 0.\n" +
                "Disc #3 has 3 positions; at time=0, it is at position 2.\n" +
                "Disc #4 has 5 positions; at time=0, it is at position 2.\n" +
                "Disc #5 has 17 positions; at time=0, it is at position 0.\n" +
                "Disc #6 has 19 positions; at time=0, it is at position 7.";
        input2 = "Disc #1 has 7 positions; at time=0, it is at position 0.\n" +
                "Disc #2 has 13 positions; at time=0, it is at position 0.\n" +
                "Disc #3 has 3 positions; at time=0, it is at position 2.\n" +
                "Disc #4 has 5 positions; at time=0, it is at position 2.\n" +
                "Disc #5 has 17 positions; at time=0, it is at position 0.\n" +
                "Disc #6 has 19 positions; at time=0, it is at position 7.\n" +
                "Disc #7 has 11 positions; at time=0, it is at position 0.\\n";
    }

    @Test
    public void getFirstTimeToPassCapsule() {
        assertEquals(5, TimingEverything.getFirstTimeToPassCapsule(test1));
        assertEquals(121834, TimingEverything.getFirstTimeToPassCapsule(input));
        assertEquals(3208099, TimingEverything.getFirstTimeToPassCapsule(input2));
    }
}
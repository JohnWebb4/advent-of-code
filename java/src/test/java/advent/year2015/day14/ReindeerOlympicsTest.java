/* Licensed under Apache-2.0 */
package advent.year2015.day14;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReindeerOlympicsTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\n" +
                "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.";
        input = "Vixen can fly 19 km/s for 7 seconds, but then must rest for 124 seconds.\n" +
                "Rudolph can fly 3 km/s for 15 seconds, but then must rest for 28 seconds.\n" +
                "Donner can fly 19 km/s for 9 seconds, but then must rest for 164 seconds.\n" +
                "Blitzen can fly 19 km/s for 9 seconds, but then must rest for 158 seconds.\n" +
                "Comet can fly 13 km/s for 7 seconds, but then must rest for 82 seconds.\n" +
                "Cupid can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.\n" +
                "Dasher can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.\n" +
                "Dancer can fly 3 km/s for 16 seconds, but then must rest for 37 seconds.\n" +
                "Prancer can fly 25 km/s for 6 seconds, but then must rest for 143 seconds.";
    }

    @Test
    public void getFarthestDistanceReindeerTraveled() {
        assertEquals(1120, ReindeerOlympics.getFarthestDistanceReindeerTraveled(test1, 1000));
        assertEquals(2660, ReindeerOlympics.getFarthestDistanceReindeerTraveled(input, 2503));
    }

    @Test
    public void getMostPointsAfterDuration() {
        assertEquals(689, ReindeerOlympics.getMostPointsAfterDuration(test1, 1000));
        assertEquals(1256, ReindeerOlympics.getMostPointsAfterDuration(input, 2503));
    }
}
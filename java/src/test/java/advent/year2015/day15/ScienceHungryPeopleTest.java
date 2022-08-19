/* Licensed under Apache-2.0 */
package advent.year2015.day15;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class ScienceHungryPeopleTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            test1 = "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n" +
                    "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3";
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day15/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getMaximumCookieScore() {
        assertEquals(62842880, ScienceHungryPeople.getMaximumCookieScore(test1, 100));
//        assertEquals(18965440, ScienceHungryPeople.getMaximumCookieScore(input, 100));
    }

    @Test
    public void getMaximumCookieScoreCalories() {
        assertEquals(57600000, ScienceHungryPeople.getMaximumCookieScoreCalories(test1, 100, 500));
//        assertEquals(15862900, ScienceHungryPeople.getMaximumCookieScoreCalories(input, 100, 500));
    }
}
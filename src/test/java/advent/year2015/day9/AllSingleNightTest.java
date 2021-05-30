/* Licensed under Apache-2.0 */
package advent.year2015.day9;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class AllSingleNightTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            test1 = "London to Dublin = 464\n" +
                    "London to Belfast = 518\n" +
                    "Dublin to Belfast = 141";
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day9/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getShortestDistance() {
        assertEquals(605, AllSingleNight.getShortestDistance(test1));
        assertEquals(117, AllSingleNight.getShortestDistance(input));
    }
}
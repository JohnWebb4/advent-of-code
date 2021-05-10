/* Licensed under Apache-2.0 */
package advent.year2020.day12;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class RainRiskTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "F10\n" +
                "N3\n" +
                "F7\n" +
                "R90\n" +
                "F11";

        try {
            input = new String(Files.readAllBytes(Path.of("./src/test/java/advent/year2020/day12/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getManhattanDistanceFromStart_25() {
        assertEquals(25, RainRisk.getManhattanDistanceFromStart(test1));
    }

    @Test
    public void getManhattanDistanceFromStart_923() {
        assertEquals(923, RainRisk.getManhattanDistanceFromStart(input));
    }

    @Test
    public void getManhattanDistanceFromStartWithWaypoint_286() {
        assertEquals(286, RainRisk.getManhattanDistanceFromStartWithWaypoint(test1));
    }

    @Test
    public void getManhattanDistanceFromStartWithWaypoint_1() {
        assertEquals(24769, RainRisk.getManhattanDistanceFromStartWithWaypoint(input));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2020.day11;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class SeatingSystemTest {
    public static String test1;
    public static String input;

    @Before
    public void initialize() {
        test1 = "L.LL.LL.LL\n" +
                "LLLLLLL.LL\n" +
                "L.L.L..L..\n" +
                "LLLL.LL.LL\n" +
                "L.LL.LL.LL\n" +
                "L.LLLLL.LL\n" +
                "..L.L.....\n" +
                "LLLLLLLLLL\n" +
                "L.LLLLLL.L\n" +
                "L.LLLLL.LL";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day11/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountStableOccupiedSeats_37() {
        assertEquals(37, SeatingSystem.getCountStableOccupiedSeats(test1));
    }

    @Test
    public void getCountStableOccupiedSeats_261() {
        assertEquals(2261, SeatingSystem.getCountStableOccupiedSeats(input));
    }

    @Test
    public void getModifiedCountStableOccupiedSeats_26() {
        assertEquals(26, SeatingSystem.getModifiedCountStableOccupiedSeats(test1));
    }

    @Test
    public void getModifiedCountStableOccupiedSeats_1() {
        assertEquals(2039, SeatingSystem.getModifiedCountStableOccupiedSeats(input));
    }
}
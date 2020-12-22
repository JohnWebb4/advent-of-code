/* Licensed under Apache-2.0 */
package advent.year2020.day13;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

public class ShuttleSearchTest {
    String test1;
    String test2;
    String input;

    @Before
    public void initialize() {
        test1 = "7,13,x,x,59,x,31,19";

        try {
            input = new String(Files.readAllBytes(Path.of("./src/test/java/advent/year2020/day13/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFirstBusIdTimeWait_944() {
        assertEquals(295, ShuttleSearch.getFirstBusIdTimesWait(939, test1));
    }

    @Test
    public void getFirstBusIdTimeWait_4135() {
        assertEquals(4135, ShuttleSearch.getFirstBusIdTimesWait(1011416, input));
    }

    @Test
    public void getEarliestTimeDepartOffsets_1068781() {
        assertEquals(1068781, ShuttleSearch.getEarliestTimeDepartOffsets(test1));
    }

    @Test
    public void getEarliestTimeDepartOffsets_3417() {
        assertEquals(3417, ShuttleSearch.getEarliestTimeDepartOffsets("17,x,13,19"));
    }

    @Test
    public void getEarliestTimeDepartOffsets_754018() {
        assertEquals(754018, ShuttleSearch.getEarliestTimeDepartOffsets("67,7,59,61"));
    }

    @Test
    public void getEarliestTimeDepartOffsets_779210() {
        assertEquals(779210, ShuttleSearch.getEarliestTimeDepartOffsets("67,x,7,59,61"));
    }

    @Test
    public void getEarliestTimeDepartOffsets_1261476() {
        assertEquals(1261476, ShuttleSearch.getEarliestTimeDepartOffsets("67,7,x,59,61"));
    }

    @Test
    public void getEarliestTimeDepartOffsets_1202161486() {
        assertEquals(1202161486, ShuttleSearch.getEarliestTimeDepartOffsets("1789,37,47,1889"));
    }

    @Test
    public void getEarliestTimeDepartOffsets_1() {
        assertEquals(640856202464541l, ShuttleSearch.getEarliestTimeDepartOffsets(input));
    }
}
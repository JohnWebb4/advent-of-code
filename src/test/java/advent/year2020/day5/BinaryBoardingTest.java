/* Licensed under Apache-2.0 */
package advent.year2020.day5;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class BinaryBoardingTest {
    public static String inputAnswer;

    @BeforeClass
    public static void initialize() {
        try {
            inputAnswer = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day5/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getHighestSeatId_567() {
        assertEquals(567, BinaryBoarding.getHighestSeatId("BFFFBBFRRR"));
    }

    @Test
    public void getHighestSeatId_119() {
        assertEquals(119, BinaryBoarding.getHighestSeatId("FFFBBBFRRR"));
    }

    @Test
    public void getHighestSeatId_820() {
        assertEquals(820, BinaryBoarding.getHighestSeatId("BBFFBBFRLL"));
    }

    @Test
    public void getHighestSeatId_861() {
        assertEquals(861, BinaryBoarding.getHighestSeatId(inputAnswer));
    }

    @Test
    public void getMissingSeatId_1() {
        assertEquals(633, BinaryBoarding.getMissingSeatId(inputAnswer));
    }
}
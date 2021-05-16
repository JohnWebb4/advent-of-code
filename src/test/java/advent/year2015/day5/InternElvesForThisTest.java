/* Licensed under Apache-2.0 */
package advent.year2015.day5;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class InternElvesForThisTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day5/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountNiceStrings_nice() {
        assertEquals(2, InternElvesForThis.getCountNiceStrings("ugknbfddgicrmopn\naaa"));
    }

    @Test
    public void getCountNiceStrings_naughty() {
        assertEquals(0, InternElvesForThis.getCountNiceStrings("jchzalrnumimnmhp\nhaegwjzuvuyypxyu\ndvszwmarrgswjxmb"));
    }

    @Test
    public void getCountNiceStrings_input() {
        assertEquals(236, InternElvesForThis.getCountNiceStrings(input));
    }

    @Test
    public void getCountNiceStringsV2_nice() {
        assertEquals(2, InternElvesForThis.getCountNiceStringsV2("qjhvhtzxzqqjkmpb\nxxyxx"));
    }

    @Test
    public void getCountNiceStringsV2_naughty() {
        assertEquals(0, InternElvesForThis.getCountNiceStringsV2("uurcxstgmygtbstg\nieodomkazucvgmuy"));
    }

    @Test
    public void getCountNiceStringsV2_input() {
        assertEquals(51, InternElvesForThis.getCountNiceStringsV2(input));
    }
}
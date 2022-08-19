/* Licensed under Apache-2.0 */
package advent.year2015.day2;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class ToldNoMathTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day2/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRequiredWrappingArea_58() {
        assertEquals(58, ToldNoMath.getRequiredWrappingArea("2x3x4"), 0.01);
    }

    @Test
    public void getRequiredWrappingArea_43() {
        assertEquals(43, ToldNoMath.getRequiredWrappingArea("1x1x10"), 0.01);
    }

    @Test
    public void getRequiredWrappingArea_0() {
        assertEquals(1588178, ToldNoMath.getRequiredWrappingArea(input), 0.01);
    }

    @Test
    public void getRequiredRibbonLength_34() {
        assertEquals(34, ToldNoMath.getRequiredRibbonLength("2x3x4"), 0.01);
    }

    @Test
    public void getRequiredRibbonLength_14() {
        assertEquals(14, ToldNoMath.getRequiredRibbonLength("1x1x10"), 0.01);
    }

    @Test
    public void getRequiredRibbonLength_0() {
        assertEquals(3783758, ToldNoMath.getRequiredRibbonLength(input), 0.01);
    }
}
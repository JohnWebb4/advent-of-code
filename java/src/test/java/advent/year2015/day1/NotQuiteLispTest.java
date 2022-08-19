/* Licensed under Apache-2.0 */
package advent.year2015.day1;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class NotQuiteLispTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day1/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFloor_0() {
        assertEquals(0, NotQuiteLisp.getFloor("(())"));
    }

    @Test
    public void testGetFloor_0_1() {
        assertEquals(0, NotQuiteLisp.getFloor("()()"));
    }

    @Test
    public void testGetFloor_74() {
        assertEquals(74, NotQuiteLisp.getFloor(input));
    }

    @Test
    public void testGetIndexFirstEnterBasement_1() {
        assertEquals(1, NotQuiteLisp.getIndexFirstEnterBasement(")"));
    }

    @Test
    public void testGetIndexFirstEnterBasement_5() {
        assertEquals(5, NotQuiteLisp.getIndexFirstEnterBasement("()())"));
    }

    @Test
    public void testGetIndexFirstEnterBasement_0() {
        assertEquals(1795, NotQuiteLisp.getIndexFirstEnterBasement(input));
    }
}
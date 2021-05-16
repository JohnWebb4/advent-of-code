/* Licensed under Apache-2.0 */
package advent.year2020.day22;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class CrabCombatTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "Player 1:\n" +
                "9\n" +
                "2\n" +
                "6\n" +
                "3\n" +
                "1\n" +
                "\n" +
                "Player 2:\n" +
                "5\n" +
                "8\n" +
                "4\n" +
                "7\n" +
                "10";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day22/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getWinningPlayerScore_306() {
        assertEquals(306, CrabCombat.getWinningPlayerScore(test1));
    }

    @Test
    public void getWinningPlayerScore_33403() {
        assertEquals(33403, CrabCombat.getWinningPlayerScore(input));
    }

    @Test
    public void getWinningRecursivePlayerScore_291() {
        assertEquals(291, CrabCombat.getWinningRecursivePlayerScore(test1));
    }

//    @Test
//    public void getWinningRecursivePlayerScore_29177() {
//        assertEquals(29177, CrabCombat.getWinningRecursivePlayerScore(input));
//    }
}
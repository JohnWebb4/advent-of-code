package advent.year2016.day10;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class BalanceBotsTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "value 5 goes to bot 2\n" +
                "bot 2 gives low to bot 1 and high to bot 0\n" +
                "value 3 goes to bot 1\n" +
                "bot 1 gives low to output 1 and high to bot 0\n" +
                "bot 0 gives low to output 2 and high to output 0\n" +
                "value 2 goes to bot 2";
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2016/day10/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBotWithMicrochips() {
        assertEquals(2, BalanceBots.getBotWithMicrochips(test1, new int[]{5, 2}));
        // 187: High
        assertEquals(0, BalanceBots.getBotWithMicrochips(input, new int[]{61, 17}));
    }
}
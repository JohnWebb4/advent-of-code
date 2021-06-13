/* Licensed under Apache-2.0 */
package advent.year2015.day13;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class KnightDinnerTableTest {
    public static String input;
    public static String test1;

    @BeforeClass
    public static void initialize() {
        try {
            test1 = "Alice would gain 54 happiness units by sitting next to Bob.\n" +
                    "Alice would lose 79 happiness units by sitting next to Carol.\n" +
                    "Alice would lose 2 happiness units by sitting next to David.\n" +
                    "Bob would gain 83 happiness units by sitting next to Alice.\n" +
                    "Bob would lose 7 happiness units by sitting next to Carol.\n" +
                    "Bob would lose 63 happiness units by sitting next to David.\n" +
                    "Carol would lose 62 happiness units by sitting next to Alice.\n" +
                    "Carol would gain 60 happiness units by sitting next to Bob.\n" +
                    "Carol would gain 55 happiness units by sitting next to David.\n" +
                    "David would gain 46 happiness units by sitting next to Alice.\n" +
                    "David would lose 7 happiness units by sitting next to Bob.\n" +
                    "David would gain 41 happiness units by sitting next to Carol.";
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day13/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getChangeInHappiness() {
        assertEquals(330, KnightDinnerTable.getChangeInHappiness(test1));
        assertEquals(709, KnightDinnerTable.getChangeInHappiness(input));
    }

    @Test
    public void getChangeInHappinessWithMe() {
        assertEquals(286, KnightDinnerTable.getChangeInHappinessWithMe(test1));
        assertEquals(668, KnightDinnerTable.getChangeInHappinessWithMe(input));
    }
}
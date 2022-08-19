/* Licensed under Apache-2.0 */
package advent.year2015.day8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

public class MatchSticksTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Path.of("./src/test/java/advent/year2015/day8/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getCodeMinusMemoryCharacters() {
        assertEquals(2, MatchSticks.getCodeMinusMemoryCharacters("\"\""));
        assertEquals(2, MatchSticks.getCodeMinusMemoryCharacters("\"abc\""));
        assertEquals(3, MatchSticks.getCodeMinusMemoryCharacters("\"aaa\\\"aaa\""));
        assertEquals(5, MatchSticks.getCodeMinusMemoryCharacters("\"\\x27\""));
        assertEquals(4, MatchSticks.getCodeMinusMemoryCharacters("\"c\\\"nhbqzndro\\\\g\""));
        assertEquals(1371, MatchSticks.getCodeMinusMemoryCharacters(input));
    }

    @Test
    public void getEncodedMinusMemoryCharacters() {
        assertEquals(4, MatchSticks.getEncodedMinusMemoryCharacters("\"\""));
        assertEquals(4, MatchSticks.getEncodedMinusMemoryCharacters("\"abc\""));
        assertEquals(6, MatchSticks.getEncodedMinusMemoryCharacters("\"aaa\\\"aaa\""));
        assertEquals(5, MatchSticks.getEncodedMinusMemoryCharacters("\"\\x27\""));
        assertEquals(2117, MatchSticks.getEncodedMinusMemoryCharacters(input));
    }
}
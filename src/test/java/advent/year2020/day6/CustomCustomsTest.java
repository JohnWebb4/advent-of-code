/* Licensed under Apache-2.0 */
package advent.year2020.day6;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class CustomCustomsTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day6/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountUniqueQuestions_11() {
        assertEquals(11, CustomCustoms.getCountUniqueQuestions("abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b"));
    }

    @Test
    public void getCountUniqueQuestions_12() {
        assertEquals(7128, CustomCustoms.getCountUniqueQuestions(input));
    }

    @Test
    public void getCountAllYesQuestions_6() {
        assertEquals(6, CustomCustoms.getCountAllYesQuestions("abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b"));

    }

    @Test
    public void getCountAllYesQuestions_10() {
        assertEquals(3640, CustomCustoms.getCountAllYesQuestions(input));
    }
}
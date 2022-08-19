/* Licensed under Apache-2.0 */
package advent.year2016.day6;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class SignalsNoiseTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() throws IOException {
        test1 = "eedadn\n" +
                "drvtee\n" +
                "eandsr\n" +
                "raavrd\n" +
                "atevrs\n" +
                "tsrnev\n" +
                "sdttsa\n" +
                "rasrtv\n" +
                "nssdts\n" +
                "ntnada\n" +
                "svetve\n" +
                "tesnvt\n" +
                "vntsnd\n" +
                "vrdear\n" +
                "dvrsen\n" +
                "enarar";


        input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2016/day6/input.txt")));
    }

    @Test
    public void getStringMostRepeatedForPosition() {
        assertEquals("easter", SignalsNoise.getStringMostRepeatedForPosition(test1));
        assertEquals("xdkzukcf", SignalsNoise.getStringMostRepeatedForPosition(input));
    }

    @Test
    public void getStringLeastRepeatedForPosition() {
        assertEquals("advent", SignalsNoise.getStringLeastRepeatedForPosition(test1));
        assertEquals("cevsgyvd", SignalsNoise.getStringLeastRepeatedForPosition(input));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2015.day12;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

public class JSAbacusFrameworkTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Path.of("./src/test/java/advent/year2015/day12/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sumJSONNumbers() {
        assertEquals(6, JSAbacusFramework.sumJSONNumbers("[1,2,3]"), 0.01);
        assertEquals(6, JSAbacusFramework.sumJSONNumbers("{\"a\":2,\"b\":4}"), 0.01);
        assertEquals(3, JSAbacusFramework.sumJSONNumbers("[[[3]]]"), 0.01);
        assertEquals(3, JSAbacusFramework.sumJSONNumbers("{\"a\":{\"b\":4},\"c\":-1}"), 0.01);
        assertEquals(0, JSAbacusFramework.sumJSONNumbers("{\"a\":[-1,1]}"), 0.01);
        assertEquals(0, JSAbacusFramework.sumJSONNumbers("[-1,{\"a\":1}]"), 0.01);
        assertEquals(0, JSAbacusFramework.sumJSONNumbers("[]"), 0.01);
        assertEquals(0, JSAbacusFramework.sumJSONNumbers("{}"), 0.01);
        assertEquals(156366, JSAbacusFramework.sumJSONNumbers(input), 0.01);
    }

    @Test
    public void sumJSONNumbersIgnoreRed() {
        assertEquals(6, JSAbacusFramework.sumJSONNumbersIgnoreRed("[1,2,3]"), 0.01);
        assertEquals(4, JSAbacusFramework.sumJSONNumbersIgnoreRed("[1,{\"c\":\"red\",\"b\":2},3]"), 0.01);
        assertEquals(0, JSAbacusFramework.sumJSONNumbersIgnoreRed("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"), 0.01);
        assertEquals(6, JSAbacusFramework.sumJSONNumbersIgnoreRed("[1,\"red\",5]"), 0.01);
        assertEquals(96852, JSAbacusFramework.sumJSONNumbersIgnoreRed(input), 0.01);
    }
}
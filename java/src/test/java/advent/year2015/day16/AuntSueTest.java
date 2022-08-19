/* Licensed under Apache-2.0 */
package advent.year2015.day16;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class AuntSueTest {
    public static String testResults;
    public static String auntSueString;

    @BeforeClass
    public static void initialize() {
        try {
            testResults = "Test 1: children: 3, cats: 7, samoyeds: 2, pomeranians: 3, akitas: 0, vizslas: 0, goldfish: 5, trees: 3, cars: 2, perfumes: 1";
            auntSueString = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day16/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getIndexSueGotGift() {
        assertEquals(213, AuntSue.getIndexSueGotGift(testResults, auntSueString));
    }

    @Test
    public void getIndexSueGotGiftWithDecay() {
        assertEquals(323, AuntSue.getIndexSueGotGiftWithDecay(testResults, auntSueString));
    }
}
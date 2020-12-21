/* Licensed under Apache-2.0 */
package advent.year2020.day10;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class AdapterArrayTest {
    public static int[] test1;
    public static int[] test2;
    public static int[] input;

    @Before
    public void initialize() {
        test1 = new int[]{
                16,
                10,
                15,
                5,
                1,
                11,
                7,
                19,
                6,
                12,
                4
        };

        test2 = new int[]{
                28,
                33,
                18,
                42,
                31,
                14,
                46,
                20,
                48,
                47,
                24,
                23,
                49,
                45,
                19,
                38,
                39,
                11,
                1,
                32,
                25,
                35,
                8,
                17,
                7,
                9,
                4,
                2,
                34,
                10,
                3,
        };

        try {
            String[] inputString = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day10/input.txt"))).split("\n");

            input = Arrays.stream(inputString).mapToInt((s) -> Integer.parseInt(s)).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getVoltageDifferencesProduct_35() {
        assertEquals(35, AdapterArray.getVoltageDifferencesProduct(test1));
    }

    @Test
    public void getVoltageDifferencesProduct_220() {
        assertEquals(220, AdapterArray.getVoltageDifferencesProduct(test2));
    }

    @Test
    public void getVoltageDifferencesProduct_1856() {
        assertEquals(1856, AdapterArray.getVoltageDifferencesProduct(input));
    }

    @Test
    public void getPossibleAdapterArrangements_8() {
        assertEquals(8, AdapterArray.getPossibleAdapterArrangements(test1));
    }

    @Test
    public void getPossibleAdapterArrangements_19208() {
        assertEquals(19208, AdapterArray.getPossibleAdapterArrangements(test2));
    }

    @Test
    public void getPossibleAdapterArrangements_2314037239808() {
        assertEquals(2314037239808l, AdapterArray.getPossibleAdapterArrangements(input));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2015.day7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class SomeAssemblyTest {
    public static String test1 = "123 -> x\n" +
            "456 -> y\n" +
            "x AND y -> d\n" +
            "x OR y -> e\n" +
            "x LSHIFT 2 -> f\n" +
            "y RSHIFT 2 -> g\n" +
            "NOT x -> h\n" +
            "NOT y -> i";
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day7/input.txt")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSignaToWire_test1() {
        assertEquals(72, SomeAssembly.getSignaToWire(test1, "d"));
        assertEquals(507, SomeAssembly.getSignaToWire(test1, "e"));
    }

    @Test
    public void getSignalToWire_input() {
        assertEquals(3176, SomeAssembly.getSignaToWire(input, "a"));
    }

    @Test
    public void getSignalToWireWithOverride_input() {
        assertEquals(14710, SomeAssembly.getSignaToWireWithOverride(input, "a", "b"));
    }
}
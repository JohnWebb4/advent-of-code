/* Licensed under Apache-2.0 */
package advent.year2020.day14;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DockingDataTest {
    public static String input;
    public static String test1;
    public static String test2;

    @BeforeClass
    public static void initialize() {
        test1 = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                "mem[8] = 11\n" +
                "mem[7] = 101\n" +
                "mem[8] = 0";

        test2 = "mask = 000000000000000000000000000000X1001X\n" +
                "mem[42] = 100\n" +
                "mask = 00000000000000000000000000000000X0XX\n" +
                "mem[26] = 1";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day14/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initializeDockingProgram_165() {
        assertEquals(165, DockingData.initializeDockingProgram(test1));
    }

    @Test
    public void initializeDockingProgram_6386593869035() {
        assertEquals(6386593869035l, DockingData.initializeDockingProgram(input));
    }

    @Test
    public void initializeDockingProgramV2_208() {
        assertEquals(208, DockingData.initializeDockingProgramV2(test2));
    }

    @Test
    public void initializeDockingProgramV2_4288986482164() {
        assertEquals(4288986482164l, DockingData.initializeDockingProgramV2(input));
    }
}
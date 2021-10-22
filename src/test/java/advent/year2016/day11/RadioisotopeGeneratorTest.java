package advent.year2016.day11;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RadioisotopeGeneratorTest {
    public static String test1;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\n" +
                "The second floor contains a hydrogen generator.\n" +
                "The third floor contains a lithium generator.\n" +
                "The fourth floor contains nothing relevant.";

        input = "The first floor contains a thulium generator, a thulium-compatible microchip, a plutonium generator, and a strontium generator.\n" +
                "The second floor contains a plutonium-compatible microchip and a strontium-compatible microchip.\n" +
                "The third floor contains a promethium generator, a promethium-compatible microchip, a ruthenium generator, and a ruthenium-compatible microchip.\n" +
                "The fourth floor contains nothing relevant.";
    }

    @Test
    public void getMinStepsToMoveChips() {
        assertEquals(11, RadioisotopeGenerator.getMinStepsToMoveChips(test1, 2));
        assertEquals(0, RadioisotopeGenerator.getMinStepsToMoveChips(input, 2));
    }
}
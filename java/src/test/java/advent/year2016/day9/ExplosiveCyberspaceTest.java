/* Licensed under Apache-2.0 */
package advent.year2016.day9;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExplosiveCyberspaceTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2016/day9/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDecompressedLength() {
        assertEquals(6, ExplosiveCyberspace.getDecompressedLength("ADVENT"));
        assertEquals(7, ExplosiveCyberspace.getDecompressedLength("A(1x5)BC"));
        assertEquals(9, ExplosiveCyberspace.getDecompressedLength("(3x3)XYZ"));
        assertEquals(11, ExplosiveCyberspace.getDecompressedLength("A(2x2)BCD(2x2)EFG"));
        assertEquals(6, ExplosiveCyberspace.getDecompressedLength("(6x1)(1x3)A"));
        assertEquals(18, ExplosiveCyberspace.getDecompressedLength("X(8x2)(3x3)ABCY"));
        assertEquals(115118, ExplosiveCyberspace.getDecompressedLength(input));
    }

    @Test
    public void getDecompressedRecursiveLength() {
        assertEquals(9, ExplosiveCyberspace.getDecompressedRecursiveLength("(3x3)XYZ"));
        assertEquals(20, ExplosiveCyberspace.getDecompressedRecursiveLength("X(8x2)(3x3)ABCY"));
        assertEquals(241920, ExplosiveCyberspace.getDecompressedRecursiveLength("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
        assertEquals(445, ExplosiveCyberspace.getDecompressedRecursiveLength("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
        assertEquals(11107527530L, ExplosiveCyberspace.getDecompressedRecursiveLength(input));
    }
}
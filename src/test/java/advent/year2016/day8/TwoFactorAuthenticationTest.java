/* Licensed under Apache-2.0 */
package advent.year2016.day8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class TwoFactorAuthenticationTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2016/day8/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLitPixelCount() {
        assertEquals(6, TwoFactorAuthentication.getLitPixelCount("rect 3x2\nrotate column x=1 by 1\nrotate row y=0 by 4\nrotate column x=1 by 1", 7, 3));
        assertEquals(116, TwoFactorAuthentication.getLitPixelCount(input, 50, 6));
    }
}
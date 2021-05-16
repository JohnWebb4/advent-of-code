/* Licensed under Apache-2.0 */
package advent.year2020.day18;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationOrderTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day18/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void evaluate_13632() {
        assertEquals(13632, OperationOrder.evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"));
    }

    @Test
    public void evaluateAndSum_86311597203806() {
        assertEquals(86311597203806l, OperationOrder.evaluateAndSum(input));
    }

    @Test
    public void evaluateAdvanced_23340() {
        assertEquals(23340, OperationOrder.evaluateAdvanced("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"));
    }

    @Test
    public void evaluateAdvancedAndSum_276894767062189() {
        assertEquals(276894767062189l, OperationOrder.evaluateAdvancedAndSum(input));
    }
}
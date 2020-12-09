/* Licensed under Apache-2.0 */
package advent.year2020.day9;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class EncodingErrorTest {
	public static String test1;
	public static String input;

	@BeforeClass
	public static void initialize() {
		try {
			test1 = "35\n" +
					"20\n" +
					"15\n" +
					"25\n" +
					"47\n" +
					"40\n" +
					"62\n" +
					"55\n" +
					"65\n" +
					"95\n" +
					"102\n" +
					"117\n" +
					"150\n" +
					"182\n" +
					"127\n" +
					"219\n" +
					"299\n" +
					"277\n" +
					"309\n" +
					"576";
			input = Files.readString(Paths.get("./src/test/java/advent/year2020/day9/input.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findNonSum_127() {
		assertEquals(127, EncodingError.findNonSum(test1, 5, 5));
	}

	@Test
	public void findNonSum_530627549() {
		assertEquals(530627549L, EncodingError.findNonSum(input, 25, 25));
	}

	@Test
	public void findWeakness_62() {
		assertEquals(62, EncodingError.findWeakness(test1, 5, 5));
	}

	@Test
	public void findWeakness_63() {
		assertEquals(77730285, EncodingError.findWeakness(input, 25, 25));
	}
}
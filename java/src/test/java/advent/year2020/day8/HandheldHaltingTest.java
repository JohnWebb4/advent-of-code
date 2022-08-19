/* Licensed under Apache-2.0 */
package advent.year2020.day8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class HandheldHaltingTest {
	public static String test1;
	public static String input;

	@BeforeClass
	public static void initialize() {
		try {
			test1 = "nop +0\n" +
					"acc +1\n" +
					"jmp +4\n" +
					"acc +3\n" +
					"jmp -3\n" +
					"acc -99\n" +
					"acc +1\n" +
					"jmp -4\n" +
					"acc +6";

			input = Files.readString(Paths.get("./src/test/java/advent/year2020/day8/input.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getValueBeforeLoop_5() {
		assertEquals(5, HandheldHalting.getValueBeforeLoop(test1));
	}

	@Test
	public void getValueBeforeLoop_1749() {
		assertEquals(1749, HandheldHalting.getValueBeforeLoop(input));
	}

	@Test
	public void fixAndGetValue_8() {
		assertEquals(8, HandheldHalting.fixAndGetValue(test1));
	}

	@Test
	public void fixAndGetValue_515() {
		assertEquals(515, HandheldHalting.fixAndGetValue(input));
	}
}
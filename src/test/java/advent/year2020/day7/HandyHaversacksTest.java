/* Licensed under Apache-2.0 */
package advent.year2020.day7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class HandyHaversacksTest {
	static String test1;
	static String test2;
	static String input;

	@BeforeClass
	public static void initialize() {
		try {
			test1 = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
					"dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
					"bright white bags contain 1 shiny gold bag.\n" +
					"muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
					"shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
					"dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
					"vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
					"faded blue bags contain no other bags.\n" +
					"dotted black bags contain no other bags.";

			test2 = "shiny gold bags contain 2 dark red bags.\n" +
					"dark red bags contain 2 dark orange bags.\n" +
					"dark orange bags contain 2 dark yellow bags.\n" +
					"dark yellow bags contain 2 dark green bags.\n" +
					"dark green bags contain 2 dark blue bags.\n" +
					"dark blue bags contain 2 dark violet bags.\n" +
					"dark violet bags contain no other bags.";

			input = Files.readString(Paths.get("./src/test/java/advent/year2020/day7/input.txt"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void countTypesCanContainGold_4() {
		assertEquals(4, HandyHaversacks.countTypesCanContainGold(test1));

	}

	@Test
	public void countTypesCanContainGold_172() {
		assertEquals(172, HandyHaversacks.countTypesCanContainGold(input));
	}

	@Test
	public void countBagsRequiredForGold_32() {
		assertEquals(32, HandyHaversacks.countBagsRequiredForGold(test1));
	}

	@Test
	public void countBagsRequiredForGold_126() {
		assertEquals(126, HandyHaversacks.countBagsRequiredForGold(test2));
	}

	@Test
	public void countBagsRequiredForGold_127() {
		assertEquals(88298, HandyHaversacks.countBagsRequiredForGold(input));
	}
}
/* Licensed under Apache-2.0 */
package advent.year2020.day3;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class TobogganTrajectoryTest {
	public static String inputAnswer;
	public static String inputTest;

	@BeforeClass
	public static void initialize() {
		inputTest = "..##.......\n" +
				"#...#...#..\n" +
				".#....#..#.\n" +
				"..#.#...#.#\n" +
				".#...##..#.\n" +
				"..#.##.....\n" +
				".#.#.#....#\n" +
				".#........#\n" +
				"#.##...#...\n" +
				"#...##....#\n" +
				".#..#...#.#";

		try {
			inputAnswer = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day3/input.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getNumTreesInSlope_7() {
		assertEquals(7, TobogganTrajectory.getNumTreesInSlope(inputTest, 3, 1));
	}

	@Test
	public void getNumTreesInSlope_10() {
		assertEquals(178, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 3, 1));
	}

	@Test
	public void getMultipleNumTreesInSlope_336() {
		assertEquals(2, TobogganTrajectory.getNumTreesInSlope(inputTest, 1, 1));
		assertEquals(7, TobogganTrajectory.getNumTreesInSlope(inputTest, 3, 1));
		assertEquals(3, TobogganTrajectory.getNumTreesInSlope(inputTest, 5, 1));
		assertEquals(4, TobogganTrajectory.getNumTreesInSlope(inputTest, 7, 1));
		assertEquals(2, TobogganTrajectory.getNumTreesInSlope(inputTest, 1, 2));
	}

	@Test
	public void getMultipleNumTreesInSlope_123() {
		assertEquals(78, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 1, 1));
		assertEquals(178, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 3, 1));
		assertEquals(75, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 5, 1));
		assertEquals(86, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 7, 1));
		assertEquals(39, TobogganTrajectory.getNumTreesInSlope(inputAnswer, 1, 2));
	}
}
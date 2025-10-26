/* Licensed under Apache-2.0 */
package advent.year2020.day3;

import java.util.Arrays;

public class TobogganTrajectory {
	public static int getNumTreesInSlope(String path, int stepX, int stepY) {
		String[][] spaces = Arrays.stream(path.split("\n")).map((row) -> row.split("")).toArray(String[][]::new);

		return getNumTreesInSlope(spaces, stepX, stepY);
	}

	public static int getNumTreesInSlope(String[][] spaces, int stepX, int stepY) {
		int numTrees = 0;

		int step = 0;
		int currentX = 0;
		int currentY = 0;

		while (currentY < spaces.length) {
			if (spaces[currentY][currentX].equals("#")) {
				numTrees++;
			}

			step++;
			currentX = (step * stepX) % spaces[0].length;
			currentY = step * stepY;
		}

		return numTrees;
	}
}

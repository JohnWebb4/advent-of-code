/* Licensed under Apache-2.0 */
package advent.year2016.day3;

import java.util.Arrays;

public class SquareThreeSides {
    public static int getCountPossibleTriangles(String triangleString) {
        String[] triangleArray = triangleString.split("\n");

        int countValid = 0;
        for (String triangle : triangleArray) {
            int[] sides = Arrays.stream(triangle.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();

            if (sides[0] + sides[1] > sides[2] && sides[0] + sides[2] > sides[1] && sides[1] + sides[2] > sides[0]) {
                countValid++;
            }
        }

        return countValid;
    }

    public static int getCountPossibleTrianglesCol(String triangleString) {
        int[][] numberGrid = Arrays.stream(triangleString.split("\n")).map((s) -> Arrays.stream(s.trim().split("\\s+")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);

        int countValid = 0;
        for (int i = 0; i < numberGrid.length; i += 3) {
            for (int j = 0; j < numberGrid[i].length; j++) {
                int side1 = numberGrid[i][j];
                int side2 = numberGrid[i + 1][j];
                int side3 = numberGrid[i + 2][j];
                int[] sides = new int[] { side1, side2, side3};

                if (sides[0] + sides[1] > sides[2] && sides[0] + sides[2] > sides[1] && sides[1] + sides[2] > sides[0]) {
                    countValid++;
                }
            }
        }

        return countValid;
    }
}

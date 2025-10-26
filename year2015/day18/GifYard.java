/* Licensed under Apache-2.0 */
package advent.year2015.day18;

import java.util.Arrays;

public class GifYard {
    public static long getLightsOnAfterXSteps(String input, int steps) {
        String[][] sGrid = Arrays.stream(input.split("\n")).map((s) -> s.split("")).toArray(String[][]::new);

        boolean[][] grid = new boolean[sGrid.length][sGrid[0].length];
        for (int i = 0; i < sGrid.length; i++) {
            for (int j = 0; j < sGrid[i].length; j++) {
                if (sGrid[i][j].equals("#")) {
                    grid[i][j] = true;
                }
            }
        }


        for (int i = 0; i < steps; i++) {
            boolean[][] newGrid = new boolean[grid.length][grid[0].length];

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    int neighborOnCount = 0;

                    if (x > 0 && y > 0 && grid[x - 1][y - 1]) {
                        neighborOnCount++;
                    }

                    if (x > 0 && grid[x - 1][y]) {
                        neighborOnCount++;
                    }

                    if (x > 0 && y < grid[x].length - 1 && grid[x - 1][y + 1]) {
                        neighborOnCount++;
                    }

                    if (y > 0 && grid[x][y - 1]) {
                        neighborOnCount++;
                    }

                    if (y < grid[x].length - 1 && grid[x][y + 1]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && y > 0 && grid[x + 1][y - 1]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && grid[x + 1][y]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && y < grid[x].length - 1 && grid[x + 1][y + 1]) {
                        neighborOnCount++;
                    }

                    if (grid[x][y] && (neighborOnCount == 2 || neighborOnCount == 3)) {
                        newGrid[x][y] = true;
                    }

                    if (!grid[x][y] && neighborOnCount == 3) {
                        newGrid[x][y] = true;
                    }
                }
            }

            grid = newGrid;
        }

        long count = 0;
        for (boolean[] booleans : grid) {
            for (boolean aBoolean : booleans) {
                count += aBoolean ? 1 : 0;
            }
        }
        return count;
    }

    public static long getLightsOnAfterXStepsConway(String input, int steps) {
        String[][] sGrid = Arrays.stream(input.split("\n")).map((s) -> s.split("")).toArray(String[][]::new);

        boolean[][] grid = new boolean[sGrid.length][sGrid[0].length];
        for (int i = 0; i < sGrid.length; i++) {
            for (int j = 0; j < sGrid[i].length; j++) {
                if (sGrid[i][j].equals("#")) {
                    grid[i][j] = true;
                }
            }
        }

        grid[0][0] = true;
        grid[0][grid[0].length - 1] = true;
        grid[grid.length - 1][grid[0].length - 1] = true;
        grid[grid.length - 1][0] = true;


        for (int i = 0; i < steps; i++) {
            boolean[][] newGrid = new boolean[grid.length][grid[0].length];

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[x].length; y++) {
                    int neighborOnCount = 0;

                    if (x > 0 && y > 0 && grid[x - 1][y - 1]) {
                        neighborOnCount++;
                    }

                    if (x > 0 && grid[x - 1][y]) {
                        neighborOnCount++;
                    }

                    if (x > 0 && y < grid[x].length - 1 && grid[x - 1][y + 1]) {
                        neighborOnCount++;
                    }

                    if (y > 0 && grid[x][y - 1]) {
                        neighborOnCount++;
                    }

                    if (y < grid[x].length - 1 && grid[x][y + 1]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && y > 0 && grid[x + 1][y - 1]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && grid[x + 1][y]) {
                        neighborOnCount++;
                    }

                    if (x < grid.length - 1 && y < grid[x].length - 1 && grid[x + 1][y + 1]) {
                        neighborOnCount++;
                    }

                    if (grid[x][y] && (neighborOnCount == 2 || neighborOnCount == 3)) {
                        newGrid[x][y] = true;
                    }

                    if (!grid[x][y] && neighborOnCount == 3) {
                        newGrid[x][y] = true;
                    }
                }
            }

            newGrid[0][0] = true;
            newGrid[0][newGrid[0].length - 1] = true;
            newGrid[newGrid.length - 1][newGrid[0].length - 1] = true;
            newGrid[newGrid.length - 1][0] = true;

            grid = newGrid;
        }

        long count = 0;
        for (boolean[] booleans : grid) {
            for (boolean aBoolean : booleans) {
                count += aBoolean ? 1 : 0;
            }
        }
        return count;
    }
}

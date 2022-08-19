/* Licensed under Apache-2.0 */
package advent.year2020.day11;

import java.util.Arrays;

public class SeatingSystem {
    public static int getCountStableOccupiedSeats(String input) {
        String[][] cells = Arrays.stream(input.split("\n")).map((String row) -> row.split("")).toArray(String[][]::new);

        boolean hasUpdate;
        do {
            hasUpdate = false;

            String[][] newCells = new String[cells.length][cells[0].length];

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    newCells[i][j] = cells[i][j];

                    int adjacentCount = 0;
                    switch (cells[i][j]) {
                        case "L":
                            adjacentCount = getAdjacentCount(cells, i, j);

                            if (adjacentCount == 0) {
                                newCells[i][j] = "#";
                                hasUpdate = true;
                            }
                            break;
                        case "#":
                            adjacentCount = getAdjacentCount(cells, i, j);

                            if (adjacentCount >= 4) {
                                newCells[i][j] = "L";
                                hasUpdate = true;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            cells = newCells;
        } while (hasUpdate);

        int count = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].equals("#")) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int getAdjacentCount(String[][] cells, int i, int j) {
        int adjacentCount = 0;

        if (i > 0) {
            if (cells[i - 1][j].equals("#")) {
                adjacentCount++;
            }
        }

        if (i < cells.length - 1) {
            if (cells[i + 1][j].equals("#")) {
                adjacentCount++;
            }
        }

        if (j > 0) {
            if (cells[i][j - 1].equals("#")) {
                adjacentCount++;
            }
        }

        if (j < cells[i].length - 1) {
            if (cells[i][j + 1].equals("#")) {
                adjacentCount++;
            }
        }

        if (i > 0 && j > 0) {
            if (cells[i - 1][j - 1].equals("#")) {
                adjacentCount++;
            }
        }

        if (i > 0 && j < cells[i].length - 1) {
            if (cells[i - 1][j + 1].equals("#")) {
                adjacentCount++;
            }
        }

        if (i < cells.length - 1 && j > 0) {
            if (cells[i + 1][j - 1].equals("#")) {
                adjacentCount++;
            }
        }

        if (i < cells.length - 1 && j < cells[i].length - 1) {
            if (cells[i + 1][j + 1].equals("#")) {
                adjacentCount++;
            }
        }

        return adjacentCount;
    }

    public static int getModifiedCountStableOccupiedSeats(String input) {
        String[][] cells = Arrays.stream(input.split("\n")).map((String row) -> row.split("")).toArray(String[][]::new);

        boolean hasUpdate;
        int loopCount = 0;
        do {
            hasUpdate = false;

            String[][] newCells = new String[cells.length][cells[0].length];

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    newCells[i][j] = cells[i][j];

                    int visibleCount = 0;
                    switch (cells[i][j]) {
                        case "L":
                            visibleCount = getVisibleCount(cells, i, j);

                            if (visibleCount == 0) {
                                newCells[i][j] = "#";
                                hasUpdate = true;
                            }
                            break;
                        case "#":
                            visibleCount = getVisibleCount(cells, i, j);

                            if (visibleCount >= 5) {
                                newCells[i][j] = "L";
                                hasUpdate = true;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            cells = newCells;

            loopCount++;
        } while (hasUpdate && loopCount < 10000);

        int count = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].equals("#")) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int getVisibleCount(String[][] cells, int i, int j) {
        int visibleCount = 0;

        if (i > 0) {
            for (int step = 1; step <= i; step++) {
                if (cells[i - step][j].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i - step][j].equals("L")) {
                    break;
                }
            }
        }

        if (i < cells.length - 1) {
            for (int step = 1; step <= cells.length - i - 1; step++) {
                if (cells[i + step][j].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i + step][j].equals("L")) {
                    break;
                }
            }
        }

        if (j > 0) {
            for (int step = 1; step <= j; step++) {
                if (cells[i][j - step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i][j - step].equals("L")) {
                    break;
                }
            }
        }

        if (j < cells[i].length - 1) {
            for (int step = 1; step <= cells[i].length - j - 1; step++) {
                if (cells[i][j + step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i][j + step].equals("L")) {
                    break;
                }
            }
        }

        if (i > 0 && j > 0) {
            for (int step = 1; step <= Math.min(i, j); step++) {
                if (cells[i - step][j - step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i - step][j - step].equals("L")) {
                    break;
                }
            }
        }

        if (i > 0 && j < cells[i].length - 1) {
            for (int step = 1; step <= Math.min(i, cells[i].length - j - 1); step++) {
                if (cells[i - step][j + step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i - step][j + step].equals("L")) {
                    break;
                }
            }
        }

        if (i < cells.length - 1 && j > 0) {
            for (int step = 1; step <= Math.min(cells.length - i - 1, j); step++) {
                if (cells[i + step][j - step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i + step][j - step].equals("L")) {
                    break;
                }
            }
        }

        if (i < cells.length - 1 && j < cells[i].length - 1) {
            for (int step = 1; step <= Math.min(cells.length - i - 1, cells[i].length - j - 1); step++) {
                if (cells[i + step][j + step].equals("#")) {
                    visibleCount++;
                    break;
                } else if (cells[i + step][j + step].equals("L")) {
                    break;
                }
            }
        }

        return visibleCount;
    }
}

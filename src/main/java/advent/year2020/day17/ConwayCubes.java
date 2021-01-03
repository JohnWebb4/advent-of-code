/* Licensed under Apache-2.0 */
package advent.year2020.day17;

import java.util.Arrays;

public class ConwayCubes {
    public static int simulateAndCountActive(String input, int cycles) {
        String[][] initialSlice = Arrays.stream(input.split("\n")).map((row) -> row.split("")).toArray(String[][]::new);

        String[][][] space = new String[1 + 2 * cycles][][];

        for (int i = 0; i < space.length; i++) {
            space[i] = new String[initialSlice.length + 2 * cycles][];

            for (int j = 0; j < space[i].length; j++) {
                space[i][j] = new String[initialSlice[0].length + 2 * cycles];

                for (int k = 0; k < space[i][j].length; k++) {
                    space[i][j][k] = ".";
                }
            }
        }

        for (int i = 0; i < initialSlice.length; i++) {
            for (int j = 0; j < initialSlice[i].length; j++) {
                space[cycles][i + cycles][j + cycles] = initialSlice[i][j];
            }
        }

        for (int t = 0; t < cycles; t++) {
            String[][][] updatedSpace = new String[space.length][][];

            for (int i = 0; i < space.length; i++) {
                updatedSpace[i] = new String[space[i].length][];

                for (int j = 0; j < space[i].length; j++) {
                    updatedSpace[i][j] = new String[space[i][j].length];

                    for (int k = 0; k < space[i][j].length; k++) {
                        int neighborActiveCount = 0;

                        for (int ineigh = -1; ineigh < 2; ineigh++) {
                            for (int jneigh = -1; jneigh < 2; jneigh++) {
                                for (int kneigh = -1; kneigh < 2; kneigh++) {
                                    try {
                                        String neighbor = space[i + ineigh][j + jneigh][k + kneigh];
                                        if (neighbor != null && neighbor.equals("#")) {
                                            if (ineigh != 0 || jneigh != 0 || kneigh != 0) {
                                                neighborActiveCount++;
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                    }
                                }
                            }
                        }

                        String cell = space[i][j][k];

                        if (cell != null && cell.equals("#")) {
                            if (neighborActiveCount == 2 || neighborActiveCount == 3) {
                                updatedSpace[i][j][k] = "#";
                            } else {
                                updatedSpace[i][j][k] = ".";
                            }
                        } else if (cell == null || cell.equals(".")) {
                            if (neighborActiveCount == 3) {
                                updatedSpace[i][j][k] = "#";
                            } else {
                                updatedSpace[i][j][k] = ".";
                            }
                        }
                    }
                }
            }

            space = updatedSpace;
        }


        int count = 0;
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[i].length; j++) {
                for (int k = 0; k < space[i][j].length; k++) {
                    if (space[i][j][k].equals("#")) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static int simulate4DAndCountActive(String input, int cycles) {
        String[][] initialSlice = Arrays.stream(input.split("\n")).map((row) -> row.split("")).toArray(String[][]::new);

        String[][][][] space = new String[1 + 2 * cycles][][][];

        for (int i = 0; i < space.length; i++) {
            space[i] = new String[initialSlice.length + 2 * cycles][][];

            for (int j = 0; j < space[i].length; j++) {
                space[i][j] = new String[initialSlice[0].length + 2 * cycles][];

                for (int k = 0; k < space[i][j].length; k++) {
                    space[i][j][k] = new String[1 + 2 * cycles];

                    for (int w = 0; w < space[i][j][k].length; w++) {
                        space[i][j][k][w] = ".";
                    }
                }
            }
        }

        for (int i = 0; i < initialSlice.length; i++) {
            for (int j = 0; j < initialSlice[i].length; j++) {
                space[cycles][i + cycles][j + cycles][cycles] = initialSlice[i][j];
            }
        }

        for (int t = 0; t < cycles; t++) {
            String[][][][] updatedSpace = new String[space.length][][][];

            for (int i = 0; i < space.length; i++) {
                updatedSpace[i] = new String[space[i].length][][];

                for (int j = 0; j < space[i].length; j++) {
                    updatedSpace[i][j] = new String[space[i][j].length][];

                    for (int k = 0; k < space[i][j].length; k++) {
                        updatedSpace[i][j][k] = new String[space[i][j][k].length];

                        for (int w = 0; w < space[i][j][k].length; w++) {
                            int neighborActiveCount = 0;

                            for (int ineigh = -1; ineigh < 2; ineigh++) {
                                for (int jneigh = -1; jneigh < 2; jneigh++) {
                                    for (int kneigh = -1; kneigh < 2; kneigh++) {
                                        for (int wneigh = -1; wneigh < 2; wneigh++) {
                                            try {
                                                String neighbor = space[i + ineigh][j + jneigh][k + kneigh][w + wneigh];

                                                if (neighbor != null && neighbor.equals("#")) {
                                                    if (ineigh != 0 || jneigh != 0 || kneigh != 0 || wneigh != 0) {
                                                        neighborActiveCount++;
                                                    }
                                                }
                                            } catch (ArrayIndexOutOfBoundsException e) {
                                            }
                                        }
                                    }
                                }
                            }

                            String cell = space[i][j][k][w];

                            if (cell != null && cell.equals("#")) {
                                if (neighborActiveCount == 2 || neighborActiveCount == 3) {
                                    updatedSpace[i][j][k][w] = "#";
                                } else {
                                    updatedSpace[i][j][k][w] = ".";
                                }
                            } else if (cell == null || cell.equals(".")) {
                                if (neighborActiveCount == 3) {
                                    updatedSpace[i][j][k][w] = "#";
                                } else {
                                    updatedSpace[i][j][k][w] = ".";
                                }
                            }
                        }
                    }
                }
            }

            space = updatedSpace;
        }


        int count = 0;
        for (int i = 0; i < space.length; i++) {
            for (int j = 0; j < space[i].length; j++) {
                for (int k = 0; k < space[i][j].length; k++) {
                    for (int w = 0; w < space[i][j][k].length; w++) {
                        if (space[i][j][k][w].equals("#")) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }
}

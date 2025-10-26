/* Licensed under Apache-2.0 */
package advent.year2016.day8;

import java.util.Arrays;
import java.util.Locale;

public class TwoFactorAuthentication {
    public static int getLitPixelCount(String instructionString, int rowSize, int colSize) {
        String[] instructions = instructionString.split("\n");

        int[][] screenPixels = new int[rowSize][colSize];

        for (String instruction : instructions) {
            String[] args = instruction.split(" ");
            String command = args[0].toLowerCase(Locale.ROOT);

            switch (command) {
                case "rect":
                    int[] dimensions = Arrays.stream(args[1].split("x")).mapToInt(Integer::parseInt).toArray();

                    for (int rowIndex = 0; rowIndex < dimensions[0]; rowIndex++) {
                        for (int colIndex = 0; colIndex < dimensions[1]; colIndex++) {
                            screenPixels[rowIndex][colIndex] = 1;
                        }
                    }

                    break;
                case "rotate":
                    String rowOrCol = args[1].toLowerCase(Locale.ROOT);

                    int amount;
                    switch (rowOrCol) {
                        case "row":
                            int rowIndex = Integer.parseInt(args[2].split("=")[1]);
                            amount = Integer.parseInt(args[4]);

                            for (int amountIndex = 0; amountIndex < amount; amountIndex++) {
                                int previousPixel = screenPixels[screenPixels.length - 1][rowIndex];

                                for (int i = 0; i < screenPixels.length; i++) {
                                    int nextPixel = previousPixel;
                                    previousPixel = screenPixels[i][rowIndex];

                                    screenPixels[i][rowIndex] = nextPixel;

                                }
                            }
                            break;
                        case "column":
                            int colIndex = Integer.parseInt(args[2].split("=")[1]);
                            amount = Integer.parseInt(args[4]);

                            for (int amountIndex = 0; amountIndex < amount; amountIndex++) {
                                int previousPixel = screenPixels[colIndex][screenPixels[colIndex].length - 1];

                                for (int i = 0; i < screenPixels[colIndex].length; i++) {
                                    int nextPixel = previousPixel;
                                    previousPixel = screenPixels[colIndex][i];

                                    screenPixels[colIndex][i] = nextPixel;
                                }
                            }
                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    break;
            }
        }

//        System.out.println(Arrays.stream(screenPixels).map((a) -> Arrays.stream(a).mapToObj((i) -> i > 0 ? "|" : " ").reduce("", (m, s) -> s + m)).reduce("", (m, s) -> m + '\n' + s));

        return Arrays.stream(screenPixels).mapToInt((a) -> Arrays.stream(a).map((i) -> i > 0 ? 1 : 0).reduce(0, Integer::sum)).reduce(0, Integer::sum);
    }
}

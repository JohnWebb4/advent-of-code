/* Licensed under Apache-2.0 */
package advent.year2015.day2;

import java.util.Arrays;

public class ToldNoMath {
    public static double getRequiredWrappingArea(String dimensions) {
        String[] dimensionArr = dimensions.split("\n");
        double requiredArea = 0;

        for (String dimensionStr : dimensionArr) {
            double[] dimension = Arrays.stream(dimensionStr.split("x")).mapToDouble(Float::parseFloat).toArray();

            double side1 = dimension[0] * dimension[1];
            double side2 = dimension[0] * dimension[2];
            double side3 = dimension[1] * dimension[2];

            requiredArea +=  2 * side1 + 2 * side2 + 2 * side3;

            if (side1 <= side2 && side1 <= side3) {
                requiredArea += side1;
            } else if (side2 <= side3) {
                requiredArea += side2;
            } else {
                requiredArea += side3;
            }
        }

        return requiredArea;
    }

    public static double getRequiredRibbonLength(String dimensions) {
        String[] dimensionArr = dimensions.split("\n");
        double requiredLength = 0;

        for (String dimensionStr : dimensionArr) {
            double[] dimension = Arrays.stream(dimensionStr.split("x")).mapToDouble(Float::parseFloat).toArray();

            requiredLength += dimension[0] * dimension[1] * dimension[2];

            double[] sides = Arrays.stream(dimension).sorted().toArray();
            requiredLength += 2 * sides[0] + 2 * sides[1];
        }

        return requiredLength;
    }
}

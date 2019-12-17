/* Licensed under Apache-2.0 */
package advent.year2019.day8;

import java.util.Arrays;
import java.util.function.Function;

public class SpaceImageDecoder {
    public static int getNumber1sTime2sForLayerWithFewestZeros(String encodedImage, int width, int height) {
        String[] layer = getLayerWithFewestZeros(encodedImage, width, height);

        int numOnes = getCharOccurences(layer, "1");
        int numTwos = getCharOccurences(layer, "2");

        return numOnes * numTwos;
    }

    public static String[] getLayerWithFewestZeros(String encodedImage, int width, int height) {
        String[][] image =  decode(encodedImage, width, height);

        int fewestZeros = Integer.MAX_VALUE;
        int indexFewestZeros = 0;

        for (int index = 0; index < image.length; index++) {
            int numZeros = getCharOccurences(image[index], "0");

            if (numZeros < fewestZeros) {
                indexFewestZeros = index;
                fewestZeros = numZeros;
            }
        }

        return image[indexFewestZeros];
    }

    public static int getCharOccurences(String[] layer, String digit) {
        return Arrays.stream(layer).mapToInt(i -> i.split(digit).length - 1).reduce((i, j) -> i + j).getAsInt();
    }

    public static String[][] decode(String encodedImage, int width, int height) {
        String[] layers = encodedImage.split(String.format("((?<=\\G%s))", new String(new char[height * width]).replace("\0", ".")));

        String[][] image = Arrays.stream(layers).map(new Function<String, String[]>() {
            @Override
            public String[] apply(String layer) {
                return layer.split(String.format("((?<=\\G%s))", new String(new char[width]).replace("\0", ".")));
            }
        }).toArray(String[][]::new);

        return image;
    }
}

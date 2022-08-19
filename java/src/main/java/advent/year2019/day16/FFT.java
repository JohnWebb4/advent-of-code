/* Licensed under Apache-2.0 */
package advent.year2019.day16;

import java.util.Arrays;
import java.util.stream.Collectors;

// Assume any repeats * input length is a multiple of the pattern length
public class FFT {
    int[] pattern;

    FFT(int[] pattern) {
        this.pattern = pattern;
    }

    String decodeMessage(String input, int times) {
        int[] inputArray =
                Arrays.stream(input.repeat(10000).split("")).mapToInt(i -> Integer.parseInt(i)).toArray();

        int offsetIndex = Integer.parseInt(input.substring(0, 7)) % inputArray.length;

        int[] result = applyXTimes(inputArray, times, offsetIndex);

        String resultString =
                Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(""));

        // Get and return 8 digit code
        return resultString.repeat(2).substring(offsetIndex, offsetIndex + 8);
    }

    String applyXTimes(String input, int times) {
        int[] inputArray = Arrays.stream(input.split("")).mapToInt(i -> Integer.parseInt(i)).toArray();

        int[] result = applyXTimes(inputArray, times);

        return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(""));
    }

    int[] applyXTimes(int[] input, int times) {
        return applyXTimes(input, times, 0);
    }

    int[] applyXTimes(int[] input, int times, int offsetIndex) {
        int[] result = Arrays.copyOf(input, input.length);

        for (int i = 0; i < times; i++) {
            result = applyFFTPattern(result, this.pattern, offsetIndex);
        }

        return result;
    }

    int[] applyFFTPattern(int[] input, int[] pattern, int offsetIndex) {
        int[] result = new int[input.length];

        for (int i = offsetIndex; i < input.length; i++) {
            int sum = 0;

            if (i <= input.length / 2 || i == offsetIndex) {
                for (int j = i; j < input.length; j++) {
                    int patternIndex = ((j + 1) / (i + 1)) % pattern.length;

                    sum += input[j] * pattern[patternIndex];
                }
            } else {
                sum = result[i - 1] - input[i - 1];
            }

            result[i] = sum;
        }

        for (int i = offsetIndex; i < result.length; i++) {
            result[i] = Math.abs(result[i]) % 10;
        }

        return result;
    }
}

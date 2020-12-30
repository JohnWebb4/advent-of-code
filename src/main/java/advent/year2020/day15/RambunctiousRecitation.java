/* Licensed under Apache-2.0 */
package advent.year2020.day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RambunctiousRecitation {
    public static int getNthSpokenNumber(String input, int n) {
        if (input == null || n < 0) {
            return -1;
        }

        int[] inputs = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
        Map<Integer, Integer> numberToLastIndex = new HashMap<>();

        for (int i = 0; i < inputs.length - 1; i++) {
            if (i < n) {
                numberToLastIndex.put(inputs[i], i + 1);
            }

            if (i == n) {
                return inputs[i];
            }
        }

        int lastValue = inputs.length > 0 ? inputs[inputs.length - 1] : 0;

        for (int i = inputs.length; i < n; i++) {
            int newValue = 0;

            if (numberToLastIndex.containsKey(lastValue)) {
                int lastIndex = numberToLastIndex.get(lastValue);
                newValue = i - lastIndex;
            } else {
                newValue = 0;
            }

            if (lastValue < n) {
                numberToLastIndex.put(lastValue, i);
            }

            lastValue = newValue;
        }

        return lastValue;
    }
}

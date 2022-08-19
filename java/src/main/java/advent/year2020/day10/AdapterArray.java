/* Licensed under Apache-2.0 */
package advent.year2020.day10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdapterArray {
    public static int getVoltageDifferencesProduct(int[] adapters) {
        List<Integer> adaptersList = Arrays.stream(adapters).sorted().boxed().collect(Collectors.toList());

        int oneDiffCount = 0;
        int threeDiffCount = 0;
        int prevValue = 0;

        for (int i = 0; i < adaptersList.size(); i++) {
            int adapter = adaptersList.get(i);
            int diff = adapter - prevValue;

            if (diff == 1) {
                oneDiffCount++;
            } else if (diff == 3) {
                threeDiffCount++;
            }

            prevValue = adapter;
        }

        threeDiffCount++; // Last is always three from charger

        return oneDiffCount * threeDiffCount;
    }

    public static long getPossibleAdapterArrangements(int[] adapters) {
        List<Integer> adaptersList = Arrays.stream(adapters).sorted().boxed().collect(Collectors.toList());
        int outletVoltage = adaptersList.get(adaptersList.size() - 1) + 3;
        Map<Integer, Long> cachedValues = new HashMap<>();

        for (int index = adaptersList.size() - 1; index >= 0; index--) {
            long indexCount = 0;
            int value = adaptersList.get(index);

            if (outletVoltage - value <= 3) {
                indexCount++;
            }

            for (int i = 1; i <= 3; i++) {
                int nextIndex = index + i;

                if (nextIndex < adaptersList.size()) {
                    int nextValue = adaptersList.get(nextIndex);

                    if (nextValue - value <= 3) {
                        indexCount += cachedValues.get(nextIndex);
                    }
                }
            }

            cachedValues.put(index, indexCount);
        }

        long count = 0;
        for (int i = 0; i < 3; i++) {
            if (adaptersList.get(i) <= 3) {
                count += cachedValues.get(i);
            }
        }

        return count;
    }
}
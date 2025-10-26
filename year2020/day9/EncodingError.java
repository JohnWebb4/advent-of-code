/* Licensed under Apache-2.0 */
package advent.year2020.day9;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EncodingError {
	public static long findNonSum(String input, int preambleLength, int memoryLength) {
		long[] numbers = Arrays.stream(input.split("\n")).mapToLong(Long::parseLong).toArray();
		HashMap<Long, Integer> availableNumbersAndCount = new HashMap<>();

		for (int i = 0; i < preambleLength; i++) {
			if (!availableNumbersAndCount.containsKey(numbers[i])) {
				availableNumbersAndCount.put(numbers[i], 1);
			} else {
				Integer count = availableNumbersAndCount.get(numbers[i]);
				availableNumbersAndCount.put(numbers[i], ++count);
			}

			if (i > memoryLength) {
				long numberToForget = numbers[i - memoryLength];

				int count = availableNumbersAndCount.get(numberToForget);

				if (count > 1) {
					availableNumbersAndCount.put(numberToForget, --count);
				} else {
					availableNumbersAndCount.remove(numberToForget);
				}
			}
		}

		for (int i = preambleLength; i < numbers.length; i++) {
			long sum = numbers[i];

			boolean isValid = false;
			for (int j = Math.max(0, i - memoryLength); j < i; j++) {
				long pair1 = numbers[j];
				long pair2 = sum - pair1;
				int pair2Count = 0;
				if (availableNumbersAndCount.containsKey(pair2)) {
					pair2Count = availableNumbersAndCount.get(pair2);
				}

				if (pair1 == pair2 && pair2Count > 1) {
					isValid = true;
					break;
				} else if (pair2Count > 0) {
					isValid = true;
					break;
				}
			}

			if (!isValid) {
				return sum;
			} else if (i > memoryLength) {
				long numberToForget = numbers[i - memoryLength];

				int count = availableNumbersAndCount.get(numberToForget);

				if (count > 1) {
					availableNumbersAndCount.put(numberToForget, --count);
				} else {
					availableNumbersAndCount.remove(numberToForget);
				}
			}

			int count = 0;
			if (availableNumbersAndCount.containsKey(sum)) {
				count = availableNumbersAndCount.get(sum);
			}

			availableNumbersAndCount.put(sum, ++count);
		}

		return -1;
	}

	public static long findWeakness(String input, int preambleLength, int memoryLength) {
		long nonSum = findNonSum(input, preambleLength, memoryLength);
		long[] numbers = Arrays.stream(input.split("\n")).mapToLong(Long::parseLong).toArray();

		int indexNonSum = 0;
		while (numbers[indexNonSum] != nonSum) {
			indexNonSum++;
		}

		List<Long> continuousSum = new LinkedList<>();

		for (int i = indexNonSum - 1; i >= 0; i--) {
			continuousSum.add(numbers[i]);

			long sum = continuousSum.stream().reduce(0L, Long::sum);

			if (sum == nonSum) {
				continuousSum.sort(Long::compareTo);

				return continuousSum.get(0) + continuousSum.get(continuousSum.size() - 1);
			} else if (sum > nonSum) {
				continuousSum.remove(0);
			}
		}

		return 0;
	}
}

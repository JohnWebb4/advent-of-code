/* Licensed under Apache-2.0 */
package advent.year2020.day1;

public class AccountManager {
    static int[] getPairSumToN(int[] numbers, int sum) {
        for (int i = numbers.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (numbers[i] + numbers[j] == sum) {
                    return new int[]{numbers[i], numbers[j]};
                }
            }
        }

        return null;
    }

    static int[] getTrioSumToN(int[] numbers, int sum) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                for (int k = j + 1; k < numbers.length; k++) {
                    if (numbers[i] + numbers[j] + numbers[k] == sum) {
                        return new int[]{numbers[i], numbers[j], numbers[k]};
                    }
                }
            }
        }

        return null;
    }
}

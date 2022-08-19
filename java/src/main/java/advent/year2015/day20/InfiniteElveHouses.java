/* Licensed under Apache-2.0 */
package advent.year2015.day20;

public class InfiniteElveHouses {
    /**
     * Get the lowest house index that has at least total presents
     *
     * @param total minimum number of presents
     * @return index of hourse
     */
    public static int getLowestHouseNumber(long total) {
        long adjustedTotal = (long) Math.ceil(total / 10.0f);

        for (int indexHouse = 1; indexHouse <= total / 2; indexHouse++) {
            long currentTotal = 0;

            for (int i = 1; i <= Math.sqrt(indexHouse); i++) {
                if (indexHouse % i == 0) {
                    int fairPair = indexHouse / i;
                    currentTotal += i;
                    currentTotal += fairPair;
                }
            }

            if (currentTotal >= adjustedTotal) {
                return indexHouse;
            }
        }

        return -1;
    }

    /**
     * Get the lowest house index that has at least total presents
     * Adjusted: Elves will only vist 50 houses and deliver 11 presents.
     *
     * @param total minimum number of presents
     * @return index of hourse
     */
    public static int getLowestHouseNumberAdjusted(long total) {
        long adjustedTotal = (long) Math.ceil(total / 11.0);

        for (int indexHouse = 1; indexHouse <= total / 2; indexHouse++) {
            long currentTotal = 0;

            for (int i = 1; i <= 50 && i <= Math.sqrt(indexHouse); i++) {
                if (indexHouse % i == 0) {
                    int fairPair = indexHouse / i;

                    if (fairPair <= 50) {
                        currentTotal += i;
                    }

                    currentTotal += fairPair;
                }
            }

            if (currentTotal >= adjustedTotal) {
                return indexHouse;
            }
        }

        return -1;
    }
}

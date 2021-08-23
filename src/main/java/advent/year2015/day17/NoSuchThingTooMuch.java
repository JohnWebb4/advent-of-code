/* Licensed under Apache-2.0 */
package advent.year2015.day17;

import java.util.*;
import java.util.stream.Collectors;

public class NoSuchThingTooMuch {
    /**
     * Handles volume combinations
     */
    public static class Combination {
        List<Integer> indexVolumes;

        public static Combination getCombinationFromString(String combinationString) {
            String[] args = combinationString.split("\n");
            List<Integer> combinationVolumes = Arrays.stream(args[0].split(",")).map(Integer::parseInt).collect(Collectors.toList());

            return new Combination(combinationVolumes);
        }

        public Combination(int[] indexVolumes) {
            this.indexVolumes = Arrays.stream(indexVolumes).boxed().collect(Collectors.toList());
        }

        public Combination(List<Integer> indexVolumes) {
            this.indexVolumes = indexVolumes;
        }

        public String getCombinationString() {
            return String.join(",", this.indexVolumes.stream().map((v) -> Integer.toString(v)).toArray(String[]::new));
        }

        public int getVolume(int[] containerVolumes) {
            return indexVolumes.stream().reduce(0, (vt, i) -> vt + containerVolumes[i]);
        }
    }

    /**
     * Get unique combinations of containers of varying sizes that added together
     * exactly contain the required volume.
     * Assume no negative volumes.
     *
     * @param requiredExactVolume  The exact volume the containers must have
     * @param containerSizesString Newline terminated string of container volumes
     * @return Number of combinations of provided container volumes
     */
    public static long getCountContainerCombinations(int requiredExactVolume, String containerSizesString) {
        // Get volumes sorted (1, 2, 3, 4, 5, etc.)
        int[] containerVolumes = Arrays.stream(containerSizesString.split("\n")).mapToInt(Integer::parseInt).sorted().toArray();
        long numCombinations = 0;

        Stack<String> combinationsToCheck = new Stack<>();

        for (int i = 0; i < containerVolumes.length; i++) {
            combinationsToCheck.add(new Combination(new int[]{i}).getCombinationString());
        }

        while (!combinationsToCheck.empty()) {
            String combinationString = combinationsToCheck.pop();
            Combination combination = Combination.getCombinationFromString(combinationString);

            int volume = combination.getVolume(containerVolumes);

            if (volume == requiredExactVolume) {
                numCombinations += 1;
            }

            if (volume < requiredExactVolume) {
                int maxIndex = combination.indexVolumes.stream().max(Comparator.naturalOrder()).orElse(0);

                for (int i = maxIndex + 1; i < containerVolumes.length; i++) {
                    if (volume + containerVolumes[i] <= requiredExactVolume) {
                        List<Integer> newIndexVolumes = new ArrayList<Integer>(combination.indexVolumes);
                        newIndexVolumes.add(i);

                        combinationsToCheck.add(new Combination(newIndexVolumes).getCombinationString());
                    }
                }
            }
        }

        return numCombinations;
    }

    /**
     * Get unique combinations of containers of varying sizes that added together
     * exactly contain the required volume. Minimize bottles used.
     * Assume no negative volumes.
     *
     * @param requiredExactVolume  The exact volume the containers must have
     * @param containerSizesString Newline terminated string of container volumes
     * @return Number of combinations of provided container volumes
     */
    public static long getMinCountContainerCombinations(int requiredExactVolume, String containerSizesString) {
        // Get volumes sorted (1, 2, 3, 4, 5, etc.)
        int[] containerVolumes = Arrays.stream(containerSizesString.split("\n")).mapToInt(Integer::parseInt).sorted().toArray();
        long numCombinations = 0;
        long minContainersUsed = Long.MAX_VALUE;

        Stack<String> combinationsToCheck = new Stack<>();

        for (int i = 0; i < containerVolumes.length; i++) {
            combinationsToCheck.add(new Combination(new int[]{i}).getCombinationString());
        }

        while (!combinationsToCheck.empty()) {
            String combinationString = combinationsToCheck.pop();
            Combination combination = Combination.getCombinationFromString(combinationString);

            int volume = combination.getVolume(containerVolumes);
            int numContainersUsed = combination.indexVolumes.size();

            if (volume == requiredExactVolume) {

                if (numContainersUsed == minContainersUsed) {
                    numCombinations += 1;
                } else if (numContainersUsed < minContainersUsed) {
                    numCombinations = 1;
                    minContainersUsed = numContainersUsed;
                }
            }

            if (volume < requiredExactVolume && numContainersUsed < minContainersUsed) {
                int maxIndex = combination.indexVolumes.stream().max(Comparator.naturalOrder()).orElse(0);

                for (int i = maxIndex + 1; i < containerVolumes.length; i++) {
                    if (volume + containerVolumes[i] <= requiredExactVolume) {
                        List<Integer> newIndexVolumes = new ArrayList<Integer>(combination.indexVolumes);
                        newIndexVolumes.add(i);

                        combinationsToCheck.add(new Combination(newIndexVolumes).getCombinationString());
                    }
                }
            }
        }

        return numCombinations;
    }
}

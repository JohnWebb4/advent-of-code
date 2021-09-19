/* Licensed under Apache-2.0 */
package advent.year2015.day24;

import java.util.*;
import java.util.stream.Collectors;

public class HangsInBalance {
    public static class PackageConfiguration implements Comparable<PackageConfiguration> {
        public final int[] toSort;
        public final int[] passengerPackageWeights;

        public static class Builder {
            int[] toSort;
            int[] passengerPackageWeights;

            public Builder setToSort(int[] toSort) {
                this.toSort = toSort;
                return this;
            }

            public Builder setPassengerPackageWeights(int[] passengerPackageWeights) {
                this.passengerPackageWeights = passengerPackageWeights;
                return this;
            }

            public PackageConfiguration build() {
                return new PackageConfiguration(toSort, passengerPackageWeights);
            }
        }

        public PackageConfiguration(int[] toSort, int[] passengerPackageWeights) {
            this.toSort = toSort;
            this.passengerPackageWeights = passengerPackageWeights;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        @Override
        public int compareTo(PackageConfiguration o) {
            return this.passengerPackageWeights.length - o.passengerPackageWeights.length;
        }

        public boolean isValid(int numGroups) {
            int passengerSum = Arrays.stream(this.passengerPackageWeights).sum();
            int toSortSum = Arrays.stream(this.toSort).sum();

            if ((float) toSortSum / (numGroups - 1) == passengerSum) {
                return true;
            }

            return false;
        }

        public long getPassengerQuantamEntanglement() {
            return Arrays.stream(this.passengerPackageWeights).mapToLong(Long::valueOf).reduce(1L, (m1, m2) -> m1 * m2);
        }

        public String getKey() {
            return String.join(",", Arrays.stream(this.passengerPackageWeights).mapToObj(String::valueOf).sorted().toArray(String[]::new));
        }
    }

    public static long getIdealQuantamEntanglementForGroups(String weightString, int numGroups) {
        int[] packageWeights = Arrays.stream(weightString.split("\n")).mapToInt(Integer::parseInt).sorted().toArray();

        PriorityQueue<PackageConfiguration> configurationsToCheck = new PriorityQueue<>();
        Set<String> passengerSet = new HashSet<>();

        configurationsToCheck.add(PackageConfiguration.getBuilder()
                .setToSort(packageWeights)
                .setPassengerPackageWeights(new int[]{})
                .build());

        int minPassengerPackages = packageWeights.length / numGroups;
        long minValidQuantamEntanglement = Long.MAX_VALUE;
        int i = 0;
        while (configurationsToCheck.size() > 0) {
            PackageConfiguration configuration = configurationsToCheck.poll();

            if (configuration.passengerPackageWeights.length > minPassengerPackages) {
                continue;
            }

            if (configuration.isValid(numGroups)) {
                // All weights equal and assigned all packages
                long passengerQuantamEntanglement = configuration.getPassengerQuantamEntanglement();

                if (configuration.passengerPackageWeights.length < minPassengerPackages) {
                    minPassengerPackages = configuration.passengerPackageWeights.length;
                    minValidQuantamEntanglement = configuration.getPassengerQuantamEntanglement();
                } else if (passengerQuantamEntanglement < minValidQuantamEntanglement) {
                    minValidQuantamEntanglement = passengerQuantamEntanglement;
                }
            } else if (configuration.toSort.length > 0 && configuration.passengerPackageWeights.length < minPassengerPackages) {
                List<Integer> toSortList = Arrays.stream(configuration.toSort).boxed().collect(Collectors.toCollection(LinkedList::new));

                for (int nextWeight : toSortList) {
                    List<Integer> nextToSort = new LinkedList<>(toSortList);
                    nextToSort.remove(Integer.valueOf(nextWeight));
                    int[] newToSort = nextToSort.stream().mapToInt(Integer::intValue).toArray();

                    List<Integer> nextPassenger = Arrays.stream(configuration.passengerPackageWeights).boxed().collect(Collectors.toList());
                    nextPassenger.add(nextWeight);
                    int[] newPassenger = nextPassenger.stream().mapToInt(Integer::intValue).toArray();

                    PackageConfiguration nextConfiguration = PackageConfiguration.getBuilder()
                            .setToSort(newToSort)
                            .setPassengerPackageWeights(newPassenger)
                            .build();

                    String nextKey = nextConfiguration.getKey();
                    if (passengerSet.contains(nextKey)) {
                        continue;
                    }

                    passengerSet.add(nextKey);

                    configurationsToCheck.add(nextConfiguration);
                }
            }
        }

        return minValidQuantamEntanglement;
    }
}

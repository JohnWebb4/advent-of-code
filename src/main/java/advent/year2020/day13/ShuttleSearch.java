/* Licensed under Apache-2.0 */
package advent.year2020.day13;

import java.util.Arrays;

public class ShuttleSearch {
    public static int getFirstBusIdTimesWait(int startTime, String services) {
        int[] buses = Arrays.stream(services.split(",")).filter((s) ->
                !s.equals("x")).mapToInt((s) -> Integer.parseInt(s)).toArray();

        int shortestWait = -1;
        int busId = 0;

        for (int bus : buses) {
            int wait = bus - (startTime % bus);

            if (shortestWait != -1) {
                if (shortestWait > wait) {
                    shortestWait = wait;
                    busId = bus;
                }
            } else {
                shortestWait = wait;
                busId = bus;
            }
        }

        return busId * shortestWait;
    }

    public static long getEarliestTimeDepartOffsets(String services) {
        long[] buses = Arrays.stream(services.split(",")).mapToLong((s) -> {
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return 1;
            }
        }).toArray();

        long count = buses[0];
        long multiplier = buses[0];

        for (int i = 0; i < buses.length - 1; i++) {
            long bus = buses[i + 1];

            for (long multiple = +count; ; multiple += multiplier) {
                if ((multiple + i + 1) % bus == 0) {
                    count = multiple;
                    multiplier = multiplier * bus;
                    break;
                }
            }
        }

        return count;
    }
}

/* Licensed under Apache-2.0 */
package advent.year2016.day15;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimingEverything {
    public static class Disc {
        public final int discNum;
        public final int numPositions;
        public final int startPosition;

        public Disc(int discNum, int numPositions, int startPosition) {
            this.discNum = discNum;
            this.numPositions = numPositions;
            this.startPosition = startPosition;
        }
    }

    public static final Pattern DISC_PATTERN = Pattern.compile("Disc #(\\d+) has (\\d+) positions; at time=(\\d+), it is at position (\\d+).");

    public static int getFirstTimeToPassCapsule(String input) {
        String[] lines = input.split("\n");

        Disc[] discs = Arrays.stream(lines).map((line) -> {
            Matcher lineMatcher = DISC_PATTERN.matcher(line);
            if (lineMatcher.find()) {
                int discNum = Integer.parseInt(lineMatcher.group(1));
                int numPositions = Integer.parseInt(lineMatcher.group(2));
                int startTime = Integer.parseInt(lineMatcher.group(3));
                int startPosition = Integer.parseInt(lineMatcher.group(4));

                startPosition -= startTime;

                return new Disc(discNum, numPositions, startPosition);
            } else {
                throw new IllegalArgumentException();
            }
        }).sorted(Comparator.comparingInt((disc) -> disc.discNum)).toArray(Disc[]::new);

        for (int startTime = 0; startTime < 4000000; startTime++) {
            boolean shouldFallThrough = true;

            for (int discIndex = 0; discIndex < discs.length; discIndex++) {
                int positionAtTime = startTime + discIndex + 1 + discs[discIndex].startPosition;
                positionAtTime = positionAtTime % discs[discIndex].numPositions;

                if (positionAtTime != 0) {
                    shouldFallThrough = false;
                    break;
                }
            }

            if (shouldFallThrough) {
                return startTime;
            }
        }

        return 0;
    }
}

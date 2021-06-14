/* Licensed under Apache-2.0 */
package advent.year2015.day14;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReindeerOlympics {
    public static class Reindeer implements Comparable {
        public final String name;
        public final int speed;
        public final int runDuration;
        public final int restTime;
        int distance;
        int points;
        int loopedTime;

        public Reindeer(String name, int speed, int runDuration, int restTime) {
            this(name, speed, runDuration, restTime, 0, 0, 0);
        }

        public Reindeer(String name, int speed, int runDuration, int restTime, int time, int distance, int points) {
            this.name = name;
            this.speed = speed;
            this.runDuration = runDuration;
            this.restTime = restTime;
            this.loopedTime = time % (runDuration + restTime);
            this.distance = 0;
            this.points = points;
        }

        public void walk() {
            if (this.loopedTime < this.runDuration) {
                this.distance += this.speed;
            }

            this.loopedTime = (this.loopedTime + 1) % (this.runDuration + restTime);
        }

        public void incrementPoints() {
            points++;
        }

        @Override
        public int compareTo(Object o) {
            Reindeer r = (Reindeer) o;

            return Integer.compare(r.distance, this.distance);
        }
    }

    public static Pattern reindeerStatisticsPattern = Pattern.compile("^(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.$");

    public static int getFarthestDistanceReindeerTraveled(String rulesString, int duration) {
        String[] rulesArray = rulesString.split("\n");
        int maxDistanceTraveled = 0;

        for (String ruleString : rulesArray) {
            Matcher statisticsMatcher = reindeerStatisticsPattern.matcher(ruleString);

            statisticsMatcher.find();
            int speed = Integer.parseInt(statisticsMatcher.group(2));
            int runDuration = Integer.parseInt(statisticsMatcher.group(3));
            int restTime = Integer.parseInt(statisticsMatcher.group(4));

            int totalTime = runDuration + restTime;
            int numLoopsCompleted = Math.floorDiv(duration, totalTime);
            int remainderTime = duration % totalTime;

            int distance = (numLoopsCompleted * runDuration + Math.min(runDuration, remainderTime)) * speed;

            if (distance > maxDistanceTraveled) {
                maxDistanceTraveled = distance;
            }
        }

        return maxDistanceTraveled;
    }

    public static int getMostPointsAfterDuration(String rulesString, int duration) {
        String[] rulesArray = rulesString.split("\n");
        List<Reindeer> reindeerList = new LinkedList<>();

        for (String ruleString : rulesArray) {
            Matcher statisticsMatcher = reindeerStatisticsPattern.matcher(ruleString);

            statisticsMatcher.find();
            String name = statisticsMatcher.group(1);
            int speed = Integer.parseInt(statisticsMatcher.group(2));
            int runDuration = Integer.parseInt(statisticsMatcher.group(3));
            int restTime = Integer.parseInt(statisticsMatcher.group(4));

            reindeerList.add(new Reindeer(name, speed, runDuration, restTime));
        }

        for (int i = 0; i < duration; i++) {
            for (Reindeer r : reindeerList) {
                r.walk();
            }

            reindeerList = reindeerList.stream().sorted().collect(Collectors.toList());
            final List<Reindeer> finalReindeerList = reindeerList;
            List<Reindeer> farthestReindeers = reindeerList.stream().filter((r) -> r.distance == finalReindeerList.get(0).distance).collect(Collectors.toList());

            for (Reindeer r : farthestReindeers) {
                r.incrementPoints();
            }
        }

        return reindeerList.stream().map((r) -> r.points).max(Integer::compare).get();
    }
}

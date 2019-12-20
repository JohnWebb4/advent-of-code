/* Licensed under Apache-2.0 */
package advent.year2019.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MonitoringStationBuilder {
  final static double EPSILON = 0.01;
  String[][] map;
  ArrayList<int[]> asteroids;

  public MonitoringStationBuilder(String map) {
    this.map =
        transpose(Arrays.stream(map.split("\n"))
            .map(row -> row.split("((?<=\\G.))"))
            .toArray(String[][]::new));

    this.asteroids = MonitoringStationBuilder.getAsteroids(this.map);
  }

  protected static ArrayList<int[]> getAsteroids(String[][] map) {
    ArrayList<int[]> asteroids = new ArrayList<>();

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        if (map[x][y].equals("#")) {
          asteroids.add(new int[] { x, y });
        }
      }
    }

    return asteroids;
  }

  protected static String[][] transpose(String[][] map) {
    String[][] copyMap = new String[map[0].length][map.length];

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        copyMap[y][x] = map[x][y];
      }
    }

    return copyMap;
  }

  public static double getAngle(int[] station, int[] asteroid) {
    int xDiff = asteroid[0] - station[0];
    int yDiff = asteroid[1] - station[1];

    double angle = Math.atan2(yDiff, xDiff);

    if (angle < 0) {
      angle += 2 * Math.PI;
    }

    return angle;
  }

  public int[] getNthVaporizedAsteroid(int[] station, int numAsteroid) {
    int countDestroyed = 0;
    int maxDistance = Math.max(map.length, map[0].length);

    for (int numLoops = 0; numLoops < 10; numLoops++) {
      ArrayList<int[]> alreadySeen = new ArrayList<int[]>();

      for (double angle = Math.PI / 2; angle > -3 * Math.PI / 2; angle -= 0.001) {
        boolean hasDestroyed = false;

        for (double distance = 1; distance < maxDistance; distance += 0.001) {
          double xDiff = Math.cos(angle) * distance;
          double yDiff = -Math.sin(angle) * distance;

          double xDouble = station[0] + xDiff;
          double yDouble = station[1] + yDiff;

          int x = (int)Math.round(xDouble);
          int y = (int)Math.round(yDouble);

          if (Math.abs(xDouble - x) < EPSILON && Math.abs(yDouble - y) < EPSILON) {
            try {
              int[][] alreadySeenArr = new int[alreadySeen.size()][2];
              alreadySeen.toArray(alreadySeenArr);
              Optional<Boolean> hasSeen = Arrays.stream(alreadySeenArr).map(asteroid -> asteroid[0] == x && asteroid[1] == y).reduce((currentSeen, hasAlreadySeen) -> currentSeen || hasAlreadySeen);

              if (hasSeen.equals(true)) {
                continue;
              }

              if (map[x][y].equals("#")) {
                if (!hasDestroyed) {
                  map[x][y] = ".";

                  countDestroyed++;

                  if (countDestroyed == numAsteroid) {
                    return new int[] { x, y };
                  }

                  hasDestroyed = true;
                } else {
                  alreadySeen.add(new int[] { x, y });
                }
              }
            } catch (IndexOutOfBoundsException e) {}
          }
        }
      }
    }

    return null;
  }

  public int[] getStationWithBestCoverage() {
    int[] stationPosition = null;
    int maxVisibleAsteroids = -1;

    for (int[] asteroid : this.asteroids) {
      int visibleAsteroids = getVisibleAsteroids(asteroid);

      if (visibleAsteroids > maxVisibleAsteroids) {
        stationPosition = asteroid;
        maxVisibleAsteroids = visibleAsteroids;
      }
    }

    return stationPosition;
  }

  public int getVisibleAsteroids(int[] position) {
    int visibleAsteroids = 0;

    for (int[] asteroid : this.asteroids) {
      if (asteroid[0] != position[0] || asteroid[1] != position[1]) {
        boolean isBlocked = false;

        if (Math.abs(asteroid[0] - position[0]) == 0) {
          // If vertical
          double yDist = asteroid[1] - position[1];
          int stepAmount = yDist > 0 ? 1 : -1;

          for (int step = stepAmount; Math.abs(step) < Math.abs(yDist); step += stepAmount) {
            int y = position[1] + step;

            if (map[position[0]][y].equals("#")) {
              isBlocked = true;
              break;
            }
          }
        } else {
          double slope = getSlope(position, asteroid);
          double yIntercept = getIntercept(position, slope);
          double xDist = asteroid[0] - position[0];
          int stepAmount = xDist > 0 ? 1 : -1;

          for (int step = stepAmount; Math.abs(step) < Math.abs(xDist); step += stepAmount) {
            int x = step + position[0];
            double y = slope * x + yIntercept;

            // If landed on a valid block and asteroid
            if (Math.abs(y - Math.round(y)) < EPSILON) {
              if (map[x][(int)y].equals("#")) {
                isBlocked = true;
                break;
              }
            }
          }
        }


        if (!isBlocked) {
          visibleAsteroids++;
        }
      }
    }

    return visibleAsteroids;
  }

  public double getSlope(int[] start, int[] finish) {
    return (finish[1] - start[1]) / ((double) finish[0] - start[0]);
  }

  public double getIntercept(int[] start, double slope) {
    return start[1] - slope * start[0];
  }
}

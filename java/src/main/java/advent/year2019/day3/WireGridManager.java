/* Licensed under Apache-2.0 */
package advent.year2019.day3;

import java.util.ArrayList;
import java.util.HashMap;

public class WireGridManager {
    final HashMap<Integer, HashMap<Integer, WireCell>> grid =
            new HashMap<Integer, HashMap<Integer, WireCell>>();
    ArrayList<String[]> lines = new ArrayList<String[]>();
    protected int lineIndex = 0;

    public void drawNewLine(String[] drawCommands) {
        ++lineIndex;
        int xIndex = 0;
        int yIndex = 0;
        int distanceTraveled = 0;

        for (String drawCommand : drawCommands) {
            char direction = drawCommand.toLowerCase().charAt(0);
            int length = Integer.parseInt(drawCommand.substring(1));

            int xDirection = 0 ;
            int yDirection = 0;

            if (direction == 'r') {
                xDirection = 1;
            } else if (direction == 'u') {
                yDirection = 1;

            } else if (direction == 'l') {
                xDirection = -1;
            } else {
                yDirection = -1;
            }

            for (int i = 0; i < length + 1; i++) {
                HashMap<Integer, WireCell> row = grid.get(xIndex);

                if (row == null) {
                    row = new HashMap<Integer, WireCell>();
                }

                WireCell cell = row.get(yIndex);

                if (cell == null) {
                    cell = new WireCell();
                }

                if (!cell.lines.contains(lineIndex)) {
                    cell.combinedDistance += distanceTraveled;
                }

                cell.lines.add(lineIndex);
                row.put(yIndex, cell);
                grid.put(xIndex, row);

                if (i < length) {
                    xIndex += xDirection;
                    yIndex += yDirection;
                    distanceTraveled++;
                }
            }
        }

        lines.add(drawCommands);
    }

    public int[][] getIntersections() {
        ArrayList<int[]> intersectionPoints = new ArrayList<int[]>();

        for (int xIndex : grid.keySet()) {
            HashMap<Integer, WireCell> row = grid.get(xIndex);

            if (row != null) {
                for (int yIndex : row.keySet()) {
                    if (xIndex == 0 && yIndex == 0) {
                        continue;
                    }

                    WireCell cell = row.get(yIndex);

                    if (cell != null) {
                        if (cell.lines.size() == lineIndex) {
                            intersectionPoints.add(new int[] { xIndex, yIndex, cell.combinedDistance });
                        }
                    }
                }
            }
        }

        return intersectionPoints.toArray(new int[][] {});
    }

    public int getIntersectionClosestToCenter() {
        int closestDistanceToCenter = Integer.MAX_VALUE;

        int[][] intersectionPoints = getIntersections();

        for (int[] intersection : intersectionPoints) {
            int distance = Math.abs(intersection[0]) + Math.abs(intersection[1]);

            if (distance < closestDistanceToCenter) {
                closestDistanceToCenter = distance;
            }
        }

        return closestDistanceToCenter;
    }

    public int getDistanceToEarliestIntersection() {
      int distanceToFirst = Integer.MAX_VALUE;
      int[][] intersectionPoints = getIntersections();

      for (int[] intersection : intersectionPoints) {
          if (intersection[2] < distanceToFirst) {
              distanceToFirst = intersection[2];
          }
      }

      return distanceToFirst;
    }
}

/* Licensed under Apache-2.0 */
package advent.year2016.day1;

import java.util.HashSet;
import java.util.Set;

public class NoTimeTaxicab {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;


    public static int howManyBlocksToHQ(String instructions) {
        int x = 0;
        int y = 0;
        int direction = NORTH;

        for (String instruction : instructions.split(",")) {
            instruction = instruction.trim();
            String instDir = instruction.substring(0, 1);
            int amount = Integer.parseInt(instruction.substring(1));

            switch (instDir) {
                case "R":
                    direction = ++direction % 4;
                    break;
                case "L":
                    --direction;
                    if (direction < 0) {
                        direction = 4 + direction;
                    }
                    break;
                default:
                    break;
            }

            switch (direction) {
                case NORTH:
                    y += amount;
                    break;
                case EAST:
                    x += amount;
                    break;
                case SOUTH:
                    y -= amount;
                    break;
                case WEST:
                    x -= amount;
                    break;
                default:
                    break;
            }
        }


        return Math.abs(x) + Math.abs(y);
    }

    public static int howManyBlocksToFirstLocationTwice(String instructions) {
        int x = 0;
        int y = 0;
        int direction = NORTH;
        Set<String> locationsVisited = new HashSet<>();
        locationsVisited.add("0,0");

        for (String instruction : instructions.split(",")) {
            instruction = instruction.trim();
            String instDir = instruction.substring(0, 1);
            int amount = Integer.parseInt(instruction.substring(1));

            switch (instDir) {
                case "R":
                    direction = ++direction % 4;
                    break;
                case "L":
                    --direction;
                    if (direction < 0) {
                        direction = 4 + direction;
                    }
                    break;
                default:
                    break;
            }

            for (int i = 0; i < amount; i++) {
                switch (direction) {
                    case NORTH:
                        y++;
                        break;
                    case EAST:
                        x++;
                        break;
                    case SOUTH:
                        y--;
                        break;
                    case WEST:
                        x--;
                        break;
                    default:
                        break;
                }

                String locationString = String.format("%s,%s", x, y);
                if (locationsVisited.contains(locationString)) {
                    return Math.abs(x) + Math.abs(y);
                } else {
                    locationsVisited.add(locationString);
                }
            }


        }

        return Math.abs(x) + Math.abs(y);
    }
}

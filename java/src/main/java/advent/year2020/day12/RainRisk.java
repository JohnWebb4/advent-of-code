/* Licensed under Apache-2.0 */
package advent.year2020.day12;

public class RainRisk {
    public static int getManhattanDistanceFromStart(String input) {
        String direction = "E";
        int y = 0;
        int x = 0;

        String[] commands = input.split("\n");

        for (String command : commands) {
            String type = command.substring(0, 1);
            int count = Integer.parseInt(command.substring(1));

            int turns;
            switch (type) {
                case "N":
                    y += count;
                    break;
                case "S":
                    y -= count;
                    break;
                case "E":
                    x += count;
                    break;
                case "W":
                    x -= count;
                    break;
                case "L":
                    turns = -count / 90;

                    direction = turnShip(direction, turns);
                    break;
                case "R":
                    turns = count / 90;

                    direction = turnShip(direction, turns);
                    break;
                case "F":
                    switch (direction) {
                        case "N":
                            y += count;
                            break;
                        case "S":
                            y -= count;
                            break;
                        case "E":
                            x += count;
                            break;
                        case "W":
                            x -= count;
                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    break;
            }
        }

        return Math.abs(y) + Math.abs(x);
    }

    public static String turnShip(String direction, int turns) {
        String[] cardinalDirections = new String[]{"N", "E", "S", "W"};
        int index = 0;

        for (int i = 0; i < cardinalDirections.length; i++) {
            if (cardinalDirections[i].equals(direction)) {
                index = i;
                break;
            }
        }

        index += turns;
        index = index % cardinalDirections.length;

        if (index < 0) {
            index += 4;
        }

        return cardinalDirections[index];
    }

    public static int getManhattanDistanceFromStartWithWaypoint(String input) {
        String direction = "E";
        int y = 0;
        int x = 0;
        int wayX = 10;
        int wayY = 1;

        String[] commands = input.split("\n");

        for (String command : commands) {
            String type = command.substring(0, 1);
            int count = Integer.parseInt(command.substring(1));

            int turns;
            int[] newWayCords;
            switch (type) {
                case "N":
                    wayY += count;
                    break;
                case "S":
                    wayY -= count;
                    break;
                case "E":
                    wayX += count;
                    break;
                case "W":
                    wayX -= count;
                    break;
                case "L":
                    turns = -count / 90;

                    newWayCords = turnWaypoint(wayX, wayY, turns);
                    wayX = newWayCords[0];
                    wayY = newWayCords[1];
                    break;
                case "R":
                    turns = count / 90;

                    newWayCords = turnWaypoint(wayX, wayY, turns);
                    wayX = newWayCords[0];
                    wayY = newWayCords[1];
                    break;
                case "F":
                    y += wayY * count;
                    x += wayX * count;

                    break;
                default:
                    break;
            }
        }

        return Math.abs(y) + Math.abs(x);
    }

    public static int[] turnWaypoint(int wayX, int wayY, int turns) {
        turns = turns % 4;
        if (turns < 0) {
            turns += 4;
        }

        int newWayX;
        int newWayY;

        switch (turns) {
            case 1:
                newWayX = wayY;
                newWayY = -wayX;
                break;
            case 2:
                newWayX = -wayX;
                newWayY = -wayY;
                break;
            case 3:
                newWayX = -wayY;
                newWayY = wayX;
                break;
            default:
                newWayX = wayX;
                newWayY = wayY;
                break;

        }

        return new int[]{newWayX, newWayY};
    }
}

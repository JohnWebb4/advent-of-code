/* Licensed under Apache-2.0 */
package advent.year2015.day3;

import java.util.HashSet;
import java.util.Set;

public class SphereVacuumHouse {
    public static int getCountHousesWithPresents(String instructions) {
        Set<String> visitedHouses = new HashSet<>();
        int posX = 0;
        int posY = 0;

        visitedHouses.add(String.format("%s,%s", posX, posY));

        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case '^':
                    posY++;
                    break;
                case 'v':
                    posY--;
                    break;
                case '<':
                    posX++;
                    break;
                case '>':
                    posX--;
                    break;
                default:
                    break;
            }

            visitedHouses.add(String.format("%s,%s", posX, posY));
        }

        return visitedHouses.size();
    }

    public static int getCountHousesWithPresentsWithRobot(String instructions) {
        Set<String> visitedHouses = new HashSet<>();
        int posX = 0;
        int posY = 0;

        int roboPosX = 0;
        int roboPosY = 0;

        boolean isRobotTurn = false;

        visitedHouses.add(String.format("%s,%s", posX, posY));

        for (char instruction : instructions.toCharArray()) {
            if (isRobotTurn) {
                switch (instruction) {
                    case '^':
                        roboPosY++;
                        break;
                    case 'v':
                        roboPosY--;
                        break;
                    case '<':
                        roboPosX++;
                        break;
                    case '>':
                        roboPosX--;
                        break;
                    default:
                        break;
                }

                visitedHouses.add(String.format("%s,%s", roboPosX, roboPosY));
            } else {
                switch (instruction) {
                    case '^':
                        posY++;
                        break;
                    case 'v':
                        posY--;
                        break;
                    case '<':
                        posX++;
                        break;
                    case '>':
                        posX--;
                        break;
                    default:
                        break;
                }

                visitedHouses.add(String.format("%s,%s", posX, posY));
            }

            isRobotTurn = !isRobotTurn;
        }

        return visitedHouses.size();
    }
}

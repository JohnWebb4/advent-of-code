/* Licensed under Apache-2.0 */
package advent.year2015.day1;

public class NotQuiteLisp {
    public static int getFloor(String instructions) {
        int floor = 0;

        if (instructions != null) {
            for (char c : instructions.toCharArray()) {
                if (c == '(') {
                    floor++;
                } else if (c == ')') {
                    floor--;
                }
            }
        }

        return floor;
    }

    public static int getIndexFirstEnterBasement(String instructions) {
        int floor = 0;

        if (instructions != null) {
            for (int i = 0; i < instructions.length(); i++) {
                char c = instructions.charAt(i);

                if (c == '(') {
                    floor++;
                } else if (c == ')') {
                    floor--;
                }

                if (floor == -1) {
                    return i + 1;
                }
            }
        }

        return -1;
    }
}

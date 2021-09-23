/* Licensed under Apache-2.0 */
package advent.year2016.day2;

public class BathroomSecurity {
    public final static String[][] keypad = new String[][]{
            new String[]{"1", "2", "3"},
            new String[]{"4", "5", "6"},
            new String[]{"7", "8", "9"},
    };

    public final static String[][] keypadAdvanced = new String[][]{
            new String[]{null, null, "1", null, null},
            new String[]{null, "2", "3", "4", null},
            new String[]{"5", "6", "7", "8", "9"},
            new String[]{null, "A", "B", "C", null},
            new String[]{null, null, "D", null, null}
    };

    public static int getCode(String codeInstruction) {
        String[] numberInstructions = codeInstruction.split("\n");

        StringBuilder code = new StringBuilder();
        int x = 1;
        int y = 1;
        for (String instructionString : numberInstructions) {
            String[] instructions = instructionString.split("");

            for (String instruction : instructions) {
                int nextX = x;
                int nextY = y;

                switch (instruction) {
                    case "U":
                        nextY--;
                        break;
                    case "L":
                        nextX--;
                        break;
                    case "D":
                        nextY++;
                        break;
                    case "R":
                        nextX++;
                        break;
                    default:
                        break;
                }

                nextX = Math.min(keypad[0].length - 1, Math.max(0, nextX));
                nextY = Math.min(keypad.length - 1, Math.max(0, nextY));

                if (keypad[nextY][nextX] != null) {
                    x = nextX;
                    y = nextY;
                }
            }

            code.append(keypad[y][x]);
        }

        return Integer.parseInt(code.toString());
    }

    public static String getAdvancedCode(String codeInstruction) {
        String[] numberInstructions = codeInstruction.split("\n");

        StringBuilder code = new StringBuilder();
        int x = 0;
        int y = 2;
        for (String instructionString : numberInstructions) {
            String[] instructions = instructionString.split("");

            for (String instruction : instructions) {
                int nextX = x;
                int nextY = y;

                switch (instruction) {
                    case "U":
                        nextY--;
                        break;
                    case "L":
                        nextX--;
                        break;
                    case "D":
                        nextY++;
                        break;
                    case "R":
                        nextX++;
                        break;
                    default:
                        break;
                }

                nextX = Math.min(keypadAdvanced[0].length - 1, Math.max(0, nextX));
                nextY = Math.min(keypadAdvanced.length - 1, Math.max(0, nextY));

                if (keypadAdvanced[nextY][nextX] != null) {
                    x = nextX;
                    y = nextY;
                }
            }

            code.append(keypadAdvanced[y][x]);
        }

        return code.toString();
    }
}

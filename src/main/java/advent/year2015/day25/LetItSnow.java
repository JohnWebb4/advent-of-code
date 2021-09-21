/* Licensed under Apache-2.0 */
package advent.year2015.day25;

public class LetItSnow {
    /**
     * Get the initilization code at the 1 based row and column index
     * @param rowIndex row index starting at one
     * @param colIndex column index starting at one
     * @return code at position
     */
    public static long getCodeAtPosition(int rowIndex, int colIndex) {
        long value = 20151125L;

        int cellPosition = 1;

        // Get row
        int step = 1;
        for (int i = 1; i < rowIndex; i++) {
            cellPosition += step;
            step++;
        }

        // Get col
        step = rowIndex + 1;
        for (int i = 1; i < colIndex; i++) {
            cellPosition += step;
            step++;
        }

        for (int i = 1; i < cellPosition; i++) {
            long nextValue = value * 252533;
            value = nextValue % 33554393;
        }

        return value;
    }
}

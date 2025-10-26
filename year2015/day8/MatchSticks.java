/* Licensed under Apache-2.0 */
package advent.year2015.day8;

public class MatchSticks {
    public static long getCodeMinusMemoryCharacters(String input) {
        long count = 0;

        for (String row : input.split("\n")) {
            count += 2 + row.length() - row.replaceAll("\\\\((x[\\da-f]{2})|.)", "\\\\").length();
        }

        return count;
    }

    public static long getEncodedMinusMemoryCharacters(String input) {
        long count = 0;

        for (String row : input.split("\n")) {
            count += 2 + row.replaceAll("[^\"\\\\]+", "").length();
        }

        return count;
    }
}

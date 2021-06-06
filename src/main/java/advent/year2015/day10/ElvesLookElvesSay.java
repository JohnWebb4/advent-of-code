/* Licensed under Apache-2.0 */
package advent.year2015.day10;

public class ElvesLookElvesSay {
    public static String playLookSayNTimes(String initial, int iterations) {
        String currentString = initial;

        for (int i = 0; i < iterations; i++) {
            String previousDigit = "";
            int digitCount = 0;
            StringBuilder sb = new StringBuilder();

            for (String s : currentString.split("")) {
                if (previousDigit.equals(s)) {
                    digitCount++;
                } else if (!previousDigit.equals("")) {
                    sb.append(digitCount);
                    sb.append(previousDigit);
                }

                if (!previousDigit.equals(s)) {
                    previousDigit = s;
                    digitCount = 1;
                }
            }

            sb.append(digitCount);
            sb.append(previousDigit);

            currentString = sb.toString();
        }

        return currentString;
    }

    public static int getLookSayNTimesLength(String initial, int iterations) {
        return ElvesLookElvesSay.playLookSayNTimes(initial, iterations).length();
    }
}

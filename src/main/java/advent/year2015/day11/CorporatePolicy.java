/* Licensed under Apache-2.0 */
package advent.year2015.day11;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorporatePolicy {
    public static Pattern sequencePattern = Pattern.compile("(\\w)\\1+");

    public static String getNextPassword(String currentPassword) {
        String nextPassword = currentPassword;

        do {
            nextPassword = CorporatePolicy.incrementPassword(nextPassword);
        } while (!CorporatePolicy.isValidPassword(nextPassword));

        return nextPassword;
    }

    public static boolean isValidPassword(String currentPassword) {
        if (currentPassword.matches(".*[iol].*")) {
            return false;
        }

        Matcher match = sequencePattern.matcher(currentPassword);
        Set<Character> sequenceList = new HashSet<>();
        while (match.find()) {
            sequenceList.add(match.group().charAt(0));
        }

        if (sequenceList.size() < 2) {
            return false;
        }

        char previousCharacter = '\n';
        boolean hasRepeatedStraight = false;
        int currentRepeatedCount = 0;
        for (char character : currentPassword.toCharArray()) {
            if (character == previousCharacter + 1) {
                currentRepeatedCount++;
            } else {
                currentRepeatedCount = 0;
            }

            if (currentRepeatedCount == 2) {
                hasRepeatedStraight = true;
            }

            previousCharacter = character;
        }

        return hasRepeatedStraight;
    }

    public static String incrementPassword(String currentPassword) {
        return convertLongToString(convertStringToNumber(currentPassword) + 1);
    }

    public static long convertStringToNumber(String password) {
        long value = 0;

        for (char character : password.toCharArray()) {
            value = value * 26 + (character - 96);
        }

        return value;
    }

    public static String convertLongToString(long value) {
        long remainder = value;
        StringBuilder sb = new StringBuilder();

        while (remainder > 0) {
            int character = (int) (remainder % 26);

            if (character == 0) {
                character = 26;
            }

            sb.insert(0, (char) (character + 96));
            remainder -= character;
            remainder /= 26;
        }

        return sb.toString();
    }
}

package advent.year2015.day5;

import java.util.HashMap;
import java.util.Map;

public class InternElvesForThis {
    public static int getCountNiceStrings(String lines) {
        int count = 0;
        String[] words = lines.split("\n");

        for (String word : words) {
            if (InternElvesForThis.isNiceString(word)) {
                count++;
            }
        }

        return count;
    }

    public static boolean isNiceString(String word) {
        if (word.matches(".*[aeiou].*[aeiou].*[aeiou].*")) {
            if (word.matches(".*(\\w)\\1{1,}.*")) {
                if (!word.matches(".*(ab|cd|pq|xy).*")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int getCountNiceStringsV2(String lines) {
        int count = 0;
        String[] words = lines.split("\n");

        for (String word : words) {
            if (InternElvesForThis.isNiceStringV2(word)) {
                count++;
            }
        }

        return count;
    }

    public static boolean isNiceStringV2(String word) {
        final Map<String, Integer> doubleCharMap = new HashMap<>();

        boolean isRepeatedOcc = false;
        boolean isSeparatedOcc = false;

        final char[] prevChars = new char[2];
        char c2 = '\0';
        final char[] chars = word.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            c2 = prevChars[0];
            prevChars[0] = prevChars[1];
            prevChars[1] = c;

            if (c == c2) {
                isSeparatedOcc = true;
            }

            if (i > 0) {
                String key = new String(prevChars);
                Integer indexLastSeen = doubleCharMap.get(key);

                if (indexLastSeen != null && indexLastSeen < i - 1) {
                    isRepeatedOcc = true;
                }

                if (indexLastSeen == null) {
                    doubleCharMap.put(key, i);
                }
            }
        }

        return isRepeatedOcc && isSeparatedOcc;
    }
}

/* Licensed under Apache-2.0 */
package advent.year2016.day6;

import java.util.HashMap;
import java.util.Map;

public class SignalsNoise {
    public static String getStringMostRepeatedForPosition(String signalString) {
        String[] signals = signalString.split("\n");

        Map<Character, Integer>[] characterCounts = new HashMap[signals[0].length()];

        for (int i = 0; i < characterCounts.length; i++) {
            characterCounts[i] = new HashMap<>();
        }

        for (String signal : signals) {
            char[] characters = signal.toCharArray();

            for (int i = 0; i < characters.length; i++) {
                characterCounts[i].put(characters[i], characterCounts[i].getOrDefault(characters[i], 0) + 1);
            }
        }

        StringBuilder trueSignalBuilder = new StringBuilder();
        for (Map<Character, Integer> characterCount : characterCounts) {
            // Get max value
            int maxValue = 0;
            char maxCharacter = '\n';

            for (Map.Entry<Character, Integer> entryObj : characterCount.entrySet()) {
                if (entryObj.getValue() > maxValue) {
                    maxValue = entryObj.getValue();
                    maxCharacter = entryObj.getKey();
                }
            }

            trueSignalBuilder.append(maxCharacter);
        }

        return trueSignalBuilder.toString();
    }

    public static String getStringLeastRepeatedForPosition(String signalString) {
        String[] signals = signalString.split("\n");

        Map<Character, Integer>[] characterCounts = new HashMap[signals[0].length()];

        for (int i = 0; i < characterCounts.length; i++) {
            characterCounts[i] = new HashMap<>();
        }

        for (String signal : signals) {
            char[] characters = signal.toCharArray();

            for (int i = 0; i < characters.length; i++) {
                characterCounts[i].put(characters[i], characterCounts[i].getOrDefault(characters[i], 0) + 1);
            }
        }

        StringBuilder trueSignalBuilder = new StringBuilder();
        for (Map<Character, Integer> characterCount : characterCounts) {
            // Get max value
            int minValue = Integer.MAX_VALUE;
            char maxCharacter = '\n';

            for (Map.Entry<Character, Integer> entryObj : characterCount.entrySet()) {
                if (entryObj.getValue() < minValue) {
                    minValue = entryObj.getValue();
                    maxCharacter = entryObj.getKey();
                }
            }

            trueSignalBuilder.append(maxCharacter);
        }

        return trueSignalBuilder.toString();
    }
}

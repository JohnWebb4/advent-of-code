package advent.year2015.day6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// Input: instructions
// // turn on: turns range on
// // toggle: turns on orr and vice versa
// // turn off: turns range off
// Output: Count of on lights
// Side effects: None

// O(n * m. n = num instructions, m = average range of instruction
// Iterate through instructions
// Long count of on lights
// Hash map of on lights
// For range in instruction
// // Get state of light
// // // if state is changing update hash map and count
// return count
public class ProbablyFireHazard {
    public static long getLightsOnCount(String instructionString) {
        long count = 0;
        Set<String> onLightSet = new HashSet<>();

        String[] instructions = instructionString.split("\n");

        for (String instruction : instructions) {
            String[] words = instruction.split(" ");

            String command = null;
            int[] start = null;
            int[] end = null;
            switch (words[0]) {
                case "turn":
                    command = words[1].toLowerCase();

                    start = Arrays.stream(words[2].split(",")).mapToInt(Integer::parseInt).toArray();
                    end = Arrays.stream(words[4].split(",")).mapToInt(Integer::parseInt).toArray();
                    break;
                case "toggle":
                    command = "toggle";

                    start = Arrays.stream(words[1].split(",")).mapToInt(Integer::parseInt).toArray();
                    end = Arrays.stream(words[3].split(",")).mapToInt(Integer::parseInt).toArray();
                default:
                    break;
            }

            if (command != null) {
                for (int i = start[0]; i <= end[0]; i++) {
                    for (int j = start[1]; j <= end[1]; j++) {
                        String key = i + "," + j;

                        switch (command) {
                            case "on":
                                if (!onLightSet.contains(key)) {
                                    onLightSet.add(key);
                                    count++;
                                }
                                break;
                            case "off":
                                if (onLightSet.contains(key)) {
                                    onLightSet.remove(key);
                                    count--;
                                }
                                break;
                            case "toggle":
                                if (onLightSet.contains(key)) {
                                    onLightSet.remove(key);
                                    count--;
                                } else {
                                    onLightSet.add(key);
                                    count++;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }


            }
        }

        return count;
    }

    public static long getBrightnessCount(String instructionString) {
        long count = 0;
        HashMap<String, Integer> onLightSet = new HashMap<>();

        String[] instructions = instructionString.split("\n");

        for (String instruction : instructions) {
            String[] words = instruction.split(" ");

            String command = null;
            int[] start = null;
            int[] end = null;
            switch (words[0]) {
                case "turn":
                    command = words[1].toLowerCase();

                    start = Arrays.stream(words[2].split(",")).mapToInt(Integer::parseInt).toArray();
                    end = Arrays.stream(words[4].split(",")).mapToInt(Integer::parseInt).toArray();
                    break;
                case "toggle":
                    command = "toggle";

                    start = Arrays.stream(words[1].split(",")).mapToInt(Integer::parseInt).toArray();
                    end = Arrays.stream(words[3].split(",")).mapToInt(Integer::parseInt).toArray();
                default:
                    break;
            }

            if (command != null) {
                for (int i = start[0]; i <= end[0]; i++) {
                    for (int j = start[1]; j <= end[1]; j++) {
                        String key = i + "," + j;
                        Integer state = onLightSet.getOrDefault(key, 0);

                        switch (command) {
                            case "on":
                                onLightSet.put(key, state + 1);
                                count++;
                                break;
                            case "off":
                                if (state > 0) {
                                    onLightSet.put(key, state - 1);
                                    count--;
                                }
                                break;
                            case "toggle":
                                onLightSet.put(key, state + 2);
                                count += 2;
                                break;
                            default:
                                break;
                        }
                    }
                }


            }
        }

        return count;
    }
}

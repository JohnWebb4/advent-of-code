/* Licensed under Apache-2.0 */
package advent.year2020.day14;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DockingData {
    public static long initializeDockingProgram(String input) {
        String[] lines = input.split("\n");
        String[] mask = null;
        Map<Integer, Long> memMap = new HashMap<>();

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.split("=")[1].trim().split("");
            } else if (line.startsWith("mem")) {
                Pattern memIndexPattern = Pattern.compile("mem\\[(\\w+)\\]", Pattern.CASE_INSENSITIVE);
                Matcher memIndexMatches = memIndexPattern.matcher(line);

                memIndexMatches.find();
                int memIndex = Integer.parseInt(memIndexMatches.group(1));
                long value = Long.parseLong(line.split("= ")[1]);

                String binaryString = Long.toBinaryString(value);
                StringBuilder binarySb = new StringBuilder();
                for (int i = 0; i < 36 - binaryString.length(); i++) {
                    binarySb.append("0");
                }
                binarySb.append(binaryString);
                String[] binaryArray = binarySb.toString().split("");

                for (int i = 0; i < mask.length; i++) {
                    switch (mask[i]) {
                        case "0":
                            binaryArray[i] = "0";
                            break;
                        case "1":
                            binaryArray[i] = "1";
                            break;
                        default:
                            break;
                    }
                }

                value = Long.parseLong(String.join("", binaryArray), 2);

                memMap.put(memIndex, value);
            }

        }

        long count = 0;
        Set<Integer> keys = memMap.keySet();
        for (Integer key : keys) {
            count += memMap.get(key);
        }

        return count;
    }

    public static long initializeDockingProgramV2(String input) {
        String[] lines = input.split("\n");
        String[] mask = null;
        Map<Long, Long> memMap = new HashMap<>();

        for (String line : lines) {
            if (line.startsWith("mask")) {
                mask = line.split("=")[1].trim().split("");
            } else if (line.startsWith("mem")) {
                Pattern memIndexPattern = Pattern.compile("mem\\[(\\w+)\\]", Pattern.CASE_INSENSITIVE);
                Matcher memIndexMatches = memIndexPattern.matcher(line);

                memIndexMatches.find();
                long memIndex = Long.parseLong(memIndexMatches.group(1));
                long value = Long.parseLong(line.split("= ")[1]);

                String binaryString = Long.toBinaryString(memIndex);
                StringBuilder binarySb = new StringBuilder();
                for (int i = 0; i < 36 - binaryString.length(); i++) {
                    binarySb.append("0");
                }
                binarySb.append(binaryString);
                String[] binaryArray = binarySb.toString().split("");

                for (int i = 0; i < mask.length; i++) {
                    switch (mask[i]) {
                        case "X":
                            binaryArray[i] = "X";
                            break;
                        case "1":
                            binaryArray[i] = "1";
                            break;
                        default:
                            break;
                    }
                }

                Queue<String> indexesToUpdate = new LinkedList<>();
                indexesToUpdate.add(String.join("", binaryArray));

                while (indexesToUpdate.size() > 0) {
                    String[] possibleMemIndex = indexesToUpdate.remove().split("");

                    for (int i = 0; i < possibleMemIndex.length; i++) {
                        if (possibleMemIndex[i].equals("X")) {
                            possibleMemIndex[i] = "0";
                            indexesToUpdate.add(String.join("", possibleMemIndex));

                            possibleMemIndex[i] = "1";
                            indexesToUpdate.add(String.join("", possibleMemIndex));

                            break;
                        } else if (i == possibleMemIndex.length - 1) {
                            memIndex = Long.parseLong(String.join("", possibleMemIndex), 2);

                            memMap.put(memIndex, value);
                        }
                    }
                }
            }
        }

        long count = 0;
        Set<Long> keys = memMap.keySet();
        for (Long key : keys) {
            count += memMap.get(key);
        }

        return count;
    }
}

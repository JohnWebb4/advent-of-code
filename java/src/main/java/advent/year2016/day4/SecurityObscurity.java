/* Licensed under Apache-2.0 */
package advent.year2016.day4;

import java.util.*;
import java.util.stream.Collectors;

public class SecurityObscurity {
    public static class CharacterOccurence implements Comparable<CharacterOccurence> {
        public String character;
        public int occurences;

        public CharacterOccurence(String character, int occurences) {
            this.character = character;
            this.occurences = occurences;
        }

        @Override
        public int compareTo(CharacterOccurence o) {
            if (this.occurences != o.occurences) {
                return o.occurences - this.occurences;
            } else {
                return this.character.charAt(0) - o.character.charAt(0);
            }
        }
    }

    public static int getSumOfRealRooms(String rooms) {
        String[] roomArray = rooms.split("\n");

        int sumRooms = 0;
        for (String room : roomArray) {
            List<String> parts = Arrays.stream(room.split("-")).collect(Collectors.toList());

            String encryptedName = parts.subList(0, parts.size() - 1).stream().reduce("", (s1, s2) -> s1 + s2);
            String checksum = getCheckSum(encryptedName, 5);

            String[] roomIdAndCheckSum = parts.get(parts.size() - 1).split("[\\[\\]]");
            int roomId = Integer.parseInt(roomIdAndCheckSum[0]);
            String resultChecksum = roomIdAndCheckSum[1];

            if (checksum.equals(resultChecksum)) {
                sumRooms += roomId;
            }
        }

        return sumRooms;
    }

    public static int getRealRoomSectorId(String rooms) {
        String[] roomArray = rooms.split("\n");

        for (String room : roomArray) {
            List<String> parts = Arrays.stream(room.split("-")).collect(Collectors.toList());

            String encryptedName = String.join("-", parts.subList(0, parts.size() - 1).toArray(String[]::new));
            String checksum = getCheckSum(encryptedName, 5);

            String[] roomIdAndCheckSum = parts.get(parts.size() - 1).split("[\\[\\]]");
            int sectorId = Integer.parseInt(roomIdAndCheckSum[0]);
            String resultChecksum = roomIdAndCheckSum[1];

            if (!checksum.equals(resultChecksum)) {
                // Possible room. Check real.
                String realName = getRealName(encryptedName, sectorId);

                if (realName.equals("northpole object storage")) {
                    return sectorId;
                }
            }
        }

        return 0;
    }

    public static String getCheckSum(String name, int sumLength) {
        Map<String, Integer> occurenceMap = new HashMap<>();

        for (String character : name.split("")) {
            if (occurenceMap.containsKey(character) && !character.equals("-")) {
                occurenceMap.put(character, occurenceMap.get(character) + 1);
            } else {
                occurenceMap.put(character, 1);
            }
        }

        PriorityQueue<CharacterOccurence> maxHeap = new PriorityQueue<>();
        for (String key : occurenceMap.keySet()) {
            maxHeap.add(new CharacterOccurence(key, occurenceMap.get(key)));
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sumLength && maxHeap.size() > 0; i++) {
            sb.append(maxHeap.poll().character);
        }

        return sb.toString();
    }

    public static String getRealName(String encryptedName, int sectorId) {
        String realName = encryptedName;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sectorId; i++) {
            sb.delete(0, sb.length());
            for (char c : realName.toCharArray()) {
                if (c == '-') {
                    sb.append(' ');
                } else if (c == ' ') {
                    sb.append(c);
                } else {
                    int cInt = (int) c - 97; // zero based index
                    cInt = (cInt + 1) % 26;
                    cInt += 97;

                    sb.append((char) cInt);
                }
            }
            realName = sb.toString();
        }

        return realName;
    }
}

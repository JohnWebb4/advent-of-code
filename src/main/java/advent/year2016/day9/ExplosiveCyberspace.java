/* Licensed under Apache-2.0 */
package advent.year2016.day9;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ExplosiveCyberspace {
    public static class SequenceDecompression {
        public final long total;
        public final int multiplier;
        public final String sequence;

        public SequenceDecompression(String sequence, int multiplier, long total) {
            this.sequence = sequence;
            this.multiplier = multiplier;
            this.total = total;
        }
    }

    public static int getDecompressedLength(String compressedString) {
        Queue<Character> characterQueue = new LinkedList<>();
        for (int i = 0; i < compressedString.length(); i++) {
            characterQueue.add(compressedString.charAt(i));

        }
        StringBuilder uncompressedStringBuilder = new StringBuilder();

        while (!characterQueue.isEmpty()) {
            Character character = characterQueue.poll();

            switch (character) {
                case '(':
                    uncompressedStringBuilder.append(handleSequence(characterQueue));
                    continue;
                default:
                    uncompressedStringBuilder.append(character);
                    break;
            }
        }

        return uncompressedStringBuilder.length();
    }

    public static String handleSequence(Queue<Character> characters) {
        StringBuilder sequenceInstruction = new StringBuilder();

        boolean isReading = true;
        while (!characters.isEmpty() && isReading) {
            Character seqChar = characters.poll();

            switch (seqChar) {
                case ')':
                    isReading = false;
                    break;
                default:
                    sequenceInstruction.append(seqChar);
                    break;
            }
        }

        int[] sequenceArgs = Arrays.stream(sequenceInstruction.toString().split("x")).mapToInt(Integer::parseInt).toArray();

        StringBuilder stringToRepeatBuilder = new StringBuilder();
        for (int i = 0; i < sequenceArgs[0] && !characters.isEmpty(); i++) {
            stringToRepeatBuilder.append(characters.poll());
        }

        return stringToRepeatBuilder.toString().repeat(sequenceArgs[1]);
    }

    public static long getDecompressedRecursiveLength(String compressedString) {
        long uncompressedLength = 0;
        Queue<SequenceDecompression> sequenceDecompressions = new LinkedList<>();
        sequenceDecompressions.add(new SequenceDecompression(compressedString, 1, 0));

        while (!sequenceDecompressions.isEmpty()) {
            SequenceDecompression sequenceDecompression = sequenceDecompressions.poll();
            Queue<Character> characters = new LinkedList<>();
            for (int i = 0; i < sequenceDecompression.sequence.length(); i++) {
                characters.add(sequenceDecompression.sequence.charAt(i));
            }

            while (!characters.isEmpty()) {
                char character = characters.poll();

                switch (character) {
                    case '(':
                        SequenceDecompression nextSequence = handleSequenceRecur(characters, sequenceDecompression.multiplier);

                        if (nextSequence.sequence.length() > 0) {
                            sequenceDecompressions.add(nextSequence);
                        } else {
                            uncompressedLength += nextSequence.total * nextSequence.multiplier;
                        }
                        break;
                    default:
                        uncompressedLength += sequenceDecompression.multiplier;
                        break;
                }
            }
        }

        return uncompressedLength;
    }

    public static SequenceDecompression handleSequenceRecur(Queue<Character> characters, int multiplier) {
        boolean isReading = true;
        StringBuilder sequenceInstruction = new StringBuilder();
        while (!characters.isEmpty() && isReading) {
            char character = characters.poll();

            switch (character) {
                case ')':
                    isReading = false;
                    break;
                default:
                    sequenceInstruction.append(character);
            }
        }

        int[] sequenceArgs = Arrays.stream(sequenceInstruction.toString().split("x")).mapToInt(Integer::parseInt).toArray();

        StringBuilder stringToRepeatBuilder = new StringBuilder();
        for (int i = 0; i < sequenceArgs[0] && !characters.isEmpty(); i++) {
            stringToRepeatBuilder.append(characters.poll());
        }

        String stringToRepeat = stringToRepeatBuilder.toString();

        if (stringToRepeat.charAt(0) == '(') {
            return new SequenceDecompression(stringToRepeat, sequenceArgs[1] * multiplier, 0);
        } else {
            return new SequenceDecompression("", sequenceArgs[1] * multiplier, stringToRepeat.length());
        }

    }
}

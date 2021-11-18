/* Licensed under Apache-2.0 */
package advent.year2016.day14;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

public class OneTimePad {
    public static final Pattern REPEATED_DIGIT_PATTERN = Pattern.compile(".*?((.)\\2{2,}).*");

    /***
     * Get index of nth key in one time pad
     * @param salt String salt used in MD5
     * @param keyNumber the nth key to look for
     * @return the index used in the salt to produce the nth key
     */
    public static long getIndexOfNthKey(String salt, int keyNumber) {
        // Map of digit to list of three matches
        Map<Character, List<Long>> digitPossibleIndexMatchesMap = new HashMap<>();
        // long index of last match
        int numKeysFound = 0;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // While count is less than key number
            for (long i = 0; numKeysFound < keyNumber && i < 40000; i++) {
                String data = salt + i;
                md.update(data.getBytes());
                byte[] digest = md.digest();
                String hash = DatatypeConverter
                        .printHexBinary(digest).toLowerCase(Locale.ROOT);

                Matcher repeatedDigitMatcher = REPEATED_DIGIT_PATTERN.matcher(hash);
                if (repeatedDigitMatcher.find()) {
                    String repeatedString = repeatedDigitMatcher.group(1);
                    char digit = repeatedString.charAt(0);

                    switch (repeatedString.length()) {
                        case 5:
                            List<Long> indexMatches = digitPossibleIndexMatchesMap.getOrDefault(digit, new LinkedList<>());
                            long finalI = i;
                            indexMatches = indexMatches.stream().filter(index -> index >= finalI - 1000).collect(Collectors.toList());

                            for (Long index : indexMatches) {
                                numKeysFound++;

                                if (numKeysFound == keyNumber) {
                                    return index;
                                }
                            }

                            digitPossibleIndexMatchesMap.put(digit, new LinkedList<>());
                            digitPossibleIndexMatchesMap.get(digit).add(i);
                            break;
                        default:
                            if (repeatedString.length() >= 3) {
                                digitPossibleIndexMatchesMap.putIfAbsent(digit, new LinkedList<>());
                                digitPossibleIndexMatchesMap.get(digit).add(i);
                            }
                            break;
                    }

                }
            }
        } catch (NoSuchAlgorithmException e) {
            // No op
        }

        return -1;
    }

    public static long getIndexOfNthKeyStretched(String salt, int keyNumber, int numRepetativeHash) {
        // Map of digit to list of three matches
        Map<Character, List<Long>> digitPossibleIndexMatchesMap = new HashMap<>();
        // long index of last match
        int numKeysFound = 0;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // While count is less than key number
            for (long i = 0; numKeysFound < keyNumber && i < 40000; i++) {
                String hash = salt + i;

                for (int indexHashes = 0; indexHashes <= numRepetativeHash; indexHashes++) {
                    md.update(hash.getBytes());
                    byte[] digest = md.digest();
                    hash = DatatypeConverter
                            .printHexBinary(digest).toLowerCase(Locale.ROOT);
                }

                Matcher repeatedDigitMatcher = REPEATED_DIGIT_PATTERN.matcher(hash);
                if (repeatedDigitMatcher.find()) {
                    String repeatedString = repeatedDigitMatcher.group(1);
                    char digit = repeatedString.charAt(0);

                    if (repeatedString.length() >= 5) {
                        List<Long> indexMatches = digitPossibleIndexMatchesMap.getOrDefault(digit, new LinkedList<>());
                        long finalI = i;
                        indexMatches = indexMatches.stream().filter(index -> index >= finalI - 999).collect(Collectors.toList());

                        for (Long index : indexMatches) {
                            numKeysFound++;

                            if (numKeysFound == keyNumber) {
                                return index;
                            }
                        }

                        digitPossibleIndexMatchesMap.put(digit, new LinkedList<>());
                        digitPossibleIndexMatchesMap.get(digit).add(i);
                    } else if (repeatedString.length() >= 3) {
                        digitPossibleIndexMatchesMap.putIfAbsent(digit, new LinkedList<>());
                        digitPossibleIndexMatchesMap.get(digit).add(i);
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            // No op
        }

        return -1;
    }
}

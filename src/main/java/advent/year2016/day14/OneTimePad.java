package advent.year2016.day14;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            for (long i = 0; numKeysFound < keyNumber && i < 30000; i++) {
                String data = salt + i;
                md.update(data.getBytes());
                byte[] digest = md.digest();
                String hash = DatatypeConverter
                        .printHexBinary(digest).toUpperCase();

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

                                System.out.println(String.format("YO %s %s %s %s", hash, index, i, numKeysFound));

                                if (numKeysFound == keyNumber) {
                                    return index;
                                }
                            }

                            digitPossibleIndexMatchesMap.put(digit, new LinkedList<>());
                            break;
                        case 3:
                            digitPossibleIndexMatchesMap.putIfAbsent(digit, new LinkedList<>());
                            digitPossibleIndexMatchesMap.get(digit).add(i);
                            break;
                        default:
                            break;
                    }

                }
            }
        } catch (NoSuchAlgorithmException e) {
            // No op
        }

        return -1;
    }
}

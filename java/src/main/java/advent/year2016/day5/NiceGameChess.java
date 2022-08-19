/* Licensed under Apache-2.0 */
package advent.year2016.day5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.xml.bind.DatatypeConverter;

public class NiceGameChess {
    public static String getDoorPassword(String doorId) {
        StringBuilder passwordBuilder = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (long index = 0; passwordBuilder.length() < 8; index++) {
                String data = doorId + index;

                md.update(data.getBytes());
                byte[] digest = md.digest();
                String hash = DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ROOT);

                if (hash.startsWith("00000")) {
                    // Valid hash
                    passwordBuilder.append(hash.charAt(5));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Cannot find MD5 algorithm");
        }

        return passwordBuilder.toString();
    }

    public static String getDoorPasswordShifting(String doorId) {
        String[] passwordArray = new String[8];

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            for (long index = 0; !isStringArrayFull(passwordArray); index++) {
                String data = doorId + index;

                md.update(data.getBytes());
                byte[] digest = md.digest();
                String hash = DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ROOT);

                if (hash.startsWith("00000")) {
                    // Valid hash
                    try {
                        int position = Integer.parseInt(hash.substring(5, 6));

                        if (position >= 0 && position < 8 && passwordArray[position] == null) {
                            String value = hash.substring(6, 7);

                            passwordArray[position] = value;
                        }
                    } catch (NumberFormatException e) {
                        // Noop
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Cannot find MD5 algorithm");
        }

        return String.join("", passwordArray);
    }

    public static boolean isStringArrayFull(String[] array) {
        for (String s : array) {
            if (s == null) {
                return false;
            }
        }

        return true;
    }
}

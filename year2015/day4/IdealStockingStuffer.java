/* Licensed under Apache-2.0 */
package advent.year2015.day4;

// In: String secret
// Out: Number to produce hash with # zeros
// Side effects: None

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

// Brute force
// Start at zero
// Add to secret and get MD5 hash
// Compare for correct number of starting zeros
// // If correct return number
// // Else increment and continue re-hashing
public class IdealStockingStuffer {
    public static long getLongToHashZeros(String secret, int numZeros) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            String hash = "";
            long countToHashZeros = 0;
            do {
                md.update((secret + countToHashZeros).getBytes());
                hash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();

                if (hash.startsWith("0".repeat(numZeros))) {
                    return countToHashZeros;
                }

                countToHashZeros++;
            } while (true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

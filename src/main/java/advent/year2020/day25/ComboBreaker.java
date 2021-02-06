/* Licensed under Apache-2.0 */
package advent.year2020.day25;

public class ComboBreaker {
    public static long getEncryptionKey(final int cardPublicKey, final int doorPublicKey, final int subjectNumber) {
        int cardLoopSize = 0;
        int currentCardPublicKey = 1;
        while (currentCardPublicKey != cardPublicKey) {
            currentCardPublicKey *= subjectNumber;
            currentCardPublicKey %= 20201227;
            cardLoopSize++;
        }

        long encryptionKey = 1;
        for (int i = 0; i < cardLoopSize; i++) {
            encryptionKey *= doorPublicKey;
            encryptionKey %= 20201227;
        }

        return encryptionKey;
    }
}

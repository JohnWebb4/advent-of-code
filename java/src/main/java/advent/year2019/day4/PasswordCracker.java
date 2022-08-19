/* Licensed under Apache-2.0 */
package advent.year2019.day4;

import java.util.Arrays;

public class PasswordCracker {
  public static boolean validPassword(int password) {
    boolean hasDouble = false;
    char[] passChars = Integer.toString(password).toCharArray();

    for (int i = 0; i < passChars.length - 1; i++) {
      if (passChars[i] == passChars[i + 1]) {
        hasDouble = true;
      }
    }

    if (!hasDouble) {
      return false;
    }

    char[] sorted = Arrays.copyOf(passChars, passChars.length);

    Arrays.sort(sorted);

    for (int i = 0; i < passChars.length; i++) {
      if (passChars[i] != sorted[i]) {
        return false;
      }
    }

    return true;
  }

  public static boolean validAdvancedPassword(int password) {
    boolean hasDouble = false;
    char[] passChars = Integer.toString(password).toCharArray();
    char[] appendedChars = Arrays.copyOf(passChars, passChars.length + 1);
    appendedChars[appendedChars.length - 1] = '0';

    for (int i = 0; i < appendedChars.length - 2; i++) {
      if (appendedChars[i] == appendedChars[i + 1] && appendedChars[i + 1] != appendedChars[i + 2]) {
        if (i > 0) {
          if (appendedChars[i] == appendedChars[i - 1]) {
            continue;
          }
        }
        hasDouble = true;
      }
    }

    if (!hasDouble) {
      return false;
    }

    char[] sorted = Arrays.copyOf(passChars, passChars.length);

    Arrays.sort(sorted);

    for (int i = 0; i < passChars.length; i++) {
      if (passChars[i] != sorted[i]) {
        return false;
      }
    }

    return true;
  }

  public static int getCombinations(int min, int max) {
    int combinations = 0;

    for (int i = min; i < max; i++) {
      if (validPassword(i)) {
        combinations++;
      }
    }

    return combinations;
  }

  public static int getAdvancedCombinatinos(int min, int max) {
    int combinations = 0;

    for (int i = min; i < max; i++) {
      if (validAdvancedPassword(i)) {
        combinations++;
      }
    }

    return combinations;
  }
}

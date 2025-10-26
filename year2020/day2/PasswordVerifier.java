/* Licensed under Apache-2.0 */
package advent.year2020.day2;

public class PasswordVerifier {
  public static int countValidPasswords(String input) {
    String[] passwordData = input.split("\n");

    int count = 0;
    for (String data : passwordData) {
      String[] inputs = data.split(" ");
      String[] numbers = inputs[0].split("-");
      String pattern = inputs[1].split(":")[0];

      if (verifyPassword(
          inputs[2],
          pattern.charAt(0),
          Integer.parseInt(numbers[0]),
          Integer.parseInt(numbers[1]))) {
        count++;
      }
    }

    return count;
  }

  public static int countNewValidPasswords(String input) {
    String[] passwordData = input.split("\n");

    int count = 0;
    for (String data : passwordData) {
      String[] inputs = data.split(" ");
      String[] numbers = inputs[0].split("-");
      String pattern = inputs[1].split(":")[0];

      if (verifyNewPassword(
          inputs[2],
          pattern.charAt(0),
          Integer.parseInt(numbers[0]) - 1,
          Integer.parseInt(numbers[1]) - 1)) {
        count++;
      }
    }

    return count;
  }

  public static boolean verifyPassword(
      String password, char pattern, int minRepeated, int maxRepeated) {

    int count = 0;
    for (char c : password.toCharArray()) {
      if (c == pattern) {
        count++;
      }
    }

    return count >= minRepeated && count <= maxRepeated;
  }

  public static boolean verifyNewPassword(String password, char pattern, int index1, int index2) {
    return password.charAt(index1) == pattern ^ password.charAt(index2) == pattern;
  }
}

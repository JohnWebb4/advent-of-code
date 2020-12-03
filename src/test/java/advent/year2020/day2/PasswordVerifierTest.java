/* Licensed under Apache-2.0 */
package advent.year2020.day2;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordVerifierTest {
  static String input;

  @BeforeClass
  public static void initialize() {
    try {
      input =
          new String(
              Files.readAllBytes(
                  Paths.get("./src/test/java/advent/year2020/day2/2020-day2-input.txt")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void countValidPasswords_2() {
    assertEquals(
        2,
        PasswordVerifier.countValidPasswords(
            "1-3 a: abcde\n" + "1-3 b: cdefg\n" + "2-9 c: ccccccccc"));
  }

  @Test
  public void countValidPasswords_3() {
    assertEquals(493, PasswordVerifier.countValidPasswords(input));
  }

  @Test
  public void countNewValidPasswords_1() {
    assertEquals(
        1,
        PasswordVerifier.countNewValidPasswords(
            "1-3 a: abcde\n" + "1-3 b: cdefg\n" + "2-9 c: ccccccccc"));
  }

  @Test
  public void countNewValidPasswords_593() {
    assertEquals(593, PasswordVerifier.countNewValidPasswords(input));
  }
}

/* Licensed under Apache-2.0 */
package advent.year2019.day4;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordCrackerTest {
  @Test
  public void isValidPassword111111() {
    assertEquals(true, PasswordCracker.validPassword(111111));
  }

  @Test
  public void isValidPassword223450() {
    assertEquals(false, PasswordCracker.validPassword(223450));
  }

  @Test
  public void isValidPassword123789() {
    assertEquals(false, PasswordCracker.validPassword(123789));
  }

  @Test
  public void getCombintions2050() {
    assertEquals(2050, PasswordCracker.getCombinations(128392, 643281));
  }

  @Test
  public void isValidAdvancedPassword111111() {
    assertEquals(false, PasswordCracker.validAdvancedPassword(111111));
  }

  @Test
  public void isValidAdvancedPassword223450() {
    assertEquals(true, PasswordCracker.validAdvancedPassword(223456));
  }

  @Test
  public void isValidAdvancedPassword112233() {
    assertEquals(true, PasswordCracker.validAdvancedPassword(112233));
  }

  @Test
  public void isValidAdvancedPassword123444() {
    assertEquals(false, PasswordCracker.validAdvancedPassword(123444));
  }

  @Test
  public void isValidAdvancedPassword111222() {
    assertEquals(false, PasswordCracker.validAdvancedPassword(111222));
  }

  @Test
  public void isValidAdvancedPassword111122() {
    assertEquals(true, PasswordCracker.validAdvancedPassword(111122));
  }

  @Test
  public void getAdvancedCombintions2050() {
    assertEquals(1390, PasswordCracker.getAdvancedCombinatinos(128392, 643281));
  }
}
/* Licensed under Apache-2.0 */
package advent.year2019.day2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class IntCodeReaderTest {
  private static int[] codes;
  private static int[] results;

  @BeforeClass
  public static void setup() {
    try {
      File file = new File("./src/test/java/advent/year2019/day2/Codes.txt");

      BufferedReader br = new BufferedReader(new FileReader(file));

      String codeString;
      while ((codeString = br.readLine()) != null) {
        codes = Arrays.stream(codeString.split(",")).mapToInt(i->Integer.parseInt(i)).toArray();
      }

      br.close();
    } catch(Exception e) {
    	e.printStackTrace();
    }

    try {
      File file = new File("./src/test/java/advent/year2019/day2/Results.txt");

      BufferedReader br = new BufferedReader(new FileReader(file));

      String resultString;
      while ((resultString = br.readLine()) != null) {
        results = Arrays.stream(resultString.split(",")).mapToInt(i->Integer.parseInt(i)).toArray();
      }
    } catch(Exception e) {
    	e.printStackTrace();
    }
  }

  @Test
  public void runIntCodes0() {
    int[] input = {1, 0, 0, 0, 99};
    int[] expected = {2, 0, 0, 0, 99};

    assertArrayEquals(expected, IntCodeReader.runIntCodes(input));
  }

  @Test
  public void runIntCodes1() {
    int[] input = {2, 3, 0, 3, 99};
    int[] expected = {2, 3, 0, 6, 99};

    assertArrayEquals(expected, IntCodeReader.runIntCodes(input));
  }

  @Test
  public void runIntCodes2() {
    int[] input = {2, 4, 4, 5, 99, 0};
    int[] expected = {2, 4, 4, 5, 99, 9801};

    assertArrayEquals(expected, IntCodeReader.runIntCodes(input));
  }

  @Test
  public void runIntCodes3() {
    int[] input = {1, 1, 1, 4, 99, 5, 6, 0, 99};
    int[] expected = { 30, 1, 1, 4, 2, 5, 6, 0, 99 };

    assertArrayEquals(expected, IntCodeReader.runIntCodes(input));
  }

  @Test
  public void restore1202AlarmAnswer() {
    assertArrayEquals(results, IntCodeReader.restore1202Alarm(codes));
  }

  @Test
  public void modifyNounandVerbForTarget19690720() {
    int[] result = IntCodeReader.modifyNounAndVerbForTarget(19690720, codes);

    assertEquals(19690720, result[0]);
    assertEquals(78, result[1]);
    assertEquals(70, result[2]);
  }
}
/* Licensed under Apache-2.0 */
package advent.year2019.day2;

public class IntCodeReader {
  public static int[] runIntCodes(int[] codes) {
    int[] result = codes.clone();

    for (int i = 0; i < result.length - 3; i += 4) {
      int code = result[i];
      int num1Index = result[i+1];
      int num2Index = result[i+2];
      int targetIndex = result[i+3];

      if (num1Index < result.length && num2Index < result.length) {
        int num1 = result[num1Index];
        int num2 = result[num2Index];

        if (code == 1) {
          result[targetIndex] = num1 + num2;
        } else if (code == 2) {
          result[targetIndex] = num1 * num2;
        }
      }
    }

    return result;
  }

  public static int[] restore1202Alarm(int[] codes) {
    int[] input = codes.clone();

    input[1] = 12;
    input[2] = 2;

    return runIntCodes(input);
  }

  public static int[] modifyNounAndVerbForTarget(int number, int[] codes) {
    for (int noun = 0; noun < 100; noun++) {
      for (int verb = 0; verb < 100;  verb++) {
        int[] inputCodes = codes.clone();
        inputCodes[1] = noun;
        inputCodes[2] = verb;

        int[] result = runIntCodes(inputCodes);

        if (result[0] == number) {
          return result;
        }
      }
    }

    return codes;
  }
}

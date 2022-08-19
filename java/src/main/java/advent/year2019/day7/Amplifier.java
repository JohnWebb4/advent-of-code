/* Licensed under Apache-2.0 */
package advent.year2019.day7;

import java.util.*;


public class Amplifier implements Enumeration<Long> {
  private final long phase;
  private final long[] codes;
  private int index = 0;
  private boolean hasFinished = false;
  private final ArrayList<Long> inputs;
  private long output = 0;

  public Amplifier(long[] codes, long phase) {
    this.codes = new long[codes.length];
    System.arraycopy(codes, 0, this.codes, 0, this.codes.length);

    this.phase = phase;

    this.inputs = new ArrayList<>();
    inputs.add((long) phase);
  }

  public static long[] getOprations(long code) {
    long operation = code % 100;
    long param1Mode = code % 1000 >= 100 ? 1 : 0;
    long param2Mode = code % 10000 >= 1000 ? 1 : 0;
    long targetIndexMode = code % 100000 >= 10000 ? 1 : 0;

    return new long[] {
        operation,
        param1Mode,
        param2Mode,
        targetIndexMode,
    };
  }

  public long getParamValue(int paramIndex, long paramMode) {
    if (paramMode == 0) {
      return codes[paramIndex];
    }

    return paramIndex;
  }

  @Override
  public boolean hasMoreElements() {
    return !hasFinished;
  }

  @Override
  public Long nextElement() {
    return nextElement(null);
  }

  public Long nextElement(Long newInput) throws IllegalArgumentException {
    if (newInput != null) {
      inputs.add(newInput);
    }

    while (index < codes.length) {
      long[] operations = getOprations(codes[index]);
      long operation = operations[0];

      if (operation == 1) {
        int param1 = (int) codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int)codes[index + 2];
        long param2Mode = operations[2];
        long param2Value = getParamValue(param2, param2Mode);

        int targetIndex = (int)codes[index + 3];

        codes[targetIndex] = param1Value + param2Value;

        index += 4;
      } else if (operation == 2) {
        int param1 = (int)codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int)codes[index + 2];
        long param2Mode = operations[2];
        long param2Value = getParamValue(param2, param2Mode);

        int targetIndex = (int)codes[index + 3];

        codes[targetIndex] = param1Value * param2Value;

        index += 4;
      } else if (operation == 3) {
        if (inputs.size() > 0) {
          int targetIndex = (int)codes[index + 1];
          codes[targetIndex] = inputs.remove(0);

          index += 2;
        } else {
          return this.output;
        }
      } else if (operation == 4) {
        int targetIndex = (int)codes[index + 1];

        output = codes[targetIndex];

        index += 2;
      } else if (operation == 5) {
        int param1 = (int)codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int)codes[index + 2];
        long param2Mode = operations[2];
        int param2Value = (int)getParamValue(param2, param2Mode);

        if (param1Value != 0) {
          index = param2Value;
        } else {
          index += 3;
        }
      } else if (operation == 6) {
        int param1 = (int) codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int) codes[index + 2];
        long param2Mode = operations[2];
        int param2Value = (int)getParamValue(param2, param2Mode);

        if (param1Value == 0) {
          index = param2Value;
        } else {
          index += 3;
        }
      } else if (operation == 7) {
        int param1 = (int)codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int) codes[index + 2];
        long param2Mode = operations[2];
        long param2Value = getParamValue(param2, param2Mode);

        int targetIndex = (int) codes[index + 3];

        if (param1Value < param2Value) {
          codes[targetIndex] = 1;
        } else {
          codes[targetIndex] = 0;
        }

        index += 4;
      } else if (operation == 8) {
        int param1 = (int) codes[index + 1];
        long param1Mode = operations[1];
        long param1Value = getParamValue(param1, param1Mode);

        int param2 = (int) codes[index + 2];
        long param2Mode = operations[2];
        long param2Value = getParamValue(param2, param2Mode);

        int targetIndex = (int) codes[index + 3];

        if (param1Value == param2Value) {
          codes[targetIndex] = 1;
        } else {
          codes[targetIndex] = 0;
        }

        index += 4;
      } else if (operation == 99) {
        hasFinished = true;
        return this.output;
      } else {
        hasFinished = true;
        throw new IllegalArgumentException(String.format("No command %s", operation));
      }
    }

    hasFinished = true;
    return this.output;
    }
}

/* Licensed under Apache-2.0 */
package advent.year2019.day5;


import java.util.ArrayList;

public class ThermalIntCodeReader {
    public static int[] getOprations(int code) {
        int operation = code % 100;
        int param1Mode = code % 1000 >= 100 ? 1 : 0;
        int param2Mode = code % 10000 >= 1000 ? 1 : 0;
        int targetIndexMode = code % 100000 >= 10000 ? 1 : 0;

        return new int[] {
                operation,
                param1Mode,
                param2Mode,
                targetIndexMode,
        };
    }

    public static int getParamValue(int paramIndex, int paramMode, int[] codes) {
        if (paramMode == 0) {
            return codes[paramIndex];
        }

        return paramIndex;
    }

    public static IntResult runIntCodes(int[] codes, int[] inputs) throws IllegalArgumentException {
        int index = 0;
        int inputIndex = 0;
        ArrayList<Integer> output = new ArrayList<>();

        while (index < codes.length) {
            int[] operations = getOprations(codes[index]);
            int operation = operations[0];

            if (operation == 1) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                int targetIndex = codes[index + 3];

                codes[targetIndex] = param1Value + param2Value;

                index += 4;
            } else if (operation == 2) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                int targetIndex = codes[index + 3];

                codes[targetIndex] = param1Value * param2Value;

                index += 4;
            } else if (operation == 3) {
                int targetIndex = codes[index + 1];

                codes[targetIndex] = inputs[inputIndex];

                inputIndex++;
                index += 2;
            } else if (operation == 4) {
                int targetIndex = codes[index + 1];

                output.add(codes[targetIndex]);

                index += 2;
            } else if (operation == 5) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                if (param1Value != 0) {
                    index = param2Value;
                } else {
                    index += 3;
                }
            } else if (operation == 6) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                if (param1Value == 0) {
                    index = param2Value;
                } else {
                    index += 3;
                }
            } else if (operation == 7) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                int targetIndex = codes[index + 3];

                if (param1Value < param2Value) {
                    codes[targetIndex] = 1;
                } else {
                    codes[targetIndex] = 0;
                }

                index += 4;
            } else if (operation == 8) {
                int param1 = codes[index + 1];
                int param1Mode = operations[1];
                int param1Value = getParamValue(param1, param1Mode, codes);

                int param2 = codes[index + 2];
                int param2Mode = operations[2];
                int param2Value = getParamValue(param2, param2Mode, codes);

                int targetIndex = codes[index + 3];

                if (param1Value == param2Value) {
                    codes[targetIndex] = 1;
                } else {
                    codes[targetIndex] = 0;
                }

                index += 4;
            } else if (operation == 99) {
                return new IntResult(codes, output.stream().mapToInt(i -> i).toArray());
            } else {
              throw new IllegalArgumentException(String.format("No command %s", operation));
            }
        }

        return new IntResult(codes, output.stream().mapToInt(i -> i).toArray());
    }

    public static IntResult runIntCodes(int[] codes) throws IllegalArgumentException {
        return runIntCodes(codes, new int[] {});
    }
}

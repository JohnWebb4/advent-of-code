/* Licensed under Apache-2.0 */
package advent.year2020.day18;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationOrder {
    public static long evaluateAndSum(String input) {
        String[] lines = input.split("\n");
        long count = 0;

        for (String line : lines) {
            count += OperationOrder.evaluate(line);
        }

        return count;
    }

    public static long evaluate(String input) {
        String equation = input;
        Pattern innermostParensPattern = Pattern.compile("\\([\\d\\s*+]+\\)");

        Matcher m = innermostParensPattern.matcher(equation);

        while (m.find()) {
            String[] innerEquation = m.group().split(" ");

            long innerCount = Long.parseLong(innerEquation[0].substring(1));
            boolean isMultiply = false;
            for (int i = 1; i < innerEquation.length; i++) {
                if (innerEquation[i].equals("+")) {
                    isMultiply = false;
                } else if (innerEquation[i].equals("*")) {
                    isMultiply = true;
                } else {
                    long number = 0;
                    if (i == innerEquation.length - 1) {
                        number = Long.parseLong(innerEquation[i].substring(0, innerEquation[i].length() - 1));
                    } else {
                        number = Long.parseLong(innerEquation[i]);
                    }

                    if (isMultiply) {
                        innerCount *= number;
                    } else {
                        innerCount += number;
                    }
                }
            }

            equation = equation.substring(0, m.start()) + innerCount + equation.substring(m.end());
            m = innermostParensPattern.matcher(equation);
        }

        String[] splitEquation = equation.split(" ");
        long count = Long.parseLong(splitEquation[0]);
        boolean isMultiply = false;
        for (int i = 1; i < splitEquation.length; i++) {
            if (splitEquation[i].equals("+")) {
                isMultiply = false;
            } else if (splitEquation[i].equals("*")) {
                isMultiply = true;
            } else {
                long number = Long.parseLong(splitEquation[i]);

                if (isMultiply) {
                    count *= number;
                } else {
                    count += number;
                }
            }
        }


        return count;
    }

    public static long evaluateAdvancedAndSum(String input) {
        String[] lines = input.split("\n");
        long count = 0;

        for (String line : lines) {
            count += OperationOrder.evaluateAdvanced(line);
        }

        return count;
    }

    public static long evaluateAdvanced(String input) {
        String equation = input;
        Pattern innermostParensPattern = Pattern.compile("\\([\\d\\s*+]+\\)");
        Pattern additionPattern = Pattern.compile("\\d+ \\+ \\d+");

        Matcher m = innermostParensPattern.matcher(equation);

        while (m.find()) {
            String innerEquation = m.group();

            Matcher mAdd = additionPattern.matcher(innerEquation);
            while (mAdd.find()) {
                String addEquation = mAdd.group();
                String[] splitAddEquation = addEquation.split(" ");
                long addResult = Long.parseLong(splitAddEquation[0]) + Long.parseLong(splitAddEquation[2]);

                innerEquation = innerEquation.substring(0, mAdd.start()) + addResult + innerEquation.substring(mAdd.end());
                mAdd = additionPattern.matcher(innerEquation);
            }

            String[] splitInner = innerEquation.split(" ");

            long innerCount = Long.parseLong(splitInner[0].split("[\\(\\)]")[1]);

            for (int i = 2; i < splitInner.length; i += 2) {
                long number = Long.parseLong(splitInner[i].split("\\)")[0]);
                innerCount *= number;
            }

            equation = equation.substring(0, m.start()) + innerCount + equation.substring(m.end());
            m = innermostParensPattern.matcher(equation);
        }

        Matcher mAdd = additionPattern.matcher(equation);
        while (mAdd.find()) {
            String addEquation = mAdd.group();
            String[] splitAddEquation = addEquation.split(" ");
            long addResult = Long.parseLong(splitAddEquation[0]) + Long.parseLong(splitAddEquation[2]);
            equation = equation.substring(0, mAdd.start()) + addResult + equation.substring(mAdd.end());
            mAdd = additionPattern.matcher(equation);
        }

        String[] splitInner = equation.split(" ");
        long count = Long.parseLong(splitInner[0]);

        for (int i = 2; i < splitInner.length; i += 2) {
            long number = Long.parseLong(splitInner[i]);
            count *= number;
        }

        return count;
    }
}

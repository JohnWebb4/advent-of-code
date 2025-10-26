/* Licensed under Apache-2.0 */
package advent.year2015.day23;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TuringLock {
    public static int getValueInRegister(String instructions, String returnRegisterName) {
        Map<String, Integer> registerMap = new HashMap<>();
        String[] instrutionArray = instructions.split("\n");

        int instructionPointer = 0;
        while (instructionPointer < instrutionArray.length) {
            String instruction = instrutionArray[instructionPointer];
            String[] instructionArgs = Arrays.stream(instruction.split("[ ,]")).filter((s) -> s.length() > 0).toArray(String[]::new);
            String command = instructionArgs[0];

            int registerValue;
            switch (command) {
                case "hlf":
                    registerValue = registerMap.getOrDefault(instructionArgs[1], 0);
                    registerValue /= 2;
                    registerMap.put(instructionArgs[1], registerValue);

                    instructionPointer++;
                    break;
                case "tpl":
                    registerValue = registerMap.getOrDefault(instructionArgs[1], 0);
                    registerValue *= 3;
                    registerMap.put(instructionArgs[1], registerValue);

                    instructionPointer++;
                    break;
                case "inc":
                    registerValue = registerMap.getOrDefault(instructionArgs[1], 0);
                    registerValue += 1;
                    registerMap.put(instructionArgs[1], registerValue);

                    instructionPointer++;
                    break;
                case "jmp":
                    instructionPointer += Integer.parseInt(instructionArgs[1]);
                    break;
                case "jie":
                    registerValue = registerMap.getOrDefault(instructionArgs[1], 0);
                    if (registerValue % 2 == 0) {
                        instructionPointer += Integer.parseInt(instructionArgs[2]);
                    } else {
                        instructionPointer++;
                    }
                    break;
                case "jio":
                    registerValue = registerMap.getOrDefault(instructionArgs[1], 0);
                    if (registerValue == 1) {
                        instructionPointer += Integer.parseInt(instructionArgs[2]);
                    } else {
                        instructionPointer++;
                    }
                    break;
                default:
                    break;
            }
        }

        return registerMap.get(returnRegisterName);
    }
}

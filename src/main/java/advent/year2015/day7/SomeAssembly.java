package advent.year2015.day7;

import java.util.*;

public class SomeAssembly {
    public static int getSignaToWire(String instructions, String destWireName) {
        List<String[]> instructionList = new ArrayList<>(Arrays.asList(Arrays.stream(instructions.split("\n")).map((s -> Arrays.stream(s.split(" -> ")).toArray(String[]::new))).toArray(String[][]::new)));
        Map<String, Integer> wireMap = new HashMap<>();

        boolean didDelete;
        do {
            ListIterator<String[]> instructionIter = instructionList.listIterator();
            didDelete = false;

            while (instructionIter.hasNext()) {
                String[] parts = instructionIter.next();

                Integer value = SomeAssembly.parseIntFromInstruction(parts[0], wireMap);

                if (value != null) {
                    String wire = parts[1];
                    wireMap.put(wire, value);
                    didDelete = true;
                    instructionIter.remove();
                }
            }
        } while (didDelete);

        return wireMap.get(destWireName);
    }

    public static int getSignaToWireWithOverride(String instructions, String destWireName, String overrideWireName) {
        List<String[]> instructionList = new ArrayList<>(Arrays.asList(Arrays.stream(instructions.split("\n")).map((s -> Arrays.stream(s.split(" -> ")).toArray(String[]::new))).toArray(String[][]::new)));
        Map<String, Integer> wireMap = new HashMap<>();

        boolean didDelete;
        do {
            ListIterator<String[]> instructionIter = instructionList.listIterator();
            didDelete = false;

            while (instructionIter.hasNext()) {
                String[] parts = instructionIter.next();

                Integer value = SomeAssembly.parseIntFromInstruction(parts[0], wireMap);

                if (value != null) {
                    String wire = parts[1];
                    wireMap.put(wire, value);
                    didDelete = true;
                    instructionIter.remove();
                }
            }

        } while (didDelete);

        instructionList = new ArrayList<>(Arrays.asList(Arrays.stream(instructions.split("\n")).map((s -> Arrays.stream(s.split(" -> ")).toArray(String[]::new))).toArray(String[][]::new)));
        instructionList.removeIf((parts) -> parts[1].equals("b"));
        Integer destValue = wireMap.get(destWireName);
        wireMap.clear();
        wireMap.put(overrideWireName, destValue);

        do {
            ListIterator<String[]> instructionIter = instructionList.listIterator();
            didDelete = false;

            while (instructionIter.hasNext()) {
                String[] parts = instructionIter.next();

                Integer value = SomeAssembly.parseIntFromInstruction(parts[0], wireMap);

                if (value != null) {
                    String wire = parts[1];
                    wireMap.put(wire, value);
                    didDelete = true;
                    instructionIter.remove();
                }
            }
        } while (didDelete);

        return wireMap.get(destWireName);
    }

    public static Integer parseIntFromInstruction(String instruction, Map<String, Integer> wireMap) {
        String[] inputs = instruction.split(" ");

        Integer value = null;
        switch (inputs.length) {
            case 1:
                try {
                    value = parseIntOrGetValue(inputs[0], wireMap);
                } catch (NumberFormatException e) {
                }
                break;
            case 2:
                if (inputs[0].equals("NOT")) {
                    try {
                        value = ~parseIntOrGetValue(inputs[1], wireMap) & 0xff;
                    } catch (NumberFormatException e) {
                    }
                }
                break;
            case 3:
                if (inputs[1].equals("AND")) {
                    try {
                        int value1 = parseIntOrGetValue(inputs[0], wireMap);
                        int value2 = parseIntOrGetValue(inputs[2], wireMap);

                        value = value1 & value2;
                    } catch (NumberFormatException e) {
                    }

                } else if (inputs[1].equals("OR")) {
                    try {
                        int value1 = parseIntOrGetValue(inputs[0], wireMap);
                        int value2 = parseIntOrGetValue(inputs[2], wireMap);

                        value = value1 | value2;
                    } catch (NumberFormatException e) {
                    }
                } else if (inputs[1].equals("LSHIFT")) {
                    try {
                        int value1 = parseIntOrGetValue(inputs[0], wireMap);
                        value = value1 << parseIntOrGetValue(inputs[2], wireMap);
                    } catch (NumberFormatException e) {
                    }
                } else if (inputs[1].equals("RSHIFT")) {
                    try {
                        int value1 = parseIntOrGetValue(inputs[0], wireMap);
                        value = value1 >> parseIntOrGetValue(inputs[2], wireMap);
                    } catch (NumberFormatException e) {
                    }
                }
                break;
            default:
                break;
        }

        return value;
    }

    public static int parseIntOrGetValue(String key, Map<String, Integer> map) throws NumberFormatException {
        Integer value = null;

        try {
            value = Integer.parseInt(key);
        } catch (NumberFormatException e) {
        }

        if (value == null) {
            try {
                value = map.get(key);
            } catch (NullPointerException e) {
            }
        }

        if (value == null) {
            throw new NumberFormatException(String.format("Cannot parse %s", value));
        }

        return value;
    }
}

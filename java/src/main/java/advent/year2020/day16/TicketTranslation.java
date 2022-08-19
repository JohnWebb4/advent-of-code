/* Licensed under Apache-2.0 */
package advent.year2020.day16;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TicketTranslation {
    public static class Rule {
        public String name;
        public int[][] ranges;

        public Rule(String rule) {
            String[] nameAndRanges = rule.split(":");
            this.name = nameAndRanges[0];
            this.ranges = Arrays.stream(nameAndRanges[1].split("or")).map(String::trim).map((String r) -> Arrays.stream(r.split("-")).mapToInt(Integer::parseInt).toArray()).toArray(int[][]::new);
        }

        public boolean isValid(int value) {
            boolean isValid = false;

            for (int[] range : ranges) {
                if (value >= range[0] && value <= range[1]) {
                    isValid = true;
                    break;
                }
            }

            return isValid;
        }
    }

    public static int getErrorRate(String input) {
        String[] inputs = input.split("\n\n");
        Rule[] rules = Arrays.stream(inputs[0].split("\n")).map(Rule::new).toArray(Rule[]::new);
        String[] otherTickets = inputs[2].split("\n");
        otherTickets = Arrays.copyOfRange(otherTickets, 1, otherTickets.length);

        int errorRate = 0;
        for (String ticket : otherTickets) {
            String[] fields = ticket.split(",");

            for (int i = 0; i < fields.length; i++) {
                boolean inRange = false;
                int field = Integer.parseInt(fields[i].trim());

                for (Rule rule : rules) {
                    if (rule.isValid(field)) {
                        inRange = true;
                    }
                }

                if (!inRange) {
                    errorRate += field;
                }
            }
        }

        return errorRate;
    }

    public static long getValidTicketFieldsAndReturnProductStartingWithName(String input, String name) {
        String[] inputs = input.split("\n\n");
        Rule[] rules = Arrays.stream(inputs[0].split("\n")).map(Rule::new).toArray(Rule[]::new);
        int[] myTicket = Arrays.stream(inputs[1].split("\n")[1].split(",")).mapToInt(Integer::parseInt).toArray();
        int[][] otherTickets = Arrays.stream(inputs[2].split("\n")).map((s) -> {
            try {
                return Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                return null;
            }
        }).filter((int[] ticket) -> {
            if (ticket != null) {
                boolean areAllFieldsValid = true;

                for (int i = 0; i < ticket.length; i++) {
                    boolean inRange = false;
                    try {
                        for (Rule rule : rules) {
                            if (rule.isValid(ticket[i])) {
                                inRange = true;
                                break;
                            }
                        }

                        if (!inRange) {
                            areAllFieldsValid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

                return areAllFieldsValid;
            }

            return false;
        }).toArray(int[][]::new);

        List<Rule>[] rowPossibleRules = new LinkedList[rules.length];

        for (int i = 0; i < rowPossibleRules.length; i++) {
            rowPossibleRules[i] = new LinkedList<>();
        }

        for (int rowIndex = 0; rowIndex < rules.length; rowIndex++) {
            for (int ruleIndex = 0; ruleIndex < rules.length; ruleIndex++) {
                boolean isValid = true;
                for (int[] ticket : otherTickets) {
                    if (!rules[ruleIndex].isValid(ticket[rowIndex])) {
                        isValid = false;
                        break;
                    }
                }

                if (isValid) {
                    rowPossibleRules[rowIndex].add(rules[ruleIndex]);
                }
            }
        }

        Rule[] rulesOrdered = null;
        Stack<Rule[]> toCheck = new Stack<>();

        for (int i = 0; i < rowPossibleRules[0].size(); i++) {
            toCheck.add(new Rule[]{rowPossibleRules[0].get(i)});
        }

        while (toCheck.size() > 0) {
            Rule[] previousRules = toCheck.pop();
            for (int possibleIndex = 0; possibleIndex < rowPossibleRules[previousRules.length].size(); possibleIndex++) {
                Rule rule = rowPossibleRules[previousRules.length].get(possibleIndex);

                boolean alreadyHasRule = false;
                for (int previousIndex = 0; previousIndex < previousRules.length; previousIndex++) {
                    if (previousRules[previousIndex] == rule) {
                        alreadyHasRule = true;
                        break;
                    }
                }

                if (!alreadyHasRule) {
                    Rule[] newRules = Arrays.copyOfRange(previousRules, 0, previousRules.length + 1);
                    newRules[newRules.length - 1] = rule;

                    if (newRules.length == rules.length) {
                        rulesOrdered = newRules;
                        toCheck.clear();
                        break;
                    } else {
                        toCheck.add(newRules);
                    }
                }
            }
        }

        boolean hasMatch = false;
        long matchingRulesProduct = 1;

        if (rulesOrdered != null) {
            for (int i = 0; i < rulesOrdered.length; i++) {
                if (rulesOrdered[i].name.contains(name)) {
                    matchingRulesProduct *= myTicket[i];
                    hasMatch = true;
                }
            }
        }

        if (hasMatch) {
            return matchingRulesProduct;
        } else {
            return 0;
        }
    }
}

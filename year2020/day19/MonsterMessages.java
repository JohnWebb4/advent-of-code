/* Licensed under Apache-2.0 */
package advent.year2020.day19;

import java.util.*;

public class MonsterMessages {
    public static class Process {
        String rule;
        String previous;

        public Process(String rule, String previous) {
            this.rule = rule;
            this.previous = previous;
        }
    }

    public static class PossibleMatch {
        String possibleMatch;
        int count42;

        public PossibleMatch(String possibleMatch, int count42) {
            this.possibleMatch = possibleMatch;
            this.count42 = count42;
        }
    }

    public static int countValid(String input) {
        String[] inputs = input.split("\n\n");
        String[] rules = inputs[0].split("\n");
        String[] images = inputs[1].split("\n");
        int maxImageLength = Arrays.stream(images).mapToInt(String::length).reduce(Math::max).getAsInt();

        Map<Integer, String> ruleMap = new HashMap<>();

        for (String rule : rules) {
            String[] ruleParts = rule.split(":");
            int ruleNumber = Integer.parseInt(ruleParts[0]);

            ruleMap.put(ruleNumber, String.join("", ruleParts[1].trim().split("\"")));
        }

        Set<String> ruleSet = new HashSet<>();
        PriorityQueue<Process> toCheck = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o2.previous.length(), o1.previous.length());
            }
        });
        toCheck.add(new Process(ruleMap.get(0), ""));

        while (toCheck.size() > 0) {
            Process process = toCheck.remove();

            String[] ruleParts = process.rule.split(" \\| ");
            String previous = process.previous;

            for (String rulePart : ruleParts) {
                int index = rulePart.indexOf(' ');

                String firstId = index > 0 ? rulePart.substring(0, index).trim() : rulePart;
                String rest = index > 0 ? rulePart.substring(index).trim() : "";

                if (firstId.matches("\\d+")) {
                    String newRule = ruleMap.get(Integer.parseInt(firstId));

                    String[] newRuleSplit = newRule.split(" \\| ");

                    for (String newRulePart : newRuleSplit) {
                        String newRest = newRulePart + " " + rest;

                        Process newProcess = new Process(newRest, previous);
                        toCheck.add(newProcess);
                    }
                } else {
                    String nextPrevious = previous + firstId;

                    if (rest != null && rest.length() > 0 && previous.length() < maxImageLength) {
                        Process newProcess = new Process(rest, nextPrevious);
                        toCheck.add(newProcess);
                    } else {
                        ruleSet.add(nextPrevious);
                    }
                }
            }
        }

        int countValid = 0;
        for (String image : images) {
            if (ruleSet.contains(image)) {
                countValid++;
            }
        }

        return countValid;
    }

    public static int countInfiniteValid(String input) {
        String[] inputs = input.split("\n\n");
        String[] rules = inputs[0].split("\n");
        String[] images = inputs[1].split("\n");
        int maxImageLength = Arrays.stream(images).mapToInt(String::length).reduce(Math::max).getAsInt();

        Map<Integer, String> ruleMap = new HashMap<>();

        for (String rule : rules) {
            String[] ruleParts = rule.split(":");
            int ruleNumber = Integer.parseInt(ruleParts[0]);

            ruleMap.put(ruleNumber, String.join("", ruleParts[1].trim().split("\"")));
        }

        ruleMap.put(8, "42 | 42 8");
        ruleMap.put(11, "42 31 | 42 11 31");

        Set<String> ruleSet42 = new HashSet<>();
        PriorityQueue<Process> toCheck = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o2.previous.length(), o1.previous.length());
            }
        });
        toCheck.add(new Process(ruleMap.get(42), ""));

        while (toCheck.size() > 0) {
            Process process = toCheck.remove();
            String[] ruleParts = process.rule.split(" \\| ");
            String previous = process.previous;

            for (String rulePart : ruleParts) {
                int index = rulePart.indexOf(' ');

                String firstId = index > 0 ? rulePart.substring(0, index).trim() : rulePart;
                String rest = index > 0 ? rulePart.substring(index).trim() : "";

                if (firstId.matches("\\d+")) {
                    String newRule = ruleMap.get(Integer.parseInt(firstId));

                    String[] newRuleSplit = newRule.split(" \\| ");

                    for (String newRulePart : newRuleSplit) {
                        String newRest = newRulePart + " " + rest;

                        if (newRest.split(" ").length + previous.length() < maxImageLength) {
                            Process newProcess = new Process(newRest, previous);
                            toCheck.add(newProcess);
                        }
                    }
                } else {
                    String nextPrevious = previous + firstId;

                    if (rest == null || rest.length() == 0) {
                        ruleSet42.add(nextPrevious);
                    } else {
                        if (rest.split(" ").length + previous.length() < maxImageLength) {
                            Process newProcess = new Process(rest, nextPrevious);
                            toCheck.add(newProcess);
                        }
                    }
                }
            }
        }

        toCheck.add(new Process(ruleMap.get(31), ""));
        Set<String> ruleSet31 = new HashSet<>();
        while (toCheck.size() > 0) {
            Process process = toCheck.remove();
            String[] ruleParts = process.rule.split(" \\| ");
            String previous = process.previous;

            for (String rulePart : ruleParts) {
                int index = rulePart.indexOf(' ');

                String firstId = index > 0 ? rulePart.substring(0, index).trim() : rulePart;
                String rest = index > 0 ? rulePart.substring(index).trim() : "";

                if (firstId.matches("\\d+")) {
                    String newRule = ruleMap.get(Integer.parseInt(firstId));

                    String[] newRuleSplit = newRule.split(" \\| ");

                    for (String newRulePart : newRuleSplit) {
                        String newRest = newRulePart + " " + rest;

                        if (newRest.split(" ").length + previous.length() < maxImageLength) {
                            Process newProcess = new Process(newRest, previous);
                            toCheck.add(newProcess);
                        }
                    }
                } else {
                    String nextPrevious = previous + firstId;

                    if (rest == null || rest.length() == 0) {
                        ruleSet31.add(nextPrevious);
                    } else {
                        if (rest.split(" ").length + previous.length() < maxImageLength) {
                            Process newProcess = new Process(rest, nextPrevious);
                            toCheck.add(newProcess);
                        }
                    }
                }
            }
        }

        int countValid = 0;
        for (String image : images) {
            Queue<PossibleMatch> possibleMatch42 = new LinkedList<>();
            Queue<PossibleMatch> possibleMatch31 = new LinkedList<>();

            for (int i = 1; i < image.length() + 1; i++) {
                String sub = image.substring(0, i);
                String rest = image.substring(i);

                if (ruleSet42.contains(sub)) {
                    possibleMatch42.add(new PossibleMatch(rest, 1));
                }
            }

            while (possibleMatch42.size() > 0) {
                PossibleMatch possibleMatch = possibleMatch42.remove();

                for (int i = 1; i < possibleMatch.possibleMatch.length() + 1; i++) {
                    String part = possibleMatch.possibleMatch.substring(0, i);
                    String rest = possibleMatch.possibleMatch.substring(i);

                    if (ruleSet42.contains(part)) {
                        possibleMatch42.add(new PossibleMatch(rest, possibleMatch.count42 + 1));
                        possibleMatch31.add(new PossibleMatch(rest, possibleMatch.count42 + 1));
                    }
                }
            }

            boolean isValid = false;
            while (!isValid && possibleMatch31.size() > 0) {
                PossibleMatch possibleMatch = possibleMatch31.remove();
                for (int i = 1; i < possibleMatch.possibleMatch.length() + 1; i++) {
                    String part = possibleMatch.possibleMatch.substring(0, i);
                    String rest = possibleMatch.possibleMatch.substring(i);

                    if (ruleSet31.contains(part)) {
                        if (rest.length() == 0 && possibleMatch.count42 > 1) {
                            isValid = true;
                            break;
                        } else {
                            possibleMatch31.add(new PossibleMatch(rest, possibleMatch.count42 - 1));
                        }
                    }
                }
            }

            if (isValid) {
                countValid++;
            }
        }

        return countValid;
    }
}

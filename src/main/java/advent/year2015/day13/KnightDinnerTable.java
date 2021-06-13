/* Licensed under Apache-2.0 */
package advent.year2015.day13;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KnightDinnerTable {
    public static class GroupList {
        List<String> people;
        Map<String, Integer> netHappinessMap;

        public static String getPersonsKey(String person1, String person2) {
            return String.join(", ", Arrays.stream(new String[]{person1, person2}).sorted().toArray(String[]::new));
        }

        public GroupList(Map<String, Integer> nextHappinessMap) {
            this(nextHappinessMap, new LinkedList<>());
        }

        protected GroupList(Map<String, Integer> netHappinessMap, List<String> people) {
            this.people = people;
            this.netHappinessMap = netHappinessMap;
        }

        public GroupList addPerson(String person) {
            if (containsPerson(person)) {
                throw new IllegalArgumentException("Duplicate person");
            }

            List<String> newPeople = new LinkedList<>(people);
            newPeople.add(person);
            return new GroupList(this.netHappinessMap, newPeople);
        }

        public boolean containsPerson(String person) {
            return this.people.contains(person);
        }

        public int getHappiness() {
            int happiness = 0;

            for (int i = 0; i < people.size(); i++) {
                String person = people.get(i);
                String nextPerson = people.get((i + 1) % people.size());

                happiness += this.netHappinessMap.get(GroupList.getPersonsKey(person, nextPerson));
            }

            return happiness;
        }

        public int getSize() {
            return this.people.size();
        }
    }

    public static final Pattern rulePattern = Pattern.compile("^(\\w+).+?(gain|lose) (\\d+).+?(\\w+).$");

    public static int getChangeInHappiness(String rules) {
        String[] ruleArray = rules.split("\n");

        Map<String, Integer> netHappinessMap = new HashMap<>();
        Set<String> people = new HashSet<>();

        for (String rule : ruleArray) {
            Matcher ruleMatcher = rulePattern.matcher(rule);

            ruleMatcher.find();
            String person1 = ruleMatcher.group(1);
            boolean isNegative = ruleMatcher.group(2).equals("lose");
            int amount = (isNegative ? -1 : 1) * Integer.parseInt(ruleMatcher.group(3));
            String person2 = ruleMatcher.group(4);

            people.add(person1);
            people.add(person2);

            String key = GroupList.getPersonsKey(person1, person2);

            if (netHappinessMap.containsKey(key)) {
                netHappinessMap.put(key, netHappinessMap.get(key) + amount);
            } else {
                netHappinessMap.put(key, amount);
            }
        }

        Stack<GroupList> listsToCheck = new Stack<>();
        listsToCheck.add(new GroupList(netHappinessMap));

        int maxHappiness = Integer.MIN_VALUE;
        while (listsToCheck.size() > 0) {
            GroupList currentList = listsToCheck.pop();

            if (currentList.getSize() == people.size()) {
                int happiness = currentList.getHappiness();
                if (maxHappiness < happiness) {
                    maxHappiness = happiness;
                }
            } else {
                String[] nextPeople = people.stream().filter(Predicate.not(currentList::containsPerson)).toArray(String[]::new);

                for (String nextPerson : nextPeople) {
                    GroupList nextList = currentList.addPerson(nextPerson);

                    listsToCheck.add(nextList);
                }
            }
        }

        return maxHappiness;
    }

    public static int getChangeInHappinessWithMe(String rules) {
        String[] ruleArray = rules.split("\n");

        Map<String, Integer> netHappinessMap = new HashMap<>();
        Set<String> people = new HashSet<>();

        for (String rule : ruleArray) {
            Matcher ruleMatcher = rulePattern.matcher(rule);

            ruleMatcher.find();
            String person1 = ruleMatcher.group(1);
            boolean isNegative = ruleMatcher.group(2).equals("lose");
            int amount = (isNegative ? -1 : 1) * Integer.parseInt(ruleMatcher.group(3));
            String person2 = ruleMatcher.group(4);

            people.add(person1);
            people.add(person2);

            String key = GroupList.getPersonsKey(person1, person2);

            if (netHappinessMap.containsKey(key)) {
                netHappinessMap.put(key, netHappinessMap.get(key) + amount);
            } else {
                netHappinessMap.put(key, amount);
            }
        }

        String meString = "Me";
        for (String person : people) {
            netHappinessMap.put(GroupList.getPersonsKey(person, meString), 0);
        }
        people.add(meString);

        Stack<GroupList> listsToCheck = new Stack<>();
        listsToCheck.add(new GroupList(netHappinessMap));

        int maxHappiness = Integer.MIN_VALUE;
        while (listsToCheck.size() > 0) {
            GroupList currentList = listsToCheck.pop();

            if (currentList.getSize() == people.size()) {
                int happiness = currentList.getHappiness();
                if (maxHappiness < happiness) {
                    maxHappiness = happiness;
                }
            } else {
                String[] nextPeople = people.stream().filter(Predicate.not(currentList::containsPerson)).toArray(String[]::new);

                for (String nextPerson : nextPeople) {
                    GroupList nextList = currentList.addPerson(nextPerson);

                    listsToCheck.add(nextList);
                }
            }
        }

        return maxHappiness;
    }
}

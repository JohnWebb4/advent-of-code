package advent.year2016.day10;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BalanceBots {
    public static final Pattern BOT_INSTRUCTION = Pattern.compile("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)");
    public static final Pattern VALUE_INSTRUCTION = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    public static final int MAX_CHIPS_HELD = 2;

    public static class Bot {
        public final int botNumber;
        public final int maxChips;
        private List<Integer> lowList;
        private List<Integer> highList;
        private List<Integer> chips;
        private Set<Integer> chipsHasHandled;

        public Bot(int botNumber, int maxChips, List<Integer> lowList, List<Integer> highList) {
            this.botNumber = botNumber;
            this.maxChips = maxChips;
            this.lowList = lowList;
            this.highList = highList;
            this.chips = new LinkedList<>();
            this.chipsHasHandled = new HashSet<>();
        }

        public void addChip(int value) {
            this.chips.add(value);
            this.chipsHasHandled.add(value);
        }

        public boolean hasHeldAll(List<Integer> chips) {
            return this.chipsHasHandled.containsAll(chips);
        }
    }

    public static int getBotWithMicrochips(String instructionString, int[] chips) {
        String[] instructions = instructionString.split("\n");
        List<Integer> chipList = Arrays.stream(chips).boxed().collect(Collectors.toList());
        Map<Integer, Bot> botMap = new HashMap<>();
        Map<Integer, List<Integer>> outputMap = new HashMap<>();

        for (String instruction : instructions) {
            if (BOT_INSTRUCTION.matcher(instruction).matches()) {
                Matcher botMatcher = BOT_INSTRUCTION.matcher(instruction);
                botMatcher.find();

                int botNum = Integer.parseInt(botMatcher.group(1));
                String lowType = botMatcher.group(2);
                int lowValue = Integer.parseInt(botMatcher.group(3));
                String highType = botMatcher.group(4);
                int highValue = Integer.parseInt(botMatcher.group(5));
                List<Integer> lowList;
                List<Integer> highList;

                if (lowType.equals("bot")) {
                    botMap.putIfAbsent(lowValue, new Bot(lowValue, MAX_CHIPS_HELD, null, null));
                    lowList = botMap.get(lowValue).chips;
                } else {
                    outputMap.putIfAbsent(lowValue, new LinkedList<>());
                    lowList = outputMap.get(lowValue);
                }

                if (highType.equals("bot")) {
                    botMap.putIfAbsent(highValue, new Bot(highValue, MAX_CHIPS_HELD, null, null));
                    highList = botMap.get(highValue).chips;
                } else {
                    outputMap.putIfAbsent(highValue, new LinkedList<>());
                    highList = outputMap.get(highValue);
                }

                if (botMap.containsKey(botNum)) {
                    Bot bot = botMap.get(botNum);
                    bot.lowList = lowList;
                    bot.highList = highList;
                } else {
                    botMap.put(botNum, new Bot(botNum, MAX_CHIPS_HELD, lowList, highList));
                }
            } else if (VALUE_INSTRUCTION.matcher(instruction).matches()) {
                Matcher valueMatcher = VALUE_INSTRUCTION.matcher(instruction);
                valueMatcher.find();

                int value = Integer.parseInt(valueMatcher.group(1));
                int botNum = Integer.parseInt(valueMatcher.group(2));

                if (botMap.containsKey(botNum)) {
                    botMap.get(botNum).addChip(value);
                } else {
                    Bot bot = new Bot(botNum, MAX_CHIPS_HELD, null, null);
                    bot.addChip(value);

                    botMap.put(botNum, bot);
                }
            }
        }

        for (long i = 0; i < 100000L; i++) {
            for (Bot bot : botMap.values()) {
                if (bot.hasHeldAll(chipList)) {
                    System.out.println(String.format("YO %s", bot.botNumber));
//                    return bot.botNumber;
                }

                if (bot.chips.size() >= bot.maxChips) {
                    if (bot.chips.contains(17)) {
                        System.out.println(String.format("Bot %s has %s", bot.botNumber, String.join(",", bot.chips.stream().map(String::valueOf).toArray(String[]::new))));
                    }

                    for (int chipIndex = 0; chipIndex < bot.chips.size(); chipIndex++) {
                        int value = bot.chips.remove(0);

                        if (value > bot.botNumber) {
                            bot.highList.add(value);
                        } else {
                            bot.lowList.add(value);
                        }

                        bot.chipsHasHandled.add(value);
                    }
                }
            }
        }

        return -1;
    }
}

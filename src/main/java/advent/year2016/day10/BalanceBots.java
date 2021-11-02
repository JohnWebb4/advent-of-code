package advent.year2016.day10;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BalanceBots {
    public static final Pattern BOT_INSTRUCTION = Pattern.compile("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)");
    public static final Pattern VALUE_INSTRUCTION = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    public static final int MAX_CHIPS_HELD = 2;

    public interface IHoldsChips {
        void addChip(int value);

        int getChipCount();
    }

    public static class Output implements IHoldsChips {
        private final List<Integer> chips;

        public Output() {
            this.chips = new LinkedList<>();
        }

        public void addChip(int value) {
            this.chips.add(value);
        }

        public int getChipCount() {
            return this.chips.size();
        }
    }

    public static class Bot implements IHoldsChips {
        public final int botNumber;
        public final int maxChips;
        private IHoldsChips lowList;
        private IHoldsChips highList;
        private final List<Integer> chips;
        private final Set<Integer> chipsHasHandled;

        public Bot(int botNumber, int maxChips, IHoldsChips lowList, IHoldsChips highList) {
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

        public int getChipCount() {
            return this.chips.size();
        }

        public boolean hasHeldAll(List<Integer> chips) {
            return this.chipsHasHandled.containsAll(chips);
        }
    }

    public static int getBotWithMicrochips(String instructionString, int[] chips) {
        String[] instructions = instructionString.split("\n");
        List<Integer> chipList = Arrays.stream(chips).boxed().collect(Collectors.toList());
        Map<Integer, Bot> botMap = new HashMap<>();
        Map<Integer, Output> outputMap = new HashMap<>();

        for (String instruction : instructions) {
            if (BOT_INSTRUCTION.matcher(instruction).matches()) {
                Matcher botMatcher = BOT_INSTRUCTION.matcher(instruction);
                botMatcher.find();

                int botNum = Integer.parseInt(botMatcher.group(1));
                String lowType = botMatcher.group(2);
                int lowValue = Integer.parseInt(botMatcher.group(3));
                String highType = botMatcher.group(4);
                int highValue = Integer.parseInt(botMatcher.group(5));
                IHoldsChips lowList;
                IHoldsChips highList;

                if (lowType.equals("bot")) {
                    botMap.putIfAbsent(lowValue, new Bot(lowValue, MAX_CHIPS_HELD, null, null));
                    lowList = botMap.get(lowValue);
                } else {
                    outputMap.putIfAbsent(lowValue, new Output());
                    lowList = outputMap.get(lowValue);
                }

                if (highType.equals("bot")) {
                    botMap.putIfAbsent(highValue, new Bot(highValue, MAX_CHIPS_HELD, null, null));
                    highList = botMap.get(highValue);
                } else {
                    outputMap.putIfAbsent(highValue, new Output());
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

        Queue<Bot> nextBotToCheck = new LinkedList<>();

        for (Bot bot : botMap.values()) {
            if (bot.chips.size() >= bot.maxChips) {
                nextBotToCheck.add(bot);
            }
        }

        while (nextBotToCheck.size() > 0) {
            Bot bot = nextBotToCheck.poll();

            if (bot.hasHeldAll(chipList)) {
                return bot.botNumber;
            }

            while (bot.chips.size() >= bot.maxChips) {
                int value0 = bot.chips.remove(0);
                int value1 = bot.chips.remove(0);

                int[] values = new int[]{value0, value1};

                for (int value : values) {
                    if (value > bot.botNumber) {
                        bot.highList.addChip(value);

                        if (bot.highList instanceof Bot) {
                            Bot highBot = (Bot) bot.highList;

                            if (highBot.hasHeldAll(chipList)) {
                                return highBot.botNumber;
                            }

                            if (highBot.getChipCount() >= highBot.maxChips) {
                                nextBotToCheck.add((Bot) bot.highList);
                            }
                        }
                    } else {
                        bot.lowList.addChip(value);

                        if (bot.lowList instanceof Bot) {
                            Bot lowBot = (Bot) bot.lowList;

                            if (lowBot.hasHeldAll(chipList)) {
                                return lowBot.botNumber;
                            }

                            if (lowBot.getChipCount() >= lowBot.maxChips) {
                                nextBotToCheck.add((Bot) bot.lowList);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }

}

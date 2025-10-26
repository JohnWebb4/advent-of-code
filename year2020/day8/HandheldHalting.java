/* Licensed under Apache-2.0 */
package advent.year2020.day8;

import java.util.*;

public class HandheldHalting {
	public static class Rule {
		String command;
		int amount;

		public Rule(String command, int amount) {
			this.command = command;
			this.amount = amount;
		}
	}

	public static int getValueBeforeLoop(String input) {
		int index = 0;
		int globalAccumulator = 0;

		String[] rules = input.split("\n");
		Set<Integer> indexesSeen = new HashSet<>();

		while (!indexesSeen.contains(index)) {
			String[] commands = rules[index].split(" ");
			String command = commands[0];
			int amount = Integer.parseInt(commands[1]);
			int newIndex = index;


			if (command.equals("nop")) {
				newIndex++;
			} else if (command.equals("acc")) {
				globalAccumulator += amount;
				newIndex++;
			} else if (command.equals("jmp")) {
				newIndex += amount;
			}

			indexesSeen.add(index);
			index = newIndex;
		}

		return globalAccumulator;
	}

	public static int fixAndGetValue(String input) {
		int index = 0;
		int globalAccumulator = 0;

		Rule[] rules = Arrays.stream(input.split("\n")).map((s) -> {
			String[] commands = s.split(" ");
			String command = commands[0];
			int amount = Integer.parseInt(commands[1]);

			return new Rule(command, amount);
		}).toArray(Rule[]::new);
		Queue<Integer> indexesSeen = new LinkedList<>();

		while (!indexesSeen.contains(index)) {
			Rule rule = rules[index];
			int newIndex = index;

			if (rule.command.equals("nop")) {
				newIndex++;
			} else if (rule.command.equals("acc")) {
				globalAccumulator += rule.amount;
				newIndex++;
			} else if (rule.command.equals("jmp")) {
				newIndex += rule.amount;
			}

			indexesSeen.add(index);
			index = newIndex;
		}


		while (indexesSeen.size() > 0) {
			int swappedindex = indexesSeen.remove();
			Rule swappedRule = rules[swappedindex];
			Rule previousRule = new Rule(swappedRule.command, swappedRule.amount);

			switch (swappedRule.command) {
				case "nop":
					swappedRule.command = "jmp";
					break;
				case "jmp":
					swappedRule.command = "nop";
					break;
				case "acc":
					continue;
			}

			Set<Integer> testIndexesSeen = new HashSet<>();

			int testIndex = 0;
			int testGlobalAccumulator = 0;
			while (!testIndexesSeen.contains(testIndex)) {
				Rule rule = rules[testIndex];
				int newTestIndex = testIndex;

				switch (rule.command) {
					case "nop":
						newTestIndex++;
						break;
					case "acc":
						testGlobalAccumulator += rule.amount;
						newTestIndex++;
						break;
					case "jmp":
						newTestIndex += rule.amount;
						break;
				}

				if (newTestIndex >= rules.length) {
					return testGlobalAccumulator;
				}

				testIndexesSeen.add(testIndex);
				testIndex = newTestIndex;
			}

			rules[swappedindex] = previousRule;
		}

		return -1;
	}
}

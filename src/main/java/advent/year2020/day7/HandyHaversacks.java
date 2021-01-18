/* Licensed under Apache-2.0 */
package advent.year2020.day7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandyHaversacks {
	public static class Node {
		public final String color;
		public int count;
		public int multiplier = 1;
		public Map<String, Node> children = new HashMap<>();

		public Node(String color, int count) {
			this.color = color;
			this.count = count;
		}
	}

	public static int countTypesCanContainGold(String input) {
		String[] rules = input.toLowerCase(Locale.ROOT).split("\n");
		Map<String, Node> bagMap = new HashMap<>();
		String colorCaptureString = "(\\d+) (\\s*[a-z]*\\s*[a-z]+) (?=bag)";
		Pattern colorPattern = Pattern.compile(colorCaptureString);

		for (String rule : rules) {
			String[] parts = rule.split("contain");
			String holderColor = parts[0].split(" bags")[0].trim();

			Matcher contentMatcher = colorPattern.matcher(parts[1]);
			ArrayList<Node> contents = new ArrayList<>();

			while (contentMatcher.find()) {
				String color = contentMatcher.group(2);
				int count = Integer.parseInt(contentMatcher.group(1));

				contents.add(new Node(color, count));
			}

			Node holder;
			if (bagMap.containsKey(holderColor)) {
				holder = bagMap.get(holderColor);
			} else {
				holder = new Node(holderColor, 0);
				bagMap.put(holderColor, holder);
			}

			for (Node content : contents) {
				if (bagMap.containsKey(content.color)) {
					content = bagMap.get(content.color);
				} else {
					bagMap.put(content.color, content);
				}

				if (!content.children.containsKey(holderColor)) {
					content.children.put(holderColor, holder);
				}
			}
		}

		HashMap<String, Node> nodesContainShinyGold = new HashMap<>();
		Queue<Node> openList = new LinkedList<>();

		openList.add(bagMap.get("shiny gold"));
		while (openList.size() > 0) {
			Node current = openList.remove();

			for (String childKey : current.children.keySet()) {
				Node child = current.children.get(childKey);

				if (!nodesContainShinyGold.containsKey(child.color)) {
					nodesContainShinyGold.put(child.color, child);
					openList.add(child);
				}
			}
		}

		return nodesContainShinyGold.size();
	}

	public static int countBagsRequiredForGold(String input) {
		String[] rules = input.toLowerCase(Locale.ROOT).split("\n");
		Map<String, Node> bagMap = new HashMap<>();
		String colorCaptureString = "(\\d+) (\\s*[a-z]*\\s*[a-z]+) (?=bag)";
		Pattern colorPattern = Pattern.compile(colorCaptureString);

		for (String rule : rules) {
			String[] parts = rule.split("contain");
			String holderColor = parts[0].split(" bags")[0].trim();

			Matcher contentMatcher = colorPattern.matcher(parts[1]);
			ArrayList<Node> contents = new ArrayList<>();

			while (contentMatcher.find()) {
				String color = contentMatcher.group(2);
				int count = Integer.parseInt(contentMatcher.group(1));

				contents.add(new Node(color.trim(), count));
			}

			Node holder = new Node(holderColor, 1);
			bagMap.put(holderColor, holder);

			for (Node stubContent : contents) {
				holder.children.put(stubContent.color, stubContent);
			}
		}

		Queue<Node> openList = new LinkedList<>();
		Node shinyGoldNode = bagMap.get("shiny gold");
		shinyGoldNode.count = 1;
		openList.add(shinyGoldNode);
		int count = 0;

		while (openList.size() > 0) {
			Node current = openList.remove();

			if (!current.color.equals("shiny gold")) {
				count += current.count * current.multiplier;
			}

			for (String childKey : current.children.keySet()) {
				Node child = current.children.get(childKey);
				Node mapChild = bagMap.get(childKey);

				child.children = mapChild.children;
				child.multiplier = current.count * current.multiplier;

				openList.add(child);
			}
		}

		return count;
	}
}

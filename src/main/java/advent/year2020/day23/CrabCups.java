/* Licensed under Apache-2.0 */
package advent.year2020.day23;

import java.util.HashMap;
import java.util.Map;

public class CrabCups {
	public static class Node {
		public final int value;
		public Node next;

		public Node(int value, Node next) {
			this.value = value;
			this.next = next;
		}
	}

	public static long getStateAfterXMoves(int n, long cupOrder) {
		String[] cups = Long.toString(cupOrder).split("");

		Node startNode = new Node(Integer.parseInt(cups[0]), null);
		Node currentNode = startNode;
		Map<Integer, Node> nodeMap = new HashMap<>();
		nodeMap.put(startNode.value, startNode);
		int maxValue = Integer.MIN_VALUE;
		int minValue = Integer.MAX_VALUE;
		for (int i = 1; i < cups.length; i++) {
			Node nextNode = new Node(Integer.parseInt(cups[i]), null);

			if (nextNode.value > maxValue) {
				maxValue = nextNode.value;
			}

			if (nextNode.value < minValue) {
				minValue = nextNode.value;
			}

			nodeMap.put(nextNode.value, nextNode);
			currentNode.next = nextNode;
			currentNode = nextNode;
		}

		// Cyclic
		currentNode.next = startNode;

		currentNode = startNode;

		for (int i = 0; i < n; i++) {
			Node nextNode = currentNode.next;
			Node next1Node = nextNode.next;
			Node next2Node = next1Node.next;

			// Sever next three cups
			currentNode.next = next2Node.next;
			next2Node.next = null;

			// Get destination
			int destinationValue = currentNode.value;
			Node destinationNode;
			do {
				destinationValue = destinationValue - 1;

				if (destinationValue < minValue) {
					destinationValue = maxValue;
				}

				destinationNode = nodeMap.get(destinationValue);
			} while (!nodeMap.containsKey(destinationValue) || (destinationNode == nextNode || destinationNode == next1Node || destinationNode == next2Node || destinationNode == currentNode));

			// Splice nodes
			Node endNode = destinationNode.next;
			destinationNode.next = nextNode;
			next2Node.next = endNode;

			currentNode = currentNode.next;
		}

		StringBuilder resultOrder = new StringBuilder();
		currentNode = nodeMap.get(1);
		Node resultOrderNode = currentNode.next;
		while (currentNode != resultOrderNode) {
			resultOrder.append(resultOrderNode.value);
			resultOrderNode = resultOrderNode.next;
		}

		return Long.parseLong(resultOrder.toString());
	}
}

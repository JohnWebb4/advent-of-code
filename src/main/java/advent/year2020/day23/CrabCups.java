/* Licensed under Apache-2.0 */
package advent.year2020.day23;

import java.util.HashMap;
import java.util.Map;

public class CrabCups {
    public static class Node {
        public final long value;
        public Node next;

        public Node(long value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public static long getStateAfterXMoves(int n, long cupOrder) {
        String[] cups = Long.toString(cupOrder).split("");

        Node startNode = new Node(Integer.parseInt(cups[0]), null);
        Node currentNode = startNode;
        Map<Long, Node> nodeMap = new HashMap<>();
        nodeMap.put(startNode.value, startNode);
        long maxValue = startNode.value;
        long minValue = startNode.value;
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
            long destinationValue = currentNode.value - 1;
            Node destinationNode = null;
            do {
                if (destinationValue < minValue) {
                    destinationValue = maxValue;
                }

                destinationNode = nodeMap.get(destinationValue);
                destinationValue = destinationValue - 1;
            } while (destinationNode == null || destinationNode == nextNode || destinationNode == next1Node || destinationNode == next2Node);

            // Splice nodes
            Node endNode = destinationNode.next;
            destinationNode.next = nextNode;
            next2Node.next = endNode;

            currentNode = currentNode.next;
        }

        StringBuilder resultOrder = new StringBuilder();
        currentNode = nodeMap.get(1l);
        Node resultOrderNode = currentNode.next;
        while (currentNode != resultOrderNode) {
            resultOrder.append(resultOrderNode.value);
            resultOrderNode = resultOrderNode.next;
        }

        return Long.parseLong(resultOrder.toString());
    }

    public static long getProductOfStarCupsAfterXMoves(int n, long cupOrder) {
        String[] cups = Long.toString(cupOrder).split("");

        Node startNode = new Node(Integer.parseInt(cups[0]), null);
        Node currentNode = startNode;
        Map<Long, Node> nodeMap = new HashMap<>();
        nodeMap.put(startNode.value, startNode);
        long maxValue = startNode.value;
        long minValue = startNode.value;
        for (int i = 1; i < 1000000; i++) {
            Node nextNode;
            if (i < cups.length) {
                nextNode = new Node(Integer.parseInt(cups[i]), null);
            } else {
                nextNode = new Node(++maxValue, null);
            }

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
            long destinationValue = currentNode.value - 1;
            Node destinationNode = null;
            do {
                if (destinationValue < minValue) {
                    destinationValue = maxValue;
                }

                destinationNode = nodeMap.get(destinationValue);
                destinationValue = destinationValue - 1;
            } while (destinationNode == null || destinationNode == nextNode || destinationNode == next1Node || destinationNode == next2Node);

            // Splice nodes
            Node endNode = destinationNode.next;
            destinationNode.next = nextNode;
            next2Node.next = endNode;

            currentNode = currentNode.next;
        }

        currentNode = nodeMap.get(1l);

        Node nodeAfterCurrent = currentNode.next;
        Node nodeAfterCurrent2 = nodeAfterCurrent.next;

        return nodeAfterCurrent.value * nodeAfterCurrent2.value;
    }
}

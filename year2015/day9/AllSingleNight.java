/* Licensed under Apache-2.0 */
package advent.year2015.day9;

import java.util.*;
import java.util.function.Predicate;

public class AllSingleNight {
    public static class Node {
        public String name;
        public Map<Node, Integer> pathMap;

        public Node(String name) {
            this.name = name;
            this.pathMap = new HashMap<>();
        }
    }

    public static class Path {
        public Node position;
        public long distanceTraveled;
        public Set<Node> seen;

        public Path(Node position, long distanceTraveled, Set<Node> seen) {
            this.position = position;
            this.distanceTraveled = distanceTraveled;
            this.seen = new HashSet<>(seen);
            this.seen.add(position);
        }
    }

    public static long getShortestDistance(String input) {
        Map<String, Node> mapPaths = new HashMap<>();

        for (String row : input.split("\n")) {
            String[] parts = row.split(" to ");
            String start = parts[0];
            String end = parts[1].split(" = ")[0];
            int distance = Integer.parseInt(parts[1].split(" = ")[1]);

            Node startNode;
            if (!mapPaths.containsKey(start)) {
                startNode = new Node(start);
                mapPaths.put(start, startNode);
            } else {
                startNode = mapPaths.get(start);
            }

            Node endNode;
            if (!mapPaths.containsKey(end)) {
                endNode = new Node(end);
                mapPaths.put(end, endNode);
            } else {
                endNode = mapPaths.get(end);
            }

            startNode.pathMap.put(endNode, distance);
            endNode.pathMap.put(startNode, distance);
        }

        long minDistance = Long.MAX_VALUE;
        Stack<Path> pathsToTest = new Stack<>();

        for (Node node : mapPaths.values()) {
            Path path = new Path(node, 0, new HashSet<>());

            pathsToTest.add(path);
        }

        while (pathsToTest.size() > 0) {
            Path path = pathsToTest.pop();

            if (path.distanceTraveled >= minDistance) {
                continue;
            }

            Node[] availableNodes = path.position.pathMap.keySet().stream().filter(Predicate.not(path.seen::contains)).toArray(Node[]::new);

            if (availableNodes.length == 0) {
                // Done. Seen all nodes?
                if (path.seen.containsAll(mapPaths.values()) && minDistance > path.distanceTraveled) {
                    minDistance = path.distanceTraveled;
                }
            }

            for (Node availableNode : availableNodes) {
                long nextDistance = path.distanceTraveled + path.position.pathMap.get(availableNode);

                if (nextDistance < minDistance) {
                    Path nextPath = new Path(availableNode, nextDistance, path.seen);

                    pathsToTest.push(nextPath);
                }
            }
        }

        return minDistance;
    }

    public static long getLongestDistance(String input) {
        Map<String, Node> mapPaths = new HashMap<>();

        for (String row : input.split("\n")) {
            String[] parts = row.split(" to ");
            String start = parts[0];
            String end = parts[1].split(" = ")[0];
            int distance = Integer.parseInt(parts[1].split(" = ")[1]);

            Node startNode;
            if (!mapPaths.containsKey(start)) {
                startNode = new Node(start);
                mapPaths.put(start, startNode);
            } else {
                startNode = mapPaths.get(start);
            }

            Node endNode;
            if (!mapPaths.containsKey(end)) {
                endNode = new Node(end);
                mapPaths.put(end, endNode);
            } else {
                endNode = mapPaths.get(end);
            }

            startNode.pathMap.put(endNode, distance);
            endNode.pathMap.put(startNode, distance);
        }

        long maxDistance = 0;
        Stack<Path> pathsToTest = new Stack<>();

        for (Node node : mapPaths.values()) {
            Path path = new Path(node, 0, new HashSet<>());

            pathsToTest.add(path);
        }

        while (pathsToTest.size() > 0) {
            Path path = pathsToTest.pop();

            Node[] availableNodes = path.position.pathMap.keySet().stream().filter(Predicate.not(path.seen::contains)).toArray(Node[]::new);

            if (availableNodes.length == 0) {
                // Done. Seen all nodes?
                if (path.seen.containsAll(mapPaths.values()) && maxDistance < path.distanceTraveled) {
                    maxDistance = path.distanceTraveled;
                }
            }

            for (Node availableNode : availableNodes) {
                long nextDistance = path.distanceTraveled + path.position.pathMap.get(availableNode);

                Path nextPath = new Path(availableNode, nextDistance, path.seen);

                pathsToTest.push(nextPath);
            }
        }

        return maxDistance;
    }
}

/* Licensed under Apache-2.0 */
package advent.year2019.day18;

import java.util.*;

public class MazePathFinder {
    Map<String, int[]> keyMap;
    Node[][] mazeMap;
    TreeMap<String, Integer> pathsMap;

    static class Node {
        public final int x;
        public final int y;
        public final String value;
        public int distanceFromStart;
        public int estimatedCostToEnd;

        public Node(int x, int y, String value, int distanceFromStart, int estimatedCostToEnd) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.distanceFromStart = distanceFromStart;
            this.estimatedCostToEnd = estimatedCostToEnd;
        }
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o, Node t1) {
            return o.distanceFromStart + o.estimatedCostToEnd - t1.distanceFromStart - t1.estimatedCostToEnd;
        }
    }

    static final NodeComparator nodeComparator = new NodeComparator();

    static int getTaxiDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    public void parseMaze(String maze) {
        this.keyMap = new HashMap<>();

        String[] mazeRows = maze.split("\n");
        Node[][] mazeMap = new Node[mazeRows.length][mazeRows[0].length()];

        for (int i = 0; i < mazeRows.length; i++) {
            String[] columns = mazeRows[i].split("");

            for (int j = 0; j < columns.length; j++) {
                if (columns[j].matches("[a-z]")) {
                    keyMap.put(columns[j], new int[]{i, j});
                } else if (columns[j].equals("@")) {
                    keyMap.put("@", new int[]{i, j});
                }

                mazeMap[i][j] = new Node(i, j, columns[j], -1, -1);
            }
        }

        this.mazeMap = mazeMap;
    }

    public int findStepsToGrabAllKeys(String maze) throws IllegalArgumentException {
        this.parseMaze(maze);

        this.pathsMap = new TreeMap<>();

        return findStepsToGrabAllKeys();
    }

    protected int findStepsToGrabAllKeys() throws IllegalArgumentException {
        int minSteps = -1;
        List<String> keys = new ArrayList<>(this.keyMap.keySet());

        PriorityQueue<String> pathsToCheck = new PriorityQueue<>(String::compareTo); // Can't be stack because we will remove from middle
        pathsToCheck.add("0-@");

        while (pathsToCheck.size() != 0) {
            String[] data = pathsToCheck.remove().split("-");
            String[] path = data[1].split(" ");
            int numSteps = Integer.parseInt(data[0]);

            if (minSteps >= 0 && minSteps < numSteps) {
                continue;
            }

            if (path.length < keys.size()) {
                for (String key : keys) {
                    boolean hasKey = false;
                    for (String location : path) {
                        if (location.equals(key)) {
                            hasKey = true;
                            break;
                        }
                    }

                    if (!hasKey) {
                        try {
                            int newNumSteps = this.getSteps(path[path.length - 1], key, path) + numSteps;

                            if (newNumSteps < minSteps || minSteps == -1) {
                                String newPath = String.format("%s-%s", String.format("%05d", newNumSteps), String.join(" ", path) + " " + key);

                                List<String> pathList = new ArrayList(Arrays.asList(path));
                                pathList.sort(String::compareTo);
                                pathList.add(key);

                                String cacheKey = String.join(",", pathList);

                                if (this.pathsMap.containsKey(cacheKey)) {
                                    if (this.pathsMap.get(cacheKey) > newNumSteps) {
                                        this.pathsMap.put(cacheKey, newNumSteps);
                                        pathsToCheck.add(newPath);
                                    }
                                } else {
                                    this.pathsMap.put(cacheKey, newNumSteps);
                                    pathsToCheck.add(newPath);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                        }
                    }
                }
            } else if (numSteps < minSteps || minSteps == -1) {
                minSteps = numSteps;
            }
        }

        if (minSteps == -1) {
            throw new IllegalArgumentException("Cannot solve maze");
        }

        return minSteps;
    }

    int getSteps(String startKey, String endKey, String[] keys) throws IllegalArgumentException {
        // A*
        int[] startPosition = this.keyMap.get(startKey);
        int[] endPosition = this.keyMap.get(endKey);

        PriorityQueue<Node> locationsToCheck = new PriorityQueue<>(nodeComparator);
        List<Node> checkedLocations = new LinkedList<>();

        this.mazeMap[startPosition[0]][startPosition[1]].distanceFromStart = 0; // This is start
        locationsToCheck.add(this.mazeMap[startPosition[0]][startPosition[1]]);

        while (locationsToCheck.size() > 0) {
            Node location = locationsToCheck.remove();

            if (location.x == endPosition[0] && location.y == endPosition[1]) {
                return location.distanceFromStart;
            }

            List<Node> adjacentLocations = new LinkedList<>();

            if (location.x > 0) {
                adjacentLocations.add(this.mazeMap[location.x - 1][location.y]);
            }

            if (location.x < this.mazeMap.length) {
                adjacentLocations.add(this.mazeMap[location.x + 1][location.y]);
            }

            if (location.y > 0) {
                adjacentLocations.add(this.mazeMap[location.x][location.y - 1]);
            }

            if (location.y < this.mazeMap[0].length) {
                adjacentLocations.add(this.mazeMap[location.x][location.y + 1]);
            }

            while (adjacentLocations.size() > 0) {
                Node adjacentLocation = adjacentLocations.remove(0);

                if (!checkedLocations.contains(adjacentLocation)) {
                    if (adjacentLocation.value.matches("[.@a-z]")) {
                        if (!locationsToCheck.contains(adjacentLocation)) {
                            adjacentLocation.distanceFromStart = location.distanceFromStart + 1;
                            adjacentLocation.estimatedCostToEnd = getTaxiDistance(adjacentLocation.x, adjacentLocation.y, endPosition[0], endPosition[1]);

                            locationsToCheck.add(adjacentLocation);
                        }
                    } else if (adjacentLocation.value.matches("[A-Z]")) {
                        boolean hasKeyToDoorOrKey = false;
                        for (String key : keys) {
                            if (key.equals(adjacentLocation.value.toLowerCase())) {
                                hasKeyToDoorOrKey = true;
                                break;
                            }
                        }

                        if (hasKeyToDoorOrKey) {
                            if (!locationsToCheck.contains(adjacentLocation)) {
                                adjacentLocation.distanceFromStart = location.distanceFromStart + 1;
                                adjacentLocation.estimatedCostToEnd = getTaxiDistance(adjacentLocation.x, adjacentLocation.y, endPosition[0], endPosition[1]);

                                locationsToCheck.add(adjacentLocation);
                            }
                        }
                    }
                }
            }

            checkedLocations.add(location);
        }

        throw new IllegalArgumentException("Cannot solve path");
    }
}

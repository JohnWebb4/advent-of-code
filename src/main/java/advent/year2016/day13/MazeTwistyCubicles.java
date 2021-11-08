/* Licensed under Apache-2.0 */
package advent.year2016.day13;

import java.util.*;

public class MazeTwistyCubicles {
    public static final int MAZE_SCALE_FROM_END = 2;

    public static class MazeState implements Comparable<MazeState> {
        public final int steps;
        public final int x;
        public final int y;
        public final int endX;
        public final int endY;

        public MazeState(int steps, int x, int y, int endX, int endY) {
            this.steps = steps;
            this.x = x;
            this.y = y;
            this.endX = endX;
            this.endY = endY;
        }

        @Override
        public int compareTo(MazeState o) {
            int thisScore = this.steps + this.endX - this.x + this.endY - this.y;
            int oScore = o.steps + o.endX - o.x + o.endY - o.y;

            return thisScore - oScore;
        }
    }

    public static int countLocationsWithinXSteps(int favoriateNumber, int numSteps) {
        boolean[][] maze = getMaze(MAZE_SCALE_FROM_END * numSteps, MAZE_SCALE_FROM_END * numSteps, favoriateNumber);

        return countLocationsWithinXSteps(maze, numSteps);
    }

    public static int countLocationsWithinXSteps(boolean[][] maze, int numSteps) {
        PriorityQueue<MazeState> stateQueue = new PriorityQueue<>();
        stateQueue.add(new MazeState(0, 1, 1, 1, 1));

        Set<String> hasSeenStates = new HashSet<>();
        hasSeenStates.add("1,1");

        while (stateQueue.size() > 0) {
            MazeState mazeState = stateQueue.poll();

            int[][] neighbors = new int[][]{
                    new int[]{1, 0},
                    new int[]{-1, 0},
                    new int[]{0, 1},
                    new int[]{0, -1},
            };

            for (int[] neighbor : neighbors) {
                int newX = neighbor[0] + mazeState.x;
                int newY = neighbor[1] + mazeState.y;

                if (mazeState.steps < numSteps && newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[newX].length && !maze[newX][newY]) {
                    String key = newX + "," + newY;

                    if (!hasSeenStates.contains(key)) {
                        hasSeenStates.add(key);

                        MazeState nextState = new MazeState(mazeState.steps + 1, newX, newY, mazeState.endX, mazeState.endY);

                        stateQueue.add(nextState);
                    }
                }
            }
        }

        return hasSeenStates.size();
    }

    public static int findFewestStepsToReachPoint(int favoriteNumber, int endX, int endY) {
        boolean[][] maze = getMaze(MAZE_SCALE_FROM_END * endX, MAZE_SCALE_FROM_END * endY, favoriteNumber);

        return findFewestStepsToReachPoint(maze, endX, endY);
    }

    public static int findFewestStepsToReachPoint(boolean[][] maze, int endX, int endY) {
        PriorityQueue<MazeState> stateQueue = new PriorityQueue<>();
        stateQueue.add(new MazeState(0, 1, 1, endX, endY));

        Map<Integer, Map<Integer, Integer>> hasSeenStates = new HashMap<>();
        hasSeenStates.put(1, new HashMap<>());
        hasSeenStates.get(1).put(1, 0);

        while (stateQueue.size() > 0) {
            MazeState mazeState = stateQueue.poll();

            if (mazeState.x == mazeState.endX && mazeState.y == mazeState.endY) {
                return mazeState.steps;
            } else {
                int[][] neighbors = new int[][]{
                        new int[]{1, 0},
                        new int[]{-1, 0},
                        new int[]{0, 1},
                        new int[]{0, -1},
                };

                for (int[] neighbor : neighbors) {
                    int newX = neighbor[0] + mazeState.x;
                    int newY = neighbor[1] + mazeState.y;

                    if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[newX].length && !maze[newX][newY]) {
                        hasSeenStates.putIfAbsent(newX, new HashMap<>());
                        int stepsToPrevReach = hasSeenStates.get(newX).getOrDefault(newY, Integer.MAX_VALUE);

                        if (mazeState.steps + 1 < stepsToPrevReach) {
                            hasSeenStates.get(newX).put(newY, mazeState.steps + 1);

                            MazeState nextState = new MazeState(mazeState.steps + 1, newX, newY, mazeState.endX, mazeState.endY);

                            stateQueue.add(nextState);
                        }
                    }
                }

            }
        }

        return -1;
    }

    public static boolean[][] getMaze(int width, int height, int favoriteNumber) {
        boolean[][] maze = new boolean[width][height];

        for (int i = 0; i < maze.length; i++) {
            maze[i] = new boolean[height];

            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = isCellWall(i, j, favoriteNumber);
            }
        }

        return maze;
    }

    public static boolean isCellWall(int x, int y, int favoriteNumber) {
        int value = x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber;
        String binaryValue = Integer.toBinaryString(value);
        int count1s = 0;

        for (char c : binaryValue.toCharArray()) {
            if (c == '1') {
                count1s++;
            }
        }

        return count1s % 2 == 1;
    }
}

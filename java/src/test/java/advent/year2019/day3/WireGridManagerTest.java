/* Licensed under Apache-2.0 */
package advent.year2019.day3;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.junit.Test;

public class WireGridManagerTest {
    public static String[] line1;
    public static String[] line2;

    @BeforeClass
    public static void setup() {
        // Load answer
        try {
            File file = new File("./src/test/java/advent/year2019/day3/Codes.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String lineString;
            line1 = br.readLine().split(",");
            line2 = br.readLine().split(",");

            br.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }

    @Test
    public void drawNewLine6() {
        String[] line1 = { "R8","U5","L5","D3" };
        String[] line2 = { "U7","R6", "D4", "L4" };
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);

        assertEquals(true, wireManager.grid.get(0).get(0).lines.contains(1));
        assertEquals(true, wireManager.grid.get(8).get(0).lines.contains(1));
        assertEquals(true, wireManager.grid.get(8).get(5).lines.contains(1));
        assertEquals(true, wireManager.grid.get(3).get(5).lines.contains(1));
        assertEquals(true, wireManager.grid.get(3).get(2).lines.contains(1));
        assertEquals(null, wireManager.grid.get(3).get(1));

        wireManager.drawNewLine(line2);

        assertEquals(true, wireManager.grid.get(0).get(0).lines.contains(2));
        assertEquals(true, wireManager.grid.get(0).get(7).lines.contains(2));
        assertEquals(true, wireManager.grid.get(6).get(7).lines.contains(2));
        assertEquals(true, wireManager.grid.get(6).get(3).lines.contains(2));
        assertEquals(true, wireManager.grid.get(2).get(3).lines.contains(2));
        assertEquals(null, wireManager.grid.get(1).get(3));
    }

    @Test
    public void getIntersections6() {
        String[] line1 = { "R8","U5","L5","D3" };
        String[] line2 = { "U7","R6", "D4", "L4" };
        WireGridManager wireManager = new WireGridManager();
        int[][] result = new int[][] {
                new int[] {
                        3, 3, 40
                },
                new int[] {
                        6, 5, 30
                }
        };

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertArrayEquals(result, wireManager.getIntersections());
    }

    @Test
    public void getDistanceToClosestIntersection6() {
        String[] line1 = { "R8","U5","L5","D3" };
        String[] line2 = { "U7","R6", "D4", "L4" };
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(6, wireManager.getIntersectionClosestToCenter());
    }

    @Test
    public void getDistanceToClosestIntersection135() {
        String[] line1 = { "R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"};
        String[] line2 = { "U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"};
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(135, wireManager.getIntersectionClosestToCenter());
    }

    @Test
    public void getDistanceToClosestIntersection159() {
        String[] line1 = { "R75","D30","R83","U83","L12","D49","R71","U7","L72" };
        String[] line2 = { "U62","R66","U55","R34","D71","R55","D58","R83"};
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(159, wireManager.getIntersectionClosestToCenter());
    }

    @Test
    public void getDistanceClosestToIntersectionAnswer() {
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(3247, wireManager.getIntersectionClosestToCenter());
    }

    @Test
    public void getDistanceToEarliestIntersection610() {
        String[] line1 = { "R75","D30","R83","U83","L12","D49","R71","U7","L72"};
        String[] line2 = { "U62","R66","U55","R34","D71","R55","D58","R83"};
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(610, wireManager.getDistanceToEarliestIntersection());
    }

    @Test
    public void getDistanceToEarliestIntersection410() {
        String[] line1 = { "R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"};
        String[] line2 = { "U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"};
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(410, wireManager.getDistanceToEarliestIntersection());
    }

    @Test
    public void getDistanceToEarliestIntersectionAnswer() {
        WireGridManager wireManager = new WireGridManager();

        wireManager.drawNewLine(line1);
        wireManager.drawNewLine(line2);

        assertEquals(48054, wireManager.getDistanceToEarliestIntersection());
    }
}
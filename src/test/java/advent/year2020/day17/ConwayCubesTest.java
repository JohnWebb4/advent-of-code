/* Licensed under Apache-2.0 */
package advent.year2020.day17;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ConwayCubesTest {
    public String test1;
    public String input;

    @Before
    public void initialize() {
        test1 = ".#.\n" +
                "..#\n" +
                "###";

        input = "...#...#\n" +
                "..##.#.#\n" +
                "###..#..\n" +
                "........\n" +
                "...##.#.\n" +
                ".#.####.\n" +
                "...####.\n" +
                "..##...#";
    }

    @Test
    public void simulateAndCountActive_11() {
        assertEquals(11, ConwayCubes.simulateAndCountActive(test1, 1));
    }

    @Test
    public void simulateAndCountActive_112() {
        assertEquals(112, ConwayCubes.simulateAndCountActive(test1, 6));
    }

    @Test
    public void simulateAndCountActive_223() {
        assertEquals(223, ConwayCubes.simulateAndCountActive(input, 6));
    }

    @Test
    public void simulate4DAndCountActive_848() {
        assertEquals(848, ConwayCubes.simulate4DAndCountActive(test1, 6));
    }

    @Test
    public void simulate4DAndCountActive_1884() {
        assertEquals(1884, ConwayCubes.simulate4DAndCountActive(input, 6));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2015.day18;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class GifYardTest {
    static String test1;
    static String input;

    @BeforeClass
    public static void initialize() {
        test1 = ".#.#.#\n" +
                "...##.\n" +
                "#....#\n" +
                "..#...\n" +
                "#.#..#\n" +
                "####..";

        input = "####.#.##.###.#.#.##.#..###.#..#.#.#..##....#.###...##..###.##.#.#.#.##...##..#..#....#.#.##..#...##\n" +
                ".##...##.##.######.#.#.##...#.#.#.#.#...#.##.#..#.#.####...#....#....###.#.#.#####....#.#.##.#.#.##.\n" +
                "###.##..#..#####.......#.########...#.####.###....###.###...#...####.######.#..#####.#.###....####..\n" +
                "....#..#..#....###.##.#.....##...#.###.#.#.#..#.#..##...#....#.##.###.#...######......#..#.#..####.#\n" +
                "..###.####..#.#.#..##.#.#....#......#.##.##..##.#.....##.###.#..###...###.#.##..#.#..###....####.#.#\n" +
                "#.#...#......####.#..##.####.#.#.#...##..###.##.#...#..#..###....#.#....#..##..#....##.....##.#...#.\n" +
                "....##.#.#.#.##..##...##..##..#....#....###...####.###...##.#...#..#....##.....#..#.#####.###.###.##\n" +
                "#...##..#.#..#....#..########.##....##..##.###..#.#..#..#.##.##.#..##..######....####..#####.#.###..\n" +
                ".####...######.#..#.##.#.#..####...####.##.#.#......#...##....##..#...###..#.####......###......#.##\n" +
                ".####.###..#..#####.##...###......#...###..#..##..#.#....##.##.#.##.###..#..#..###.#..#.#....####.##\n" +
                "#..#..##.##.##.###.#.##.##.#.#.#....#....#.####.#.##...#####...###.#####.#.#.#....####..###..###..##\n" +
                "#.##....#...########..##...#.#.##.......#.#..##...####...#.####.####..##...##.#....###.#.####...#.##\n" +
                "#.#...##..#.##.##..##....#.....##.##.....#...###...#..#...####.##.####..#...##..##.##.##.##..##...##\n" +
                ".#..###...#.#.....#######..##.###....##..#.##.#......###.##....#......###...#.##....#.....##......##\n" +
                "..##....#.###...###..####.##..#..##.##......##.#.....#...#..#..##...###..#.####...#...#..##.#..##..#\n" +
                "...#.#.#...#.#..#.##....##..#...#.##..#......#.#.....#####.##.#...#######.#.#..#.####..###.....###.#\n" +
                ".#....#.#.##..####.#####..#.#######..#.##.###...##.##....##..###..#.##.###.......#....#..######.####\n" +
                "#..#.##.##..#..#..##.####.#.#.#.#..#.##...#..######....#.##.#..##.##.######.###.###.###...#.....#.#.\n" +
                ".#.......#...#.####.##...#####..##..#.#....##..#.#.#.####.#.##....#..##.##..#.###.....#.##.##.#.##.#\n" +
                "#..##..##...#....#.##.#...#.#....#......####...##..#...##.##.#..#########..#..#.##.##..#.#.#######..\n" +
                "#.......#####..###..######.#..##.#.#####..##...###...#.####.##...###..#.#.#####....#...#.##...#.#..#\n" +
                ".##..#...#####.##.##......#...#.#.#.###.#.#.#...##.#..#....###.....#..#.#.###......#####.###.#..##.#\n" +
                ".....###.#.#.#..##...#...###..#...#.#.##..###.##.#####.##..#.#.#.#.#####....#.#.#####...##.#..#.#.#.\n" +
                "###...##.#..#.####..##.#..##.#.#.#...#.#..#..##..##..#.#.#.#.##...##..#..#.....#....#####.#.#.####.#\n" +
                "....##....#.#.....#...###.#...##..##.##..#..###..##.###..#####..#...#####.##.#..#.#.#.###...####.###\n" +
                "##.##.##.#...#..#...........##.##.###.#...###.####.#..#..#...#..#..####.#.###########..#.###.###.#.#\n" +
                "##.##..##.####..###...##...#....###.###.#..##..#..#.###.#..####.#..##.#.#...#..#.#.##.##...#...#....\n" +
                "..##...#.#.##....##...#.#.#......##.##.#.#.####.####....####.#.###.##.#.#..####..#..######..#..#.#..\n" +
                "####.#.##.......##.###....##.#..####.#.#######..#...###..##.##..#...#...####........#.#..##...#....#\n" +
                "#..#.#.....#..#.###..#.#...###..##...#.#..#.#.##..#...##.##.##.#.#.#..#.####.########....########..#\n" +
                "#...#..##.##..#.#.#.##.##.##.#..#..#.##....#....###.#.###.#.#..#....#...##..#.....####...##.#..#...#\n" +
                ".###...##...####....###.##.#..####...##.#.##.#..##..##....#....##.#...#..#..##..##..##.#...#...###..\n" +
                ".#..##.#..##..####..#.#.##..###.#...#....##.###...#.###....#.#.#........#..#.#.#..##..#####..#..#.#.\n" +
                ".#.##.....#..#...#.##.....#.##..#..#....#..#..#....#.##..##...#.##.##..##..#.#.#.##..####.##..#.#..#\n" +
                "...###.#.....#...#.##.#.###.#...##..#.###..#..#..#.#..#...###.#.##.##.##.#.##.#####.#..#.#..#.#...##\n" +
                "#.#.#.#.##.#.....##..#.###......##.#.##..#...#.########.##.###..#..#..##..##.#..##..###.#.###...#.#.\n" +
                "..##...##...#...###.#..##..#..#..#.#.##..##......##..##.....##.....####..#.##......#..####...###..##\n" +
                "##.......#..##....###...###......#.##.##....######..###.##...##.#...#...#.....#.###.#.#..#.##..#..#.\n" +
                "#.#..#..#.#####.##.##.###..#...###.....#..##..####...#.#.###....#..#.#.###.####..#.#........##.#....\n" +
                "..###.#...##.#.####.#.##.##.....##...#.##.#.###.#.#..##.#..##..#..##.##....#.#####.##..#######.....#\n" +
                "###.###..##.#..##...#####..##.####....#.##......##......#.#....##.####.#.#.#.###...#..####..#.######\n" +
                "#..###...#.#.......#..####.####...#....###.###...#.##..##..#..##.##.......####.##...#.#.#.##.#.#..#.\n" +
                "..#...#..###.##..#.#.#.##..#..#.#.......###..###..#####.#.#.#.#.#..#.#.#.#..###....#.####..###...#..\n" +
                "...######.###....#..####.####......#...#.###.#....#...####.##........##...##.#..##.###.#..#..##..###\n" +
                ".#..###.####.###.#.#..#..#..#.##.#.#.###.##..####.#####..##....##.#.##...###.####.#.#######.#..#..#.\n" +
                ".#..##.#..##..#...##...#..#..##.#.#....##.##...###.#.#...##..##..#.###.#.#.#.#...#....#.#..#.#.###.#\n" +
                ".###..#.#..####.#########...####....####.#.##...##.##..#.##.#........#.....###.###.######.##.....###\n" +
                "..##.##..##..#.####.#..#####.#....##.##.#####.....#.#......##...#####..####....###..#.#...#..####..#\n" +
                ".#..##..##.##.##.##.#.###.###.#..#..#...###.#.##..##...##...###...##.###..#.#.#####.#.#.##....#.##..\n" +
                "...#.#....##.#.....###.##...#..##....#...###....#..#.###...##.#...###.#....#...##..###.#.....##....#\n" +
                ".#######..#...##.#.###.##.#.###...##......#.###.#...#.###.#.#.#..#..#####..#########...##..##...#..#\n" +
                ".#..#.##...#.#..#.##..#.#.#.##.....####.#..#.###..##.#.#.#...#....#.#..##.######...#.#..##.##...#..#\n" +
                "#.#######.#####..#####.##.##.#.#.##.###..#....####.#..##.##.######..###...#.#..#.####.##.##....####.\n" +
                "...##..#...##..#..#.....#.##...#.....##.#####.###.########.######..#...###..#.##.#.#.##..#.#.##..##.\n" +
                "#..#..#.#....###.#...##..####.#.##..#.####.###..##.#...#.###.#..#.##..#######.#...#..#.#..##.#....##\n" +
                "..#.##.#.####..##.###.###..#.##.#.####..##....##.###.#..##.#.###.###.##.##.#####..#.#...########....\n" +
                ".#.#.###..###...#...#..##.##......#..#...#.#.#.######.#.#...##..##........#....###..##...#..##.##...\n" +
                "##..#....##.###...##.#.##.##.##..#....#.#.#..#..####.##..#...#...#..#..#####.###...#..###..#...#.#..\n" +
                "##.#.#.##.###.....######.#.....#...#.##....###.#.##.#.#.##..##.######.#####....#.#####...##.#..###.#\n" +
                "######.#...####..###..##..#..##...#.#....##.#...##...#.....#...##....#.##..###..###...###..#..######\n" +
                ".....##.........#####.#.##..#..#.#.#.#.##...#....#.....###.########...#..####..#...#...##..#.##.##.#\n" +
                "#..###...#.##.##.#.#..####.#.....##..###....##..#...#.#...##.##..###..####...#.####..##..#..##..#...\n" +
                "#.####.#..##.#..#.....#..#.#..###...######.#.........####....###..#.#.#.##.#..#...#..####.....##..#.\n" +
                "..##....#.###.......##.#...#.####..##....##.#..#....#######...####.##..#####.#.#.#.#.##..##..#.#.#..\n" +
                "#.#.#.###..#..#.#..#.#.###....#...#####.###...........#.#....#####...#..####....#...###.#..#..####..\n" +
                ".......#.####.##...#..#.##..###..#..#.#.#.#.###....#....#.#.#..#.#..##.#####.#.....#.##.#.###.###.##\n" +
                "..###...#..#...####.#..##..##.#.#..#...#.#..#....###.#..####..######...####.#.##..#.#..###...##.####\n" +
                "..#.###..#.#...##...#.#....#..#...#.#..##.######.######.#.##.....#..##.#..###..#..#.##.###...#..#.##\n" +
                "####..##.####.....#...#.#.###..#...####.###.#.#.#.......##...#....#..#....#.#......###...#####.#.##.\n" +
                "#..##..#..#.####...#####.#.###.##.#.##.....#.#..#.##........######.#.#.###....##.##..##..########.##\n" +
                "#.#....###.##....#######.#...#.#.#.#..##.#.##...#.###...#.#.#..#.#..####.#.#..#..#.##.####....#..##.\n" +
                "####.##....#.......###..#..##.#.#.##..#...#...##.###....##..###.#.#...#..#.....##.###.##...###....##\n" +
                "..##.#..#....######..#.##.#.#...##..####.#####...##.#..###.##...#..####..###.##..##.##.#####.#..#.#.\n" +
                ".#.##..#..##.#.###.###....#.#..#....#...###.##.#.#.####.....#....#...#.....#....#.#.###.#..#.##..###\n" +
                "..###.#.#.##...##.##.##.#...#####.#..##.#....##..####...###..#....#.##...#........#####.#.###.#..#..\n" +
                "....#..##..##....#.#....#.#..##...##.#...##.###.#.#..###..##.##.##..#.#.#..#.#.##.......#.##.###..#.\n" +
                ".#..##.##.####.##....##.##.....###..##.#.##...#..###....###.###....#.#....#....#.##.#.##.#.##.....##\n" +
                "#.#..#.##.###.#.######.....###.#..#...#.#.....##.###.#...#.#..###.#.....##.###.#.###.####..#####.#..\n" +
                "#.#.##......#.##.#.#..##....#..###.#.###...##...###.#..#.##...#..#.##..##.#...######.##.....#####.##\n" +
                "#.#..#####....###.###...#.......#....###.##...#..#.##..#...#####..#..#.##......###...#...###..#.#..#\n" +
                "#.##..##.##.#..#.##.##..#.###.##.........###.#.#..#.#.....#.#...#.#.##.#.##.#...#...####.#.......##.\n" +
                ".#...####.##..#..##....####..######...#.#..##.##.....#####.#...#..#.####.#######...#.#####..#.###...\n" +
                ".#..######.#.##..##...##.....###.#..##..#...####..###...###.###..#..######.#....########..#####...#.\n" +
                "#..##.......#####...###..#.#.##.#..###.#...##.#..#.##.###...###...##.#..##..########..#.#..##..#.###\n" +
                ".#.#..#...#.#..#..##...#.#.##...###..#..#....###.#....#.##....###.###..##..#.#.####..####.#######.##\n" +
                "...##..##.##.###.##.###...##.#.#.....##.####..#..##.#..#.####...##..#..#.##...##...###.##.#.......##\n" +
                ".#.....#.##..#.#.....#.##.##..###..#....###...#.#....##########.##.###.#...#.####..####.#..#.#..###.\n" +
                ".##.#.#.##..#..###.###.##.#########.#.#.#.#.##.###..##..#.##.####......#####...#..####.#.##..#####.#\n" +
                "..#....###...##....#.###..##..#..####.##..####.#..####.###.#....####.....#.###..##...##..####...##.#\n" +
                ".###.....###.##.##..###.###.....##..#.######.#.#..##..#.##.#..#.#.#....#...#.#.#...#...##....#..##.#\n" +
                "..##....#..#####....#..####.#.#...##.#....##..##.###.###....###......#...#.#####.......#...#.....###\n" +
                "###.#..#.#.##..#..#...#.#....###.##.#.###.#...#.##.#..#.#.......#.#.#.###.####.###....#..##..#####..\n" +
                ".#..#######.#..###.#.##.#####.#####...##..#.####.#.#.##..###...#..##.##..#.#.###..#....#..#...###.#.\n" +
                "..#####..#.##.....###..##.#...#.#.#..#######.#..#...#.##.##.#.#....####...###..##...#....####.#..#.#\n" +
                ".####..#.#.##.###.#.##.....#..##.#.....###.....#..##...#....###.###..#......###.#.#.#.##.#.##..#...#\n" +
                "##.#..##.#..##...#.#....##..######..#.....#..#...#####....##......####.##..#...##..#.##.#.#######..#\n" +
                "##..####.#...##...#.#####.#.#..#....#.#..##.####.#..######.#..#..#.......#####..#..#..###.##...##.##\n" +
                "#.####......#.###...#..####.#..##.##..#.#...##.###.#...#####..####.#..#.#.....#.##...###...#.#....##\n" +
                "###.#.#.##.######......#.#.#.#.#........#..#..###.#.#.#..#.........#..#....#.#..#..#..###.##......##\n" +
                "##.#########...#...###..#.###.....#.#.##.........###....#.####.#...###.#..##..#.###..#..##......#.##";

    }

    @Test
    public void getLightsOnAfterXSteps() {
        assertEquals(4, GifYard.getLightsOnAfterXSteps(test1, 4));
        assertEquals(768, GifYard.getLightsOnAfterXSteps(input, 100));
    }

    @Test
    public void getLightsOnAfterXStepsConway() {
        assertEquals(17, GifYard.getLightsOnAfterXStepsConway(test1, 5));
        assertEquals(781, GifYard.getLightsOnAfterXStepsConway(input, 100));
    }
}
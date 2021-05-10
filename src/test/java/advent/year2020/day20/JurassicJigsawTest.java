/* Licensed under Apache-2.0 */
package advent.year2020.day20;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class JurassicJigsawTest {
    public static String test1;
    public static String test1Sea;
    public static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "Tile 2311:\n" +
                "..##.#..#.\n" +
                "##..#.....\n" +
                "#...##..#.\n" +
                "####.#...#\n" +
                "##.##.###.\n" +
                "##...#.###\n" +
                ".#.#.#..##\n" +
                "..#....#..\n" +
                "###...#.#.\n" +
                "..###..###\n" +
                "\n" +
                "Tile 1951:\n" +
                "#.##...##.\n" +
                "#.####...#\n" +
                ".....#..##\n" +
                "#...######\n" +
                ".##.#....#\n" +
                ".###.#####\n" +
                "###.##.##.\n" +
                ".###....#.\n" +
                "..#.#..#.#\n" +
                "#...##.#..\n" +
                "\n" +
                "Tile 1171:\n" +
                "####...##.\n" +
                "#..##.#..#\n" +
                "##.#..#.#.\n" +
                ".###.####.\n" +
                "..###.####\n" +
                ".##....##.\n" +
                ".#...####.\n" +
                "#.##.####.\n" +
                "####..#...\n" +
                ".....##...\n" +
                "\n" +
                "Tile 1427:\n" +
                "###.##.#..\n" +
                ".#..#.##..\n" +
                ".#.##.#..#\n" +
                "#.#.#.##.#\n" +
                "....#...##\n" +
                "...##..##.\n" +
                "...#.#####\n" +
                ".#.####.#.\n" +
                "..#..###.#\n" +
                "..##.#..#.\n" +
                "\n" +
                "Tile 1489:\n" +
                "##.#.#....\n" +
                "..##...#..\n" +
                ".##..##...\n" +
                "..#...#...\n" +
                "#####...#.\n" +
                "#..#.#.#.#\n" +
                "...#.#.#..\n" +
                "##.#...##.\n" +
                "..##.##.##\n" +
                "###.##.#..\n" +
                "\n" +
                "Tile 2473:\n" +
                "#....####.\n" +
                "#..#.##...\n" +
                "#.##..#...\n" +
                "######.#.#\n" +
                ".#...#.#.#\n" +
                ".#########\n" +
                ".###.#..#.\n" +
                "########.#\n" +
                "##...##.#.\n" +
                "..###.#.#.\n" +
                "\n" +
                "Tile 2971:\n" +
                "..#.#....#\n" +
                "#...###...\n" +
                "#.#.###...\n" +
                "##.##..#..\n" +
                ".#####..##\n" +
                ".#..####.#\n" +
                "#..#.#..#.\n" +
                "..####.###\n" +
                "..#.#.###.\n" +
                "...#.#.#.#\n" +
                "\n" +
                "Tile 2729:\n" +
                "...#.#.#.#\n" +
                "####.#....\n" +
                "..#.#.....\n" +
                "....#..#.#\n" +
                ".##..##.#.\n" +
                ".#.####...\n" +
                "####.#.#..\n" +
                "##.####...\n" +
                "##..#.##..\n" +
                "#.##...##.\n" +
                "\n" +
                "Tile 3079:\n" +
                "#.#.#####.\n" +
                ".#..######\n" +
                "..#.......\n" +
                "######....\n" +
                "####.#..#.\n" +
                ".#...#.##.\n" +
                "#.#####.##\n" +
                "..#.###...\n" +
                "..#.......\n" +
                "..#.###...";

        test1Sea = ".#.#..#.##...#.##..#####\n" +
                "###....#.#....#..#......\n" +
                "##.##.###.#.#..######...\n" +
                "###.#####...#.#####.#..#\n" +
                "##.#....#.##.####...#.##\n" +
                "...########.#....#####.#\n" +
                "....#..#...##..#.#.###..\n" +
                ".####...#..#.....#......\n" +
                "#..#.##..#..###.#.##....\n" +
                "#.####..#.####.#.#.###..\n" +
                "###.#.#...#.######.#..##\n" +
                "#.####....##..########.#\n" +
                "##..##.#...#...#.#.#.#..\n" +
                "...#..#..#.#.##..###.###\n" +
                ".#.#....#.##.#...###.##.\n" +
                "###.#...#..#.##.######..\n" +
                ".#.#.###.##.##.#..#.##..\n" +
                ".####.###.#...###.#..#.#\n" +
                "..#.#..#..#.#.#.####.###\n" +
                "#..####...#.#.#.###.###.\n" +
                "#####..#####...###....##\n" +
                "#.##..#..#...#..####...#\n" +
                ".#.###..##..##..####.##.\n" +
                "...###...##...#...#..###";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day20/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void solveAndProductOfCorners_20899048083289() {
        assertEquals(20899048083289l, JurassicJigsaw.solveAndProductOfCorners(test1));
    }

    @Test
    public void solveAndProductOfCorners_15405893262491() {
        assertEquals(15405893262491l, JurassicJigsaw.solveAndProductOfCorners(input));
    }

    @Test
    public void calculateRoughWaters_273() {
        assertEquals(273, JurassicJigsaw.calculateRoughWaters(test1));
    }

    @Test
    public void calculateRoughtWaters_1() {
        assertEquals(2133, JurassicJigsaw.calculateRoughWaters(input));
    }

    @Test
    public void calculateRoughWatersFromSea_273() {
        assertEquals(273, JurassicJigsaw.calculateRoughWatersFromSea(test1Sea));
    }
}
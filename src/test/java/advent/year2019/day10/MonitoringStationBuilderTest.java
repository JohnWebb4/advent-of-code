/* Licensed under Apache-2.0 */
package advent.year2019.day10;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonitoringStationBuilderTest {
  @Test
  public void getStationWithBestCoverage34() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        ".#..#",
        ".....",
        "#####",
        "....#",
        "...##"
    }));

    assertArrayEquals(new int[] { 3, 4 }, builder.getStationWithBestCoverage());
  }

  @Test
  public void getStationWithBestCoverage58() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        "......#.#.",
        "#..#.#....",
        "..#######.",
        ".#.#.###..",
        ".#..#.....",
        "..#....#.#",
        "#..#....#.",
        ".##.#..###",
        "##...#..#.",
        ".#....####"
    }));

    assertArrayEquals(new int[] { 5, 8 }, builder.getStationWithBestCoverage());
  }

  @Test
  public void getStationWithBestCoverage12() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        "#.#...#.#.",
        ".###....#.",
        ".#....#...",
        "##.#.#.#.#",
        "....#.#.#.",
        ".##..###.#",
        "..#...##..",
        "..##....##",
        "......#...",
        ".####.###.",
    }));

    assertArrayEquals(new int[] { 1, 2 }, builder.getStationWithBestCoverage());
  }

  @Test
  public void getStationWithBestCoverage63() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        ".#..#..###",
        "####.###.#",
        "....###.#.",
        "..###.##.#",
        "##.##.#.#.",
        "....###..#",
        "..#.#..#.#",
        "#..#.#.###",
        ".##...##.#",
        ".....#.#..",
    }));

    assertArrayEquals(new int[] { 6, 3 }, builder.getStationWithBestCoverage());
  }

  @Test
  public void getStationWithBestCoverage1113() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        ".#..##.###...#######",
        "##.############..##.",
        ".#.######.########.#",
        ".###.#######.####.#.",
        "#####.##.#.##.###.##",
        "..#####..#.#########",
        "####################",
        "#.####....###.#.#.##",
        "##.#################",
        "#####.##.###..####..",
        "..######..##.#######",
        "####.##.####...##..#",
        ".#####..#.######.###",
        "##...#.##########...",
        "#.##########.#######",
        ".####.#.###.###.#.##",
        "....##.##.###..#####",
        ".#.#.###########.###",
        "#.#.#.#####.####.###",
        "###.##.####.##.#..##",
    }));

    assertArrayEquals(new int[] { 11, 13 }, builder.getStationWithBestCoverage());
  }

  @Test
  public void getStationWithBestCoverageAnswer() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        ".##.#.#....#.#.#..##..#.#.",
        "#.##.#..#.####.##....##.#.",
        "###.##.##.#.#...#..###....",
        "####.##..###.#.#...####..#",
        "..#####..#.#.#..#######..#",
        ".###..##..###.####.#######",
        ".##..##.###..##.##.....###",
        "#..#..###..##.#...#..####.",
        "....#.#...##.##....#.#..##",
        "..#.#.###.####..##.###.#.#",
        ".#..##.#####.##.####..#.#.",
        "#..##.#.#.###.#..##.##....",
        "#.#.##.#.##.##......###.#.",
        "#####...###.####..#.##....",
        ".#####.#.#..#.##.#.#...###",
        ".#..#.##.#.#.##.#....###.#",
        ".......###.#....##.....###",
        "#..#####.#..#..##..##.#.##",
        "##.#.###..######.###..#..#",
        "#.#....####.##.###....####",
        "..#.#.#.########.....#.#.#",
        ".##.#.#..#...###.####..##.",
        "##...###....#.##.##..#....",
        "..##.##.##.#######..#...#.",
        ".###..#.#..#...###..###.#.",
        "#..#..#######..#.#..#..#.#",
    }));

    assertArrayEquals(new int[] { 19, 14 }, builder.getStationWithBestCoverage());
  }

  public void get9thVaporizedAsteroid() {
    MonitoringStationBuilder builder = new MonitoringStationBuilder(String.join("\n", new String[] {
        ".#....#####...#..",
        "##...##.#####..##",
        "##...#...#.#####.",
        "..#.........###..",
        "..#.#.....#....##",
    }));

    assertArrayEquals(new int[] { 17, 1 }, builder.getNthVaporizedAsteroid(new int[] { 8, 3 }, 9));
  }
}
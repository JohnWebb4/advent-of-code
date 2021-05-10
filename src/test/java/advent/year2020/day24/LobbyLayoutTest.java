/* Licensed under Apache-2.0 */
package advent.year2020.day24;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class LobbyLayoutTest {
    static String test1;
    static String input;

    @BeforeClass
    public static void initialize() {
        test1 = "sesenwnenenewseeswwswswwnenewsewsw\n" +
                "neeenesenwnwwswnenewnwwsewnenwseswesw\n" +
                "seswneswswsenwwnwse\n" +
                "nwnwneseeswswnenewneswwnewseswneseene\n" +
                "swweswneswnenwsewnwneneseenw\n" +
                "eesenwseswswnenwswnwnwsewwnwsene\n" +
                "sewnenenenesenwsewnenwwwse\n" +
                "wenwwweseeeweswwwnwwe\n" +
                "wsweesenenewnwwnwsenewsenwwsesesenwne\n" +
                "neeswseenwwswnwswswnw\n" +
                "nenwswwsewswnenenewsenwsenwnesesenew\n" +
                "enewnwewneswsewnwswenweswnenwsenwsw\n" +
                "sweneswneswneneenwnewenewwneswswnese\n" +
                "swwesenesewenwneswnwwneseswwne\n" +
                "enesenwswwswneneswsenwnewswseenwsese\n" +
                "wnwnesenesenenwwnenwsewesewsesesew\n" +
                "nenewswnwewswnenesenwnesewesw\n" +
                "eneswnwswnwsenenwnwnwwseeswneewsenese\n" +
                "neswnwewnwnwseenwseesewsenwsweewe\n" +
                "wseweeenwnesenwwwswnew";

        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2020/day24/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFlippedTileCount_10() {
        assertEquals(10, LobbyLayout.getFlippedTileCount(test1));
    }

    @Test
    public void getFlippedTileCount_1() {
        assertEquals(289, LobbyLayout.getFlippedTileCount(input));
    }

    @Test
    public void getFlippedTileCountAfterXDays_2208() {
        assertEquals(2208, LobbyLayout.getFlippedTileCountAfterXDays(100, test1));
    }

    @Test
    public void getFlippedTileCountAfterXDays_1() {
        assertEquals(3551, LobbyLayout.getFlippedTileCountAfterXDays(100, input));
    }
}
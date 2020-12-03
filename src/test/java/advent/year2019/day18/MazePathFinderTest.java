/* Licensed under Apache-2.0 */
package advent.year2019.day18;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class MazePathFinderTest {
    static String input;

    @BeforeClass
    public static void beforeClass() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2019/day18/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFindStepsToGrabAllKeys8() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(8, finder.findStepsToGrabAllKeys("#########\n" + "#b.A.@.a#\n" + "#########"));
    }

    @Test
    public void findStepsToGrabAllKeys86() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(86, finder.findStepsToGrabAllKeys("########################\n" +
                "#f.D.E.e.C.b.A.@.a.B.c.#\n" +
                "######################.#\n" +
                "#d.....................#\n" +
                "########################"));
    }

    @Test
    public void findStepsToGrabAllKeys132() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(132, finder.findStepsToGrabAllKeys("########################\n" +
                "#...............b.C.D.f#\n" +
                "#.######################\n" +
                "#.....@.a.B.c.d.A.e.F.g#\n" +
                "########################"));
    }

    // Slow 20s
    public void findStepsToGrabAllKeys136() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(136, finder.findStepsToGrabAllKeys("#################\n" +
                "#i.G..c...e..H.p#\n" +
                "########.########\n" +
                "#j.A..b...f..D.o#\n" +
                "########@########\n" +
                "#k.E..a...g..B.n#\n" +
                "########.########\n" +
                "#l.F..d...h..C.m#\n" +
                "#################"));
    }

    @Test
    public void findStepsToGraphAllKeys81() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(81, finder.findStepsToGrabAllKeys("########################\n" +
                "#@..............ac.GI.b#\n" +
                "###d#e#f################\n" +
                "###A#B#C################\n" +
                "###g#h#i################\n" +
                "########################"));

    }

    // Extremely slow test > 100s
    public void findStepsToGraphAllKeys1() {
        MazePathFinder finder = new MazePathFinder();

        assertEquals(81, finder.findStepsToGrabAllKeys(input));

    }
}

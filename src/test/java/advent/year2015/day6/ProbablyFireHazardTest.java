package advent.year2015.day6;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ProbablyFireHazardTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day6/input.txt")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getLightsOnCount_turn_on() {
        assertEquals(1000000, ProbablyFireHazard.getLightsOnCount("turn on 0,0 through 999,999"));
        assertEquals(1000, ProbablyFireHazard.getLightsOnCount("turn on 0,0 through 999,0"));
    }

    @Test
    public void getLightsOnCount_toggle() {
        assertEquals(999000, ProbablyFireHazard.getLightsOnCount("turn on 0,0 through 999,999\ntoggle 0,0 through 999,0"));
        assertEquals(1000, ProbablyFireHazard.getLightsOnCount("toggle 0,0 through 999,0"));
    }

    @Test
    public void getLightOnCount_turn_off() {
        assertEquals(999996, ProbablyFireHazard.getLightsOnCount("turn on 0,0 through 999,999\nturn off 499,499 through 500,500"));
        assertEquals(0, ProbablyFireHazard.getLightsOnCount("turn off 499,499 through 500,500"));
    }

//    @Test
//    public void getLightsOnCount_input() {
//        assertEquals(377891, ProbablyFireHazard.getLightsOnCount(input));
//    }

    @Test
    public void getBrightnessCount_turn_on() {
        assertEquals(1000000, ProbablyFireHazard.getBrightnessCount("turn on 0,0 through 999,999"));
        assertEquals(1001000, ProbablyFireHazard.getBrightnessCount("turn on 0,0 through 999,999\nturn on 0,0 through 999,0"));
    }

    @Test
    public void getBrightnessCount_toggle() {
        assertEquals(1002000, ProbablyFireHazard.getBrightnessCount("turn on 0,0 through 999,999\ntoggle 0,0 through 999,0"));
        assertEquals(2000, ProbablyFireHazard.getBrightnessCount("toggle 0,0 through 999,0"));
    }

    @Test
    public void getBrightnessCount_turn_off() {
        assertEquals(999996, ProbablyFireHazard.getBrightnessCount("turn on 0,0 through 999,999\nturn off 499,499 through 500,500"));
        assertEquals(0, ProbablyFireHazard.getBrightnessCount("turn off 499,499 through 500,500"));
    }

//    @Test
//    public void getBrightnessCount_input() {
//        assertEquals(0, ProbablyFireHazard.getBrightnessCount(input));
//    }
}
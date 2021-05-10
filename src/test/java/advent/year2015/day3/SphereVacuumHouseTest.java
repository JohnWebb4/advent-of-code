package advent.year2015.day3;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SphereVacuumHouseTest {
    public static String input;

    @BeforeClass
    public static void initialize() {
        try {
            input = new String(Files.readAllBytes(Paths.get("./src/test/java/advent/year2015/day3/input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountHousesWithPresents_2() {
        assertEquals(2, SphereVacuumHouse.getCountHousesWithPresents(">"));
    }

    @Test
    public void getCountHousesWithPresents_4() {
        assertEquals(4, SphereVacuumHouse.getCountHousesWithPresents("^>v<"));
    }

    @Test
    public void getCountHousesWithPresents_2_many() {
        assertEquals(2, SphereVacuumHouse.getCountHousesWithPresents("^v^v^v^v^v"));
    }

    @Test
    public void getCountHousesWithPresents_2572() {
        assertEquals(2572, SphereVacuumHouse.getCountHousesWithPresents(input));
    }

    @Test
    public void getCountHousesWithPresentsWithRobot_3() {
        assertEquals(3, SphereVacuumHouse.getCountHousesWithPresentsWithRobot("^v"));
    }

    @Test
    public void getCountHousesWithPresentsWithRobot_3_2() {
        assertEquals(3, SphereVacuumHouse.getCountHousesWithPresentsWithRobot("^>v<"));
    }

    @Test
    public void getCountHousesWithPresentsWithRobot_11() {
        assertEquals(11, SphereVacuumHouse.getCountHousesWithPresentsWithRobot("^v^v^v^v^v"));
    }

    @Test
    public void getCountHousesWithPresentsWithRobot_2631() {
        assertEquals(2631, SphereVacuumHouse.getCountHousesWithPresentsWithRobot(input));
    }
}
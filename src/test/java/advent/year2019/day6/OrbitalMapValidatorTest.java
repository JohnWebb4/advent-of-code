/* Licensed under Apache-2.0 */
package advent.year2019.day6;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class OrbitalMapValidatorTest {
  static String[] orbits = new String[] {};

  @BeforeClass
  public static void setup() {
    // Load answer
    try {
      File file = new File("./src/test/java/advent/year2019/day6/Orbits.txt");

      BufferedReader br = new BufferedReader(new FileReader(file));

      String orbit;
      ArrayList<String> orbitList = new ArrayList<String>();
      while ((orbit = br.readLine()) != null) {
        orbitList.add(orbit);
      }

      orbits = orbitList.toArray(new String[0]);

      br.close();
    } catch(Exception e) {
      System.out.println(String.format("Error reading 2019 day 3 int codes %s"));
    }
  }
  @Test
  public void addPlanets() {


    OrbitalMapValidator validator = new OrbitalMapValidator();

    validator.addPlanets(new String[] {
        "COM)B",
        "B)C",
        "C)D",
        "D)E",
        "E)F",
        "B)G",
        "G)H",
        "D)I",
        "E)J",
        "J)K",
        "K)L",
    });

    assertEquals("COM", validator.coms.get(0).name);
    assertEquals("B", validator.coms.get(0).orbits.get(0).name);
    assertEquals("C", validator.coms.get(0).orbits.get(0).orbits.get(0).name);
    assertEquals("G", validator.coms.get(0).orbits.get(0).orbits.get(1).name);
    assertEquals("H", validator.coms.get(0).orbits.get(0).orbits.get(1).orbits.get(0).name);
    assertEquals("D", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).name);
    assertEquals("E", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).name);
    assertEquals("I", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(1).name);
    assertEquals("F", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).name);
    assertEquals("J", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(1).name);
    assertEquals("K", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(1).orbits.get(0).name);
    assertEquals("L", validator.coms.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(0).orbits.get(1).orbits.get(0).orbits.get(0).name);
  }

  @Test
  public void getIndirectOrbits() {
    OrbitalMapValidator validator = new OrbitalMapValidator();

    validator.addPlanets(new String[] {
        "COM)B",
        "B)C",
        "C)D",
        "D)E",
        "E)F",
        "B)G",
        "G)H",
        "D)I",
        "E)J",
        "J)K",
        "K)L",
    });

    assertEquals(42, validator.getIndirectOrbits(), 0.1);
  }

  @Test
  public void getIndirectOrbitsAnswer() {
    OrbitalMapValidator validator = new OrbitalMapValidator();

    validator.addPlanets(orbits);

    double result = validator.getIndirectOrbits();

    assertEquals(1794.0d, result, 0.5);
  }

}
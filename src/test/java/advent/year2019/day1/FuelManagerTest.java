/* Licensed under Apache-2.0 */
package advent.year2019.day1;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class FuelManagerTest {
  private static int[] masses;

  @BeforeClass
  public static void setUp() {
    try {
      File file = new File("./src/test/java/advent/year2019/day1/Masses.txt");

      BufferedReader br = new BufferedReader(new FileReader(file));

      String massString;
      ArrayList<Integer> massesList = new ArrayList<Integer>();
      while ((massString = br.readLine()) != null) {
        int mass = Integer.parseInt(massString);
        massesList.add(mass);
      }

      masses = massesList.stream().mapToInt(i->i).toArray();

      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getFuel12() {
    assertEquals(2, FuelManager.getFuel(12));
  }

  @Test
  public void getFuel14() {
    assertEquals(2, FuelManager.getFuel(14));
  }

  @Test
  public void getFuel1969() {
    assertEquals(654, FuelManager.getFuel(1969));
  }

  @Test
  public void getFuel100756() {
    assertEquals(33583, FuelManager.getFuel(100756));
  }

  @Test
  public void getFuelAnswer() {
    assertEquals(3325347, FuelManager.getFuel(masses));
  }

  @Test
  public void getImprovedFuel14() {
    assertEquals(2, FuelManager.getImprovedFuel(14));
  }

  @Test
  public void getImprovedFuel1969() {
    assertEquals(966, FuelManager.getImprovedFuel(1969));
  }

  @Test
  public void getImprovedFuel100756() {
    assertEquals(50346, FuelManager.getImprovedFuel(100756));
  }

  @Test
  public void getImprovedAnswer() {
    assertEquals(4985145, FuelManager.getImprovedFuel(masses));
  }
}
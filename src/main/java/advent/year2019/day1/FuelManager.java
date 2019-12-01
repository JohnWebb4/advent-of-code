/* Licensed under Apache-2.0 */
package advent.year2019.day1;

public class FuelManager {
  public static int getFuel(int[] masses) {
    int totalFuel = 0;

    for (int mass : masses)
    {
      totalFuel += FuelManager.getFuel(mass);
    }

    return totalFuel;
  }

  public static int getFuel(int mass) {
    return (int)(Math.floor(mass / 3.0F) - 2);
  }

  public static int getImprovedFuel(int[] masses) {
    int totalFuel = 0;

    for (int mass : masses)
    {
      totalFuel += FuelManager.getImprovedFuel(mass);
    }

    return totalFuel;
  }

  public static int getImprovedFuel(int mass) {
    int fuel = getFuel(mass);

    if (fuel >= 6) {
      fuel += getImprovedFuel(fuel);
    }

    return fuel;
  }
}

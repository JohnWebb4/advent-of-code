/* Licensed under Apache-2.0 */
package advent.year2019.day12;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlanetRadarTest {
  @Test
  public void getEnergy1940() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { -1, 0, 2 },
        new int[] { 2, -10, -7 },
        new int[] { 4, -8, 8 },
        new int[] { 3, 5, -1 }
    });

    radar.step(10);

    assertArrayEquals(
        new int[][] {
          new int[] { 2, 1, -3, -3, -2, 1 },
          new int[] { 1, -8, 0, -1, 1, 3 },
          new int[] { 3, -6, 1, 3, 2, -3 },
          new int[] { 2, 0, 4, 1, -1, -1 },
        },
        radar.planets.stream().map(planet -> {
          int[] concat = new int[6];
          System.arraycopy(planet.position, 0, concat, 0, 3);
          System.arraycopy(planet.velocity, 0, concat, 3, 3);

          return concat;
        }).toArray());

    assertEquals(179, radar.getEnergy());
  }

  public void getEnergy8100() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { -8, -10, 0},
        new int[] { 5, 5, 10},
        new int[] { 2, -7, 3 },
        new int[] { 9, -8, -3 }
    });

    radar.step(100);

    assertEquals(1940, radar.getEnergy());
  }

  @Test
  public void getEnergy3158() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { 3, 15, 8 },
        new int[] { 5, -1, -2 },
        new int[] { -10, 8, 2 },
        new int[] { 8, 4, -5 }
    });

    radar.step(1000);

    assertEquals(7179, radar.getEnergy());
  }

  @Test
  public void getStepsToRepeat1940() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { -1, 0, 2 },
        new int[] { 2, -10, -7 },
        new int[] { 4, -8, 8 },
        new int[] { 3, 5, -1 }
    });

    assertEquals(2772, radar.getStepsToRepeat());
  }

  @Test
  public void getStepsToRepeat8100() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { -8, -10, 0},
        new int[] { 5, 5, 10},
        new int[] { 2, -7, 3 },
        new int[] { 9, -8, -3 }
    });

    assertEquals(4686774924L, radar.getStepsToRepeat());
  }

  @Test
  public void getStepsToRepeat3158() {
    PlanetRadar radar = new PlanetRadar(new int[][] {
        new int[] { 3, 15, 8 },
        new int[] { 5, -1, -2 },
        new int[] { -10, 8, 2 },
        new int[] { 8, 4, -5 }
    });

    assertEquals(428576638953552L, radar.getStepsToRepeat());
  }
}
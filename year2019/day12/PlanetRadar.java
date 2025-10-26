/* Licensed under Apache-2.0 */
package advent.year2019.day12;

import java.util.*;

public class PlanetRadar {
  public List<Planet> planets;

  public static long getLCM(long [] numbers) {
    Map<Long, Long> denominators = new HashMap<>();

    for (long number : numbers) {
      long sum = number;
      for (long i = 2; i <= number; i++) {
        if (sum % i == 0) {
          long count = 0;

          while (sum % i == 0) {
            count++;
            sum /= i;
          }

          long maxCount = denominators.getOrDefault(i, 0L);

          if (count > maxCount) {
            denominators.put(i, count);
          }
        }
      }
    }

    long gcd = 1;
    for (long key : denominators.keySet()) {
      gcd *= Math.pow(key, denominators.get(key));
    }

    return gcd;
  }

  public PlanetRadar(int[][] planets) {
    this.planets = Arrays.asList(Arrays.stream(planets).map(pos -> new Planet(pos)).toArray(Planet[]::new));
  }

  public void step(int steps) {
    for (int i = 0; i < steps; i++) {
      this.step();
    }
  }

  public void step() {
    for (int i = 0; i < this.planets.size(); i++) {
      Planet planet1 = this.planets.get(i);

      for (int j = i + 1; j < this.planets.size(); j++) {
        // Update gravity
        Planet planet2 = this.planets.get(j);

        for (int axis = 0; axis < 3; axis++) {
          int normDiff = Math.min(1, Math.max(planet1.position[axis] - planet2.position[axis], -1));

          planet1.velocity[axis] -= normDiff;
          planet2.velocity[axis] += normDiff;
        }

        this.planets.set(j, planet2);
      }

      for (int axis = 0; axis < 3; axis++) {
        // Update position
        planet1.position[axis] += planet1.velocity[axis];
      }

      this.planets.set(i, planet1);
    }
  }

  public void stepAxis(int axis) {
    for (int i = 0; i < this.planets.size(); i++) {
      Planet planet1 = this.planets.get(i);

      for (int j = i + 1; j < this.planets.size(); j++) {
        // Update gravity
        Planet planet2 = this.planets.get(j);

        int normDiff = Math.min(1, Math.max(planet1.position[axis] - planet2.position[axis], -1));

        planet1.velocity[axis] -= normDiff;
        planet2.velocity[axis] += normDiff;

        this.planets.set(j, planet2);
      }

      // Update position
      planet1.position[axis] += planet1.velocity[axis];

      this.planets.set(i, planet1);
    }
  }


  public int getEnergy() {
    int energy = 0;
    for (Planet planet : this.planets) {
      int potential = Arrays.stream(planet.position).reduce(0, (i, j) -> Math.abs(i) + Math.abs(j));
      int kinetic = Arrays.stream(planet.velocity).reduce(0, (i, j) -> Math.abs(i) + Math.abs(j));

      energy += potential * kinetic;
    }

    return energy;
  }

  public long getStepsToRepeat() {
    float potentialSum = 0;
    long[] periods = new long[3];

    for (int axis = 0; axis < 3; axis++) {
      // Get period of each axis
      final int finalAxis = axis;
      Integer[] initialPositions = this.planets.stream().map(planet -> planet.position[finalAxis]).toArray(Integer[]::new);
      long stepsToRepeat = 0;
      boolean isRepeating = false;

      do {
        this.stepAxis(axis);
        stepsToRepeat++;

        if (this.getEnergy() == 0) {
          boolean isPlanetRepeating = true;

          for (int i = 0; i < this.planets.size(); i++) {
            if (this.planets.get(i).position[axis] != initialPositions[i]) {
              isPlanetRepeating = false;
            }
          }

          if (isPlanetRepeating) {
            isRepeating = true;
          }
        }
      } while (this.getEnergy() != 0);

      periods[axis] = stepsToRepeat;
    }

    return 2 * PlanetRadar.getLCM(periods);
  }
}

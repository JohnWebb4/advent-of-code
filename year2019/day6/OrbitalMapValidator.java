/* Licensed under Apache-2.0 */
package advent.year2019.day6;

import java.util.ArrayList;

public class OrbitalMapValidator {
  ArrayList<Planet> coms = new ArrayList<>();

  public void addPlanets(String[] orbits) {
    for (String orbit : orbits) {
      String[] orbitPlanets = orbit.split("\\)");
      String target = orbitPlanets[0];
      String child = orbitPlanets[1];

      addPlanet(target, child);
    }
  }

  public boolean addPlanet(String target, String child) {
    for (Planet com : coms) {
      if (addPlanet(target, child, com)) {
        return true;
      }
    }

    // Else add COM
    coms.add(new Planet(target));
    coms.get(0).orbits.add(new Planet(child));
    return true;
  }

  public boolean addPlanet(String target, String child, Planet currentPlanet) {
    if (currentPlanet.name.equals(target)) {
      currentPlanet.orbits.add(new Planet(child));
      return true;
    }

    for (Planet childPlanet : currentPlanet.orbits) {
        if (addPlanet(target, child, childPlanet)) {
          return true;
        }
    }

    return false;
  }

  public double getIndirectOrbits() {
    double count = 0;

    for (Planet com : coms) {
      count += getIndirectOrbits(com, 0);
    }

    return count;
  }

  public double getIndirectOrbits(Planet currentPlanet, int depth) {
    double count = depth;

    for (Planet child : currentPlanet.orbits) {
      double result = getIndirectOrbits(child, depth + 1);

      count +=  result;
    }

    return count;
  }
}

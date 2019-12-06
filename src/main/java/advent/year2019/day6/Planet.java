/* Licensed under Apache-2.0 */
package advent.year2019.day6;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Planet {
  public final String name;
  public ArrayList<Planet> orbits = new ArrayList<>();
}

/* Licensed under Apache-2.0 */
package advent.year2019.day12;

public class Planet implements Cloneable {
  int[] position;
  int[] velocity = new int[] { 0, 0, 0 };

  public Planet(int[] position) {
    this.position = position;
  }

  public Object clone() throws CloneNotSupportedException {
    Planet planet = (Planet)super.clone();

    planet.position = new int[this.position.length];
    planet.velocity = new int[this.position.length];
    System.arraycopy(this.position, 0, planet.position, 0, this.position.length);
    System.arraycopy(this.velocity, 0, planet.velocity, 0, this.velocity.length);

    return planet;
  }
}

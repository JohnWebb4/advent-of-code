/* Licensed under Apache-2.0 */
package advent.year2016.day13;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MazeTwistyCubiclesTest {

    @Test
    public void findFewestStepsToWin() {
        assertEquals(11, MazeTwistyCubicles.findFewestStepsToReachPoint(10, 7, 4));
        assertEquals(82, MazeTwistyCubicles.findFewestStepsToReachPoint(1362, 31, 39));
    }

    @Test
    public void countLocationsWithinXSteps() {
        assertEquals(11, MazeTwistyCubicles.countLocationsWithinXSteps(10, 5));
        assertEquals(138, MazeTwistyCubicles.countLocationsWithinXSteps(1362, 50));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2020.day15;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RambunctiousRecitationTest {
    @Test
    public void getNthSpokenNumber_436() {
        assertEquals(436, RambunctiousRecitation.getNthSpokenNumber("0,3,6", 2020));
    }

    @Test
    public void getNthSpokenNumber_1() {
        assertEquals(1, RambunctiousRecitation.getNthSpokenNumber("1,3,2", 2020));
    }

    @Test
    public void getNthSpokenNumber_27() {
        assertEquals(27, RambunctiousRecitation.getNthSpokenNumber("1,2,3", 2020));
    }

    @Test
    public void getNthSpokenNumber_78() {
        assertEquals(78, RambunctiousRecitation.getNthSpokenNumber("2,3,1", 2020));
    }

    @Test
    public void getNthSpokenNumber_438() {
        assertEquals(438, RambunctiousRecitation.getNthSpokenNumber("3,2,1", 2020));
    }

    @Test
    public void getNthSpokenNumber_1836() {
        assertEquals(1836, RambunctiousRecitation.getNthSpokenNumber("3,1,2", 2020));
    }

    @Test
    public void getNthSpokenNumber_273() {
        assertEquals(273, RambunctiousRecitation.getNthSpokenNumber("1,12,0,20,8,16", 2020));
    }

//    @Test
//    public void getNthSpokenNumber_175594() {
//        assertEquals(175594, RambunctiousRecitation.getNthSpokenNumber("0,3,6", 300000000));
//    }
//
//    @Test
//    public void getNthSpokenNumber_362() {
//        assertEquals(362, RambunctiousRecitation.getNthSpokenNumber("1,3,2", 300000000));
//    }
//
//    @Test
//    public void getNthSpokenNumber_47205() {
//        assertEquals(47025, RambunctiousRecitation.getNthSpokenNumber("1,12,0,20,8,16", 30000000));
//    }
}
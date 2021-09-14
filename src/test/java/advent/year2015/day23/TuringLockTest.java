/* Licensed under Apache-2.0 */
package advent.year2015.day23;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TuringLockTest {

    @Test
    public void getValueInRegister() {
        assertEquals(2, TuringLock.getValueInRegister("inc a\n" +
                "jio a, +2\n" +
                "tpl a\n" +
                "inc a", "a"));
        assertEquals(184, TuringLock.getValueInRegister("jio a, +19\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "jmp +23\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "jio a, +8\n" +
                "inc b\n" +
                "jie a, +4\n" +
                "tpl a\n" +
                "inc a\n" +
                "jmp +2\n" +
                "hlf a\n" +
                "jmp -7", "b"));
        assertEquals(231, TuringLock.getValueInRegister("inc a\n" +
                "jio a, +19\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "jmp +23\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "inc a\n" +
                "inc a\n" +
                "tpl a\n" +
                "tpl a\n" +
                "inc a\n" +
                "jio a, +8\n" +
                "inc b\n" +
                "jie a, +4\n" +
                "tpl a\n" +
                "inc a\n" +
                "jmp +2\n" +
                "hlf a\n" +
                "jmp -7", "b"));
    }
}
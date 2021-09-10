package advent.year2015.day22;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class WizardSimulatorTest {
    static WizardSimulator.Character testPlayer;
    static WizardSimulator.Character testEnemy;

    @BeforeClass
    public static void initialize() {
        testPlayer = new WizardSimulator.Character(10, 250);
        testPlayer.addAction(
                WizardSimulator.Action.getBuilder()
                        .setName("Magic Missile")
                        .setManaCost(53)
                        .setDamage(4)
                        .build());

        testPlayer.addAction(
                WizardSimulator.Action.getBuilder()
                        .setName("Drain")
                        .setManaCost(53)
                        .setDamage(4)
                        .build());

        testEnemy = new WizardSimulator.Character(13, 0);
    }

    @Test
    public void getMinManaToWin() {
        assertEquals(226, WizardSimulator.getMinManaToWin(testPlayer, testEnemy));
    }
}
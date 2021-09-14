/* Licensed under Apache-2.0 */
package advent.year2015.day22;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WizardSimulatorTest {
    static List<WizardSimulator.Action> playerActions;
    static List<WizardSimulator.Action> enemyActions;

    @BeforeClass
    public static void initialize() {
        playerActions = new LinkedList<>();
        enemyActions = new LinkedList<>();

        playerActions.add(WizardSimulator.Action.getBuilder()
                .setName("Magic Missile")
                .setManaCost(53)
                .setDamage(4)
                .build());
        playerActions.add(WizardSimulator.Action.getBuilder()
                .setName("Drain")
                .setManaCost(73)
                .setDamage(2)
                .setHeal(2)
                .build()
        );
        playerActions.add(WizardSimulator.Action.getBuilder()
                .setName("Shield")
                .setManaCost(113)
                .setEffect(WizardSimulator.Effect.getBuilder()
                        .setName("Shield")
                        .setEffectLength(6)
                        .setArmor(7)
                        .build())
                .build()
        );
        playerActions.add(WizardSimulator.Action.getBuilder()
                .setName("Poison")
                .setManaCost(173)
                .setEffect(WizardSimulator.Effect.getBuilder()
                        .setName("Posion")
                        .setEffectLength(6)
                        .setDamage(3)
                        .build())
                .build());

        playerActions.add(WizardSimulator.Action.getBuilder()
                .setName("Recharge")
                .setManaCost(229)
                .setEffect(WizardSimulator.Effect.getBuilder()
                        .setName("Recharge")
                        .setEffectLength(5)
                        .setManaRecharge(101)
                        .build())
                .build());

        enemyActions.add(WizardSimulator.Action.getBuilder()
                .setName("Attack")
                .setDamage(8)
                .setManaCost(0)
                .build()
        );
    }

    @Test
    public void getMinManaToWin() {
        assertEquals(226, WizardSimulator.getMinManaToWin(WizardSimulator.Character.getBuilder()
                        .setName("Player")
                        .setActions(playerActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(10)
                        .setMana(250)
                        .build(),
                WizardSimulator.Character.getBuilder()
                        .setName("Boss")
                        .setActions(enemyActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(13)
                        .setMana(0)
                        .build()));

        assertEquals(641, WizardSimulator.getMinManaToWin(WizardSimulator.Character.getBuilder()
                        .setName("Player")
                        .setActions(playerActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(10)
                        .setMana(250)
                        .build(),
                WizardSimulator.Character.getBuilder()
                        .setName("Boss")
                        .setActions(enemyActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(14)
                        .setMana(0)
                        .build()));

        // Greater than 854
        assertEquals(0, WizardSimulator.getMinManaToWin(WizardSimulator.Character.getBuilder()
                        .setName("Player")
                        .setActions(playerActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(50)
                        .setMana(500)
                        .build(),
                WizardSimulator.Character.getBuilder()
                        .setName("Boss")
                        .setActions(enemyActions.toArray(WizardSimulator.Action[]::new))
                        .setHitPoints(55)
                        .setMana(0)
                        .build()));

    }
}
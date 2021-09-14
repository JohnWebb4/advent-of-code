/* Licensed under Apache-2.0 */
package advent.year2015.day22;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WizardSimulatorTest {
    static WizardSimulator.Character testPlayer;
    static WizardSimulator.Character testEnemy;

    @BeforeClass
    public static void initialize() {
        List<WizardSimulator.Action> playerActions = new LinkedList();
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

        WizardSimulator.Character.Builder testPlayerBuilder = WizardSimulator.Character.getBuilder();
        testPlayerBuilder.setName("Player");
        testPlayerBuilder.setActions(playerActions.toArray(WizardSimulator.Action[]::new));
        testPlayerBuilder.setHitPoints(10);
        testPlayerBuilder.setMana(250);

        List<WizardSimulator.Action> enemyActions = new LinkedList<>();
        enemyActions.add(WizardSimulator.Action.getBuilder()
                .setName("Attack")
                .setDamage(8)
                .setManaCost(0)
                .build()
        );
        WizardSimulator.Character.Builder testEnemyBuilder = WizardSimulator.Character.getBuilder();
        testEnemyBuilder.setName("Boss");
        testEnemyBuilder.setActions(enemyActions.toArray(WizardSimulator.Action[]::new));
        testEnemyBuilder.setHitPoints(13);
        testEnemyBuilder.setMana(0);

        testEnemy = testEnemyBuilder.build();
        testPlayer = testPlayerBuilder.build();
    }

    @Test
    public void getMinManaToWin() {
        assertEquals(226, WizardSimulator.getMinManaToWin(testPlayer, testEnemy));
    }
}
/* Licensed under Apache-2.0 */
package advent.year2015.day21;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class RPGSimulatorTest {
    static RPGSimulator.Store emptyStore;
    static RPGSimulator.Store store;
    static RPGSimulator.Character playerInput;
    static RPGSimulator.Character playerTest;
    static RPGSimulator.Character enemyInput;
    static RPGSimulator.Character enemyTest;

    @BeforeClass
    public static void initialize() {
        emptyStore = RPGSimulator.Store.getBuilder().build();

        store = RPGSimulator.Store.getStoreItemsFromString("Weapons:    Cost  Damage  Armor\n" +
                "Dagger        8     4       0\n" +
                "Shortsword   10     5       0\n" +
                "Warhammer    25     6       0\n" +
                "Longsword    40     7       0\n" +
                "Greataxe     74     8       0\n" +
                "\n" +
                "Armor:      Cost  Damage  Armor\n" +
                "Leather      13     0       1\n" +
                "Chainmail    31     0       2\n" +
                "Splintmail   53     0       3\n" +
                "Bandedmail   75     0       4\n" +
                "Platemail   102     0       5\n" +
                "\n" +
                "Rings:      Cost  Damage  Armor\n" +
                "Damage +1    25     1       0\n" +
                "Damage +2    50     2       0\n" +
                "Damage +3   100     3       0\n" +
                "Defense +1   20     0       1\n" +
                "Defense +2   40     0       2\n" +
                "Defense +3   80     0       3");

        playerTest = RPGSimulator.Character.getBuilder().setHitPoints(8).setBaseDamage(7).setBaseArmor(5).build();
        enemyTest = RPGSimulator.Character.getBuilder().setHitPoints(12).setBaseDamage(7).setBaseArmor(2).build();

        playerInput = RPGSimulator.Character.getBuilder().setHitPoints(100).build();
        enemyInput = RPGSimulator.Character.getBuilder().setHitPoints(109).setBaseDamage(8).setBaseArmor(2).build();
    }

    @Test
    public void getCheapestGoldToWin() {
        assertEquals(0.0f, RPGSimulator.getCheapestGoldToWin(emptyStore, playerTest, enemyTest), 0.01f);
        assertEquals(111.0f, RPGSimulator.getCheapestGoldToWin(store, playerInput, enemyInput), 0.01f);
    }

    @Test
    public void getMostGoldToLoose() {
        assertEquals(-1.0f, RPGSimulator.getMostGoldToLoose(emptyStore, playerTest, enemyTest), 0.01f);
        assertEquals(188.0f, RPGSimulator.getMostGoldToLoose(store, playerInput, enemyInput), 0.01f);
    }
}
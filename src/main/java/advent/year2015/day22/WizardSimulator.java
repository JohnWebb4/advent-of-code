/* Licensed under Apache-2.0 */
package advent.year2015.day22;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class WizardSimulator {
    public static class FightInstance implements Comparable<FightInstance> {
        public final Character enemy;
        public final Character player;
        public final int playerManaSpent;

        public static class Builder {
            Character enemy;
            Character player;
            int playerManaSpent;

            public Builder() {
            }

            public Builder(FightInstance instance) {
                this.player = instance.player.getBuilderFromCharacter().build();
                this.enemy = instance.enemy.getBuilderFromCharacter().build();
                this.playerManaSpent = instance.playerManaSpent;
            }

            public Builder setPlayer(Character player) {
                this.player = player;
                return this;
            }

            public Builder setEnemy(Character enemy) {
                this.enemy = enemy;
                return this;
            }

            public Builder setPlayerManaSpent(int playerManaSpent) {
                this.playerManaSpent = playerManaSpent;
                return this;
            }

            public FightInstance build() {
                return new FightInstance(this.player, this.enemy, this.playerManaSpent);
            }
        }

        public FightInstance(Character player, Character enemy, int playerManaSpent) {
            this.player = player;
            this.playerManaSpent = playerManaSpent;
            this.enemy = enemy;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder getBuilderFromInstance() {
            return new Builder(this);
        }

        @Override
        public int compareTo(FightInstance o) {
            int thisValue = this.player.hitPoints - this.playerManaSpent - this.enemy.hitPoints;
            int oValue = o.player.hitPoints - o.playerManaSpent - o.enemy.hitPoints;

            return thisValue - oValue;
        }
    }

    public static class CharacterInstance {
        public int hitPoints;
        public int mana;
        public List<Action> actions;
        public List<Effect.EffectInstance> effectInstances;

        public CharacterInstance(Character character) {
            this.hitPoints = character.hitPoints;
            this.mana = character.mana;
            this.actions = new LinkedList<>(Arrays.asList(character.actions));
            this.effectInstances = new LinkedList<>(Arrays.asList(character.effectInstances));
        }

        public boolean isAlive() {
            return this.hitPoints > 0;
        }

        public Character getCharacter() {
            return Character.getBuilder()
                    .setHitPoints(this.hitPoints)
                    .setMana(this.mana)
                    .setActions(this.actions)
                    .setEffectInstances(this.effectInstances)
                    .build();
        }
    }

    public static class Character {
        final int hitPoints;
        final int mana;
        final Action[] actions;
        final Effect.EffectInstance[] effectInstances;

        public static class Builder {
            int hitPoints;
            int mana;
            List<Action> actions;
            List<Effect.EffectInstance> effectInstances;

            public Builder() {
                this.actions = new LinkedList<>();
                this.effectInstances = new LinkedList<>();
            }

            public Builder(Character character) {
                this.hitPoints = character.hitPoints;
                this.mana = character.mana;
                this.actions = new LinkedList<>(Arrays.asList(character.actions));
                this.effectInstances = new LinkedList<>(Arrays.asList(character.effectInstances));
            }

            public Builder setHitPoints(int hitPoints) {
                this.hitPoints = hitPoints;
                return this;
            }

            public Builder setMana(int mana) {
                this.mana = mana;
                return this;
            }

            public Builder setActions(List<Action> actions) {
                this.actions = actions;
                return this;
            }

            public Builder addAction(Action action) {
                this.actions.add(action);
                return this;
            }

            public Builder setEffectInstances(List<Effect.EffectInstance> effectInstances) {
                this.effectInstances = effectInstances;
                return this;
            }

            public Builder addEffectInstance(Effect.EffectInstance effectInstance) {
                this.effectInstances.add(effectInstance);
                return this;
            }

            public Character build() {
                return new Character(hitPoints,
                        mana,
                        actions.toArray(Action[]::new),
                        effectInstances.toArray(Effect.EffectInstance[]::new));
            }
        }

        public Character(int hitPoints, int mana, Action[] actions, Effect.EffectInstance[] effectInstances) {
            this.hitPoints = hitPoints;
            this.mana = mana;
            this.actions = actions;
            this.effectInstances = effectInstances;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder getBuilderFromCharacter() {
            return new Builder(this);
        }

        public CharacterInstance getInstance() {
            return new CharacterInstance(this);
        }

        public boolean isAlive() {
            return this.hitPoints > 0;
        }
    }

    public static class Action {
        final String name;
        final int manaCost;
        final int damage;
        final int heal;
        final Effect effect;

        public static class Builder {
            String name;
            int manaCost;
            int damage;
            int heal;
            Effect effect;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setManaCost(int manaCost) {
                this.manaCost = manaCost;
                return this;
            }

            public Builder setDamage(int damage) {
                this.damage = damage;
                return this;
            }

            public Builder setHeal(int heal) {
                this.heal = heal;
                return this;
            }

            public Builder setEffect(Effect effect) {
                this.effect = effect;
                return this;
            }

            public Action build() {
                return new Action(name, manaCost, damage, heal, effect);
            }
        }

        public Action(String name, int manaCost, int damage, int heal, Effect effect) {
            this.name = name;
            this.manaCost = manaCost;
            this.damage = damage;
            this.heal = heal;
            this.effect = effect;
        }

        public static Builder getBuilder() {
            return new Builder();
        }
    }

    public static class Effect {
        final String name;
        final int armor;
        final int damage;
        final int manaRecharge;
        final int numTurnsAlive;

        public static class Builder {
            String name;
            int armor;
            int damage;
            int manaRecharge;
            int numTurnsAlive;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setArmor(int armor) {
                this.armor = armor;
                return this;
            }

            public Builder setDamage(int damage) {
                this.damage = damage;
                return this;
            }

            public Builder setManaRecharge(int manaRecharge) {
                this.manaRecharge = manaRecharge;
                return this;
            }

            public Builder setNumTurnsAlive(int numTurnsAlive) {
                this.numTurnsAlive = numTurnsAlive;
                return this;
            }

            public Effect build() {
                return new Effect(this.name, this.armor, this.damage, this.manaRecharge, this.numTurnsAlive);
            }
        }

        public static class EffectInstance extends Effect {
            private int turnsAlive;

            public EffectInstance(Effect effect) {
                super(effect.name, effect.armor, effect.damage, effect.manaRecharge, effect.numTurnsAlive);

                this.turnsAlive = effect.numTurnsAlive;
            }

            public boolean isAlive() {
                return this.turnsAlive > 0;
            }

            public void decreaseTurnsAlive() {
                this.turnsAlive--;
            }
        }

        public Effect(String name, int armor, int damage, int manaRecharge, int numTurnsAlive) {
            this.name = name;
            this.armor = armor;
            this.damage = damage;
            this.manaRecharge = manaRecharge;
            this.numTurnsAlive = numTurnsAlive;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public EffectInstance getInstance() {
            return new EffectInstance(this);
        }
    }

    public static int getMinManaToWin(Character player, Character enemy) {
        PriorityQueue<FightInstance> instancesToCheck = new PriorityQueue<>();
        instancesToCheck.add(FightInstance.getBuilder()
                .setPlayer(player)
                .setEnemy(enemy)
                .setPlayerManaSpent(0)
                .build());

        int minPlayerManaSpent = Integer.MAX_VALUE;
        while (instancesToCheck.size() > 0) {
            FightInstance fightInstance = instancesToCheck.poll();

            if (fightInstance.playerManaSpent > minPlayerManaSpent) {
                continue;
            }

            CharacterInstance playerInstance = fightInstance.player.getInstance();
            CharacterInstance enemyInstance = fightInstance.enemy.getInstance();

            // TODO: Apply effects
            if (playerInstance.isAlive()) {
                for (Effect.EffectInstance effectInstance : playerInstance.effectInstances) {
                    if (effectInstance.isAlive()) {
                        // TODO: Apply effects
                    }
                }
            }

            if (enemyInstance.isAlive()) {
                for (Effect.EffectInstance effectInstance : enemyInstance.effectInstances) {
                    if (effectInstance.isAlive()) {

                        // TODO: Apply effects

                    }
                }
            }

            Character playerAfterEffects = playerInstance.getCharacter();
            Character enemyAfterEffects = enemyInstance.getCharacter();

            if (playerAfterEffects.isAlive() && enemyAfterEffects.isAlive()) {
                for (Action action : playerAfterEffects.actions) {
                    int playerManaSpent = fightInstance.playerManaSpent;
                    CharacterInstance playerActionInstance = playerAfterEffects.getInstance();
                    CharacterInstance enemyActionInstance = enemyAfterEffects.getInstance();

                    // TODO: DO move

                    if (enemyInstance.isAlive()) {

                        // TODO: Add to stack

                    } else if (playerManaSpent < minPlayerManaSpent) {
                        minPlayerManaSpent = playerManaSpent;
                    }
                }
            } else if (playerAfterEffects.isAlive() && !enemyAfterEffects.isAlive()) {
                if (fightInstance.playerManaSpent < minPlayerManaSpent) {
                    minPlayerManaSpent = fightInstance.playerManaSpent;
                }
            }
        }

        return minPlayerManaSpent;
    }
}

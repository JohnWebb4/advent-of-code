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

        @Override
        public int compareTo(FightInstance o) {
            int thisValue = this.player.hitPoints - this.playerManaSpent - this.enemy.hitPoints;
            int oValue = o.player.hitPoints - o.playerManaSpent - o.enemy.hitPoints;

            return thisValue - oValue;
        }
    }

    public static class Character {
        final String name;
        final int armor;
        final int baseArmor;
        final int hitPoints;
        final int mana;
        final Action[] actions;
        final EffectInstance[] effectInstances;

        public static class Builder {
            String name;
            int armor;
            int baseArmor;
            int hitPoints;
            int mana;
            Action[] actions;
            EffectInstance[] effectInstances;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setArmor(int armor) {
                this.armor = armor;
                return this;
            }

            public Builder setBaseArmor(int baseArmor) {
                this.baseArmor = baseArmor;
                return this;
            }


            public Builder setHitPoints(int hitPoints) {
                this.hitPoints = hitPoints;
                return this;
            }

            public Builder setMana(int mana) {
                this.mana = mana;
                return this;
            }

            public Builder setActions(Action[] actions) {
                this.actions = actions;
                return this;
            }

            public Builder setEffectInstances(EffectInstance[] effectInstances) {
                this.effectInstances = effectInstances;
                return this;
            }

            public Character build() {
                if (actions == null) {
                    actions = new Action[]{};
                }

                if (effectInstances == null) {
                    effectInstances = new EffectInstance[]{};
                }

                return new Character(name,
                        hitPoints,
                        mana,
                        armor,
                        baseArmor,
                        actions,
                        effectInstances);
            }
        }

        public Character(String name, int hitPoints, int mana, int armor, int baseArmor, Action[] actions, EffectInstance[] effectInstances) {
            this.name = name;
            this.hitPoints = hitPoints;
            this.mana = mana;
            this.armor = armor;
            this.baseArmor = baseArmor;
            this.actions = actions;
            this.effectInstances = effectInstances;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder getBuilderFromCharacter() {
            return new Builder()
                    .setName(this.name)
                    .setActions(this.actions)
                    .setArmor(this.armor)
                    .setBaseArmor(this.baseArmor)
                    .setEffectInstances(this.effectInstances)
                    .setHitPoints(this.hitPoints)
                    .setMana(this.mana);
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

    public static class EffectInstance {
        final String name;
        final int armor;
        final int damage;
        final int manaRecharge;
        final int effectLength;
        final int turnsAlive;

        public static class Builder {
            String name;
            int armor;
            int damage;
            int manaRecharge;
            int effectLength;
            int turnsAlive;

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

            public Builder setEffectLength(int effectLength) {
                this.effectLength = effectLength;
                return this;
            }

            public Builder setTurnsAlive(int turnsAlive) {
                this.turnsAlive = turnsAlive;
                return this;
            }

            public EffectInstance build() {
                return new EffectInstance(name, armor, damage, manaRecharge, effectLength, turnsAlive);
            }
        }

        public EffectInstance(String name, int armor, int damage, int manaRecharge, int effectLength, int turnsAlive) {
            this.name = name;
            this.armor = armor;
            this.damage = damage;
            this.manaRecharge = manaRecharge;
            this.effectLength = effectLength;
            this.turnsAlive = turnsAlive;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder getBuilderFromInstance() {
            return new Builder()
                    .setName(this.name)
                    .setArmor(this.armor)
                    .setDamage(this.damage)
                    .setManaRecharge(this.manaRecharge)
                    .setEffectLength(this.effectLength)
                    .setTurnsAlive(this.turnsAlive);
        }

        public boolean isAlive() {
            return this.turnsAlive < this.effectLength;
        }
    }

    public static class Effect {
        final String name;
        final int armor;
        final int damage;
        final int manaRecharge;
        final int effectLength;

        public static class Builder {
            String name;
            int armor;
            int damage;
            int manaRecharge;
            int effectLength;

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

            public Builder setEffectLength(int effectLength) {
                this.effectLength = effectLength;
                return this;
            }

            public Effect build() {
                return new Effect(this.name, this.armor, this.damage, this.manaRecharge, this.effectLength);
            }
        }

        public Effect(String name, int armor, int damage, int manaRecharge, int effectLength) {
            this.name = name;
            this.armor = armor;
            this.damage = damage;
            this.manaRecharge = manaRecharge;
            this.effectLength = effectLength;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public EffectInstance getInstance() {
            return EffectInstance.getBuilder()
                    .setName(this.name)
                    .setArmor(this.armor)
                    .setDamage(this.damage)
                    .setManaRecharge(this.manaRecharge)
                    .setEffectLength(this.effectLength)
                    .setTurnsAlive(0)
                    .build();
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

            for (FightInstance playerOption : getFightOptions(fightInstance.player, fightInstance.enemy, fightInstance.playerManaSpent)) {
                int playerManaSpent = playerOption.playerManaSpent;

                if (playerOption.player.isAlive()) {
                    if (playerOption.enemy.isAlive()) {
                        for (FightInstance enemyOption : getEnemyOptions(playerOption.enemy, playerOption.player)) {
                            if (enemyOption.player.isAlive()) {
                                if (enemyOption.enemy.isAlive()) {
                                    instancesToCheck.add(FightInstance.getBuilder()
                                            .setPlayerManaSpent(playerManaSpent)
                                            .setPlayer(enemyOption.player)
                                            .setEnemy(enemyOption.enemy)
                                            .build());
                                } else if (playerManaSpent < minPlayerManaSpent) {
                                    // Enemy killed by effect on enemy turn
                                    minPlayerManaSpent = playerManaSpent;
                                }
                            }
                        }
                    } else if (playerOption.playerManaSpent < minPlayerManaSpent) {
                        // Enemey killed on player turn
                        minPlayerManaSpent = playerOption.playerManaSpent;
                    }
                }
            }
        }

        return minPlayerManaSpent;
    }

    public static FightInstance[] getEnemyOptions(Character enemy, Character player) {
        // Find a better way to handle getting the enemies options
        // Reverse character and target for enemy and players
        return Arrays.stream(getFightOptions(enemy, player, 0)).map((fi) -> FightInstance.getBuilder()
                .setPlayer(fi.enemy)
                .setEnemy(fi.player)
                .build()).toArray(FightInstance[]::new);
    }

    public static FightInstance[] getFightOptions(Character character, Character target, int characterManaSpent) {
        Character[] charactersAfterEffects = applyAllEffects(character, target);
        Character characterAfterEffects = charactersAfterEffects[0];
        Character targetAfterEffects = charactersAfterEffects[1];

        List<FightInstance> options = new LinkedList<>();

        if (characterAfterEffects.isAlive()) {
            for (Action action : characterAfterEffects.actions) {
                if (characterAfterEffects.mana >= action.manaCost && !Arrays.stream(characterAfterEffects.effectInstances).anyMatch((ei) -> ei.name.equals(action.name))) {
                    System.out.println(String.format("-- %s turn -- ", characterAfterEffects.name));
                    System.out.println(String.format("- %s has %s hit points, %s armor %s mana",
                            characterAfterEffects.name,
                            characterAfterEffects.hitPoints,
                            characterAfterEffects.armor,
                            characterAfterEffects.mana
                    ));
                    System.out.println(String.format("- %s has %s hit points", targetAfterEffects.name, targetAfterEffects.hitPoints));

                    int totalManaSpentAfterAction = characterManaSpent + action.manaCost;

                    Character.Builder characterBuilder = characterAfterEffects.getBuilderFromCharacter();
                    characterBuilder.setMana(characterBuilder.mana - action.manaCost);
                    characterBuilder.setHitPoints(characterBuilder.hitPoints + action.heal);

                    Character.Builder targetBuilder = targetAfterEffects.getBuilderFromCharacter();
                    targetBuilder.setHitPoints(targetBuilder.hitPoints - Math.max(1, action.damage - targetBuilder.armor));

                    if (action.effect != null) {
                        List<EffectInstance> characterEffectInstances = new LinkedList<>();

                        for (EffectInstance effectInstance : characterBuilder.effectInstances) {
                            characterEffectInstances.add(effectInstance.getBuilderFromInstance().build());
                        }

                        characterEffectInstances.add(action.effect.getInstance());

                        characterBuilder.setEffectInstances(characterEffectInstances.toArray(EffectInstance[]::new));
                    }

                    System.out.println(String.format("%s casts %s", characterBuilder.name, action.name));

                    options.add(FightInstance.getBuilder()
                            .setPlayerManaSpent(totalManaSpentAfterAction)
                            .setPlayer(characterBuilder.build())
                            .setEnemy(targetBuilder.build())
                            .build());

                    System.out.println("");
                }
            }
        } else {
            options.add(FightInstance.getBuilder()
                    .setPlayer(characterAfterEffects)
                    .setEnemy(targetAfterEffects)
                    .setPlayerManaSpent(characterManaSpent).build());
        }


        return options.toArray(FightInstance[]::new);
    }

    public static Character[] applyAllEffects(Character character, Character target) {
        Character newCharacter = character;
        Character newTarget = target;

        Character[] charactersAfterEffect = applyEffect(newCharacter, newTarget);
        newCharacter = charactersAfterEffect[0];
        newTarget = charactersAfterEffect[1];

        charactersAfterEffect = applyEffect(newTarget, newCharacter);
        newTarget = charactersAfterEffect[0];
        newCharacter = charactersAfterEffect[1];

        return new Character[]{newCharacter, newTarget};
    }

    public static Character[] applyEffect(Character character, Character target) {
        Character.Builder characterBuilder = character.getBuilderFromCharacter();
        List<EffectInstance> characterEffects = new LinkedList<>();
        Character.Builder targetBuilder = target.getBuilderFromCharacter();

        if (character.isAlive()) {
            characterBuilder.setArmor(character.baseArmor);

            for (EffectInstance effectInstance : character.effectInstances) {
                if (effectInstance.isAlive()) {
                    characterBuilder.setArmor(characterBuilder.armor + effectInstance.armor);
                    characterBuilder.setMana(characterBuilder.mana + effectInstance.manaRecharge);
                    targetBuilder.setHitPoints(targetBuilder.hitPoints - Math.max(1, effectInstance.damage - targetBuilder.armor));

                    EffectInstance nextInstance = effectInstance.getBuilderFromInstance()
                            .setTurnsAlive(effectInstance.turnsAlive + 1)
                            .build();

                    if (nextInstance.isAlive()) {
                        characterEffects.add(nextInstance);
                    }
                }
            }

            characterBuilder.setEffectInstances(characterEffects.toArray(EffectInstance[]::new));
        }

        return new Character[]{characterBuilder.build(), targetBuilder.build()};
    }
}

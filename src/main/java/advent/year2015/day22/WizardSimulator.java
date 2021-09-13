package advent.year2015.day22;

import java.util.LinkedList;
import java.util.List;

public class WizardSimulator {
    public static class Character {
        final int hitPoints;
        final int mana;
        Action[] actions;
        Effect.EffectInstance[] effectInstances;

        public static class Builder {
            int hitPoints;
            int mana;
            List<Action> actions;
            List<Effect.EffectInstance> effectInstances;

            public Builder() {
                this.actions = new LinkedList<>();
                this.effectInstances = new LinkedList<>();
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

        public static class EffectInstance {
            public final Effect effect;
            private int turnsAlive;

            public EffectInstance(Effect effect) {
                this.turnsAlive = effect.numTurnsAlive;
                this.effect = effect;
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
        return 0;
    }
}

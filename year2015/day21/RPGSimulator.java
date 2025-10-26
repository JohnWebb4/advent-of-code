/* Licensed under Apache-2.0 */
package advent.year2015.day21;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGSimulator {
    public static final Pattern ITEM_PATTERN = Pattern.compile("(\\w+(?:\\s\\+\\d)?)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)");

    public static class FightInstance implements Comparable<FightInstance> {
        Character player;
        Character enemy;

        public static class Builder {
            Character player;
            Character enemy;

            public Builder setPlayer(Character player) {
                this.player = player;
                return this;
            }

            public Builder setEnemy(Character enemy) {
                this.enemy = enemy;
                return this;
            }

            public FightInstance build() {
                return new FightInstance(this.player, this.enemy);
            }
        }

        public FightInstance(Character player, Character enemy) {
            this.player = player;
            this.enemy = enemy;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public boolean willPlayerKillEnemy() {
            int enemyDamage = Math.max(1, this.enemy.getDamagePoints() - this.player.getArmorPoints());
            int playerDamage = Math.max(1, this.player.getDamagePoints() - this.enemy.getArmorPoints());

            int turnsToKillPlayer = this.player.getHitPoints() / enemyDamage;
            int turnsToKillEnemy = this.enemy.getHitPoints() / playerDamage;

            return turnsToKillEnemy <= turnsToKillPlayer;
        }

        @Override
        public int compareTo(FightInstance fightInstance) {
            return Integer.compare(this.getPlayerCost(), fightInstance.getPlayerCost());
        }

        public int getPlayerCost() {
            return this.player.getTotalCost();
        }

        @Override
        public String toString() {
            return String.join("{fightInstance}", new String[]{
                    this.player != null ? this.player.toString() : "null",
                    this.enemy != null ? this.enemy.toString() : "null",
            });
        }
    }

    public static class Character {
        int hitPoints;
        int baseDamage;
        int baseArmor;

        Item weapon;
        Item armor;
        Item ringLeft;
        Item ringRight;

        public static class Builder {
            int hitPoints;
            int baseDamage;
            int baseArmor;

            Item weapon;
            Item armor;
            Item ringLeft;
            Item ringRight;

            public Builder setHitPoints(int hitPoints) {
                this.hitPoints = hitPoints;
                return this;
            }

            public Builder setBaseDamage(int baseDamage) {
                this.baseDamage = baseDamage;
                return this;
            }

            public Builder setBaseArmor(int baseArmor) {
                this.baseArmor = baseArmor;

                return this;
            }

            public Builder setWeapon(Item weapon) {
                this.weapon = weapon;
                return this;
            }

            public Builder setArmor(Item armor) {
                this.armor = armor;
                return this;
            }

            public Builder setRingLeft(Item ringLeft) {
                this.ringLeft = ringLeft;
                return this;
            }

            public Builder setRingRight(Item ringRight) {
                this.ringRight = ringRight;
                return this;
            }

            public Builder copyCharacter(Character character) {
                this.hitPoints = character.hitPoints;
                this.baseDamage = character.baseDamage;
                this.baseArmor = character.baseArmor;
                this.weapon = character.weapon;
                this.armor = character.armor;
                this.ringLeft = character.ringLeft;
                this.ringRight = character.ringRight;

                return this;
            }

            public Character build() {
                return new Character(this.hitPoints, this.baseDamage, this.baseArmor, this.weapon, this.armor, this.ringLeft, this.ringRight);
            }
        }

        Character(int hitPoints, int baseDamage, int baseArmor, Item weapon, Item armor, Item ringLeft, Item ringRight) {
            this.hitPoints = hitPoints;
            this.baseDamage = baseDamage;
            this.baseArmor = baseArmor;

            this.weapon = weapon;
            this.armor = armor;
            this.ringLeft = ringLeft;
            this.ringRight = ringRight;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public int getHitPoints() {
            return this.hitPoints;
        }

        public int getDamagePoints() {
            int damage = this.baseDamage;

            if (this.weapon != null) {
                damage += this.weapon.damage;
            }

            if (this.armor != null) {
                damage += this.armor.damage;
            }

            if (this.ringLeft != null) {
                damage += this.ringLeft.damage;
            }

            if (this.ringRight != null) {
                damage += this.ringRight.damage;
            }

            return damage;
        }

        public int getArmorPoints() {
            int armor = this.baseArmor;

            if (this.weapon != null) {
                armor += this.weapon.armor;
            }

            if (this.armor != null) {
                armor += this.armor.armor;
            }

            if (this.ringLeft != null) {
                armor += this.ringLeft.armor;
            }

            if (this.ringRight != null) {
                armor += this.ringRight.armor;
            }

            return armor;
        }

        public int getTotalCost() {
            int cost = 0;

            if (this.weapon != null) {
                cost += this.weapon.cost;
            }

            if (this.armor != null) {
                cost += this.armor.cost;
            }

            if (this.ringLeft != null) {
                cost += this.ringLeft.cost;
            }

            if (this.ringRight != null) {
                cost += this.ringRight.cost;
            }

            return cost;
        }

        @Override
        public String toString() {
            return String.join("{character}", new String[]{
                    Integer.toString(this.hitPoints),
                    Integer.toString(this.baseDamage),
                    Integer.toString(this.baseArmor),
                    this.weapon != null ? this.weapon.toString() : "null",
                    this.armor != null ? this.armor.toString() : "null",
                    this.ringLeft != null ? this.ringLeft.toString() : "null",
                    this.ringRight != null ? this.ringRight.toString() : "null",
            });
        }
    }

    public static class Store {
        Item[] weapons;
        Item[] armor;
        Item[] rings;

        public static class Builder {
            List<Item> weapons;
            List<Item> armor;
            List<Item> rings;

            public Builder() {
                this.weapons = new LinkedList<>();
                this.armor = new LinkedList<>();
                this.rings = new LinkedList<>();
            }

            public Builder addWeapon(Item weapon) {
                this.weapons.add(weapon);
                return this;
            }

            public Builder addArmor(Item armor) {
                this.armor.add(armor);
                return this;
            }

            public Builder addRing(Item ring) {
                this.rings.add(ring);
                return this;
            }

            public Store build() {
                return new Store(this.weapons.toArray(Item[]::new), this.armor.toArray(Item[]::new), this.rings.toArray(Item[]::new));
            }
        }

        public Store(Item[] weapons, Item[] armor, Item[] rings) {
            this.weapons = weapons;
            this.armor = armor;
            this.rings = rings;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public static Store getStoreItemsFromString(String storeString) {
            String[] itemCategories = storeString.split("\n\n");
            Builder storeBuilder = getBuilder();

            for (String itemCategory : itemCategories) {
                String[] rows = itemCategory.split("\n");

                String itemTypeString = rows[0].split(":")[0];
                Item.ItemType itemType;

                switch (itemTypeString.toLowerCase(Locale.ROOT)) {
                    case "armor":
                        itemType = Item.ItemType.ARMOR;
                        break;
                    case "weapons":
                        itemType = Item.ItemType.WEAPON;
                        break;
                    case "rings":
                        itemType = Item.ItemType.RING;
                        break;
                    default:
                        itemType = Item.ItemType.ARMOR;
                        break;
                }

                for (int i = 1; i < rows.length; i++) {
                    String row = rows[i];
                    Item.Builder itemBuilder = Item.getBuilder();

                    Matcher itemMatcher = ITEM_PATTERN.matcher(row);
                    if (itemMatcher.find()) {
                        itemBuilder.setName(itemMatcher.group(1));
                        itemBuilder.setCost(Integer.parseInt(itemMatcher.group(2)));
                        itemBuilder.setDamage(Integer.parseInt(itemMatcher.group(3)));
                        itemBuilder.setArmor(Integer.parseInt(itemMatcher.group(4)));
                        itemBuilder.setType(itemType);
                    }

                    Item item = itemBuilder.build();

                    switch (itemType) {
                        case ARMOR:
                            storeBuilder.addArmor(item);
                            break;
                        case WEAPON:
                            storeBuilder.addWeapon(item);
                            break;
                        case RING:
                            storeBuilder.addRing(item);
                            break;
                        default:
                            storeBuilder.addArmor(item);
                            break;
                    }
                }
            }

            return storeBuilder.build();
        }
    }

    public static class Item {
        final String name;
        final int armor;
        final int cost;
        final int damage;
        final ItemType type;

        public static class Builder {
            String name;
            int armor;
            int cost;
            int damage;
            ItemType type;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setArmor(int armor) {
                this.armor = armor;
                return this;
            }

            public Builder setCost(int cost) {
                this.cost = cost;
                return this;
            }

            public Builder setDamage(int damage) {
                this.damage = damage;
                return this;
            }

            public Builder setType(ItemType type) {
                this.type = type;
                return this;
            }

            public Item build() {
                return new Item(this.name, this.cost, this.damage, this.armor, this.type);
            }
        }

        public enum ItemType {
            ARMOR,
            RING,
            WEAPON
        }

        public Item(String name, int cost, int damage, int armor, ItemType type) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
            this.type = type;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public String getName() {
            return this.name;
        }

        public int getArmor() {
            return this.armor;
        }

        public int getCost() {
            return this.cost;
        }

        public int getDamage() {
            return this.damage;
        }

        public ItemType getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return String.join("{item}", new String[]{
                    this.name,
                    this.type != null ? this.type.name() : "null",
                    Integer.toString(this.cost),
                    Integer.toString(this.damage),
                    Integer.toString(this.armor),
            });
        }
    }

    public static float getCheapestGoldToWin(Store store, Character player, Character enemy) {
        PriorityQueue<FightInstance> instancesToCheck = new PriorityQueue<>();
        Set<String> seenInstances = new HashSet<>();
        instancesToCheck.add(FightInstance.getBuilder().setPlayer(player).setEnemy(enemy).build());

        while (instancesToCheck.size() > 0) {
            FightInstance fightInstance = instancesToCheck.poll();

            if (fightInstance.willPlayerKillEnemy()) {
                return fightInstance.getPlayerCost();
            } else {
                if (fightInstance.player.weapon == null) {
                    // Iterate weapon options
                    for (Item weapon : store.weapons) {
                        Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                        playerBuilder.setWeapon(weapon);

                        FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                        if (!seenInstances.contains(nextInstance.toString())) {
                            instancesToCheck.add(nextInstance);
                        }
                    }
                }

                if (fightInstance.player.armor == null) {
                    // Iterate armor options
                    for (Item armor : store.armor) {
                        Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                        playerBuilder.setArmor(armor);

                        FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                        if (!seenInstances.contains(nextInstance.toString())) {
                            instancesToCheck.add(nextInstance);
                        }
                    }
                }

                if (fightInstance.player.ringLeft == null) {
                    // Iterate ring left options. Not current right ring
                    for (Item ringLeft : store.rings) {
                        if (fightInstance.player.ringRight != null && ringLeft == fightInstance.player.ringRight) {
                            continue;
                        }

                        Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                        playerBuilder.setRingLeft(ringLeft);

                        FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                        if (!seenInstances.contains(nextInstance.toString())) {
                            instancesToCheck.add(nextInstance);
                        }
                    }
                }

                if (fightInstance.player.ringRight == null) {
                    // Iterate ring right options. Not current left ring
                    for (Item ringRight : store.rings) {
                        if (fightInstance.player.ringLeft != null && ringRight == fightInstance.player.ringLeft) {
                            continue;
                        }

                        Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                        playerBuilder.setRingRight(ringRight);

                        FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                        if (!seenInstances.contains(nextInstance.toString())) {
                            instancesToCheck.add(nextInstance);
                        }
                    }
                }
            }

            seenInstances.add(fightInstance.toString());
        }

        return -1;
    }

    public static float getMostGoldToLoose(Store store, Character player, Character enemy) {
        final Character.Builder mostExpensiveBuild = Character.getBuilder();
        mostExpensiveBuild.setHitPoints(player.hitPoints);
        mostExpensiveBuild.setBaseDamage(player.baseDamage);
        mostExpensiveBuild.setBaseArmor(player.baseArmor);
        mostExpensiveBuild.setWeapon(Arrays.stream(store.weapons).max(Comparator.comparingInt(Item::getCost)).orElse(null));
        mostExpensiveBuild.setArmor(Arrays.stream(store.armor).max(Comparator.comparingInt(Item::getCost)).orElse(null));
        mostExpensiveBuild.setRingLeft(Arrays.stream(store.rings).max(Comparator.comparingInt(Item::getCost)).orElse(null));
        mostExpensiveBuild.setRingRight(Arrays.stream(store.rings).max((fi1, fi2) -> {
            if (mostExpensiveBuild.ringLeft != null) {
                if (fi1 == mostExpensiveBuild.ringLeft) return -1;
                if (fi2 == mostExpensiveBuild.ringLeft) return 1;
            }

            return fi1.getCost() - fi2.getCost();
        }).orElse(null));
        final Character mostExpensivePlayer = mostExpensiveBuild.build();

        PriorityQueue<FightInstance> instancesToCheck = new PriorityQueue<>((o1, o2) -> -o1.compareTo(o2));

        Set<String> seenInstances = new HashSet<>();
        instancesToCheck.add(FightInstance.getBuilder().setPlayer(mostExpensivePlayer).setEnemy(enemy).build());

        while (instancesToCheck.size() > 0) {
            FightInstance fightInstance = instancesToCheck.poll();

            if (!fightInstance.willPlayerKillEnemy()) {
                return fightInstance.getPlayerCost();
            } else {
                if (fightInstance.player.weapon == mostExpensivePlayer.weapon) {
                    // Iterate weapon options
                    for (Item weapon : store.weapons) {
                        if (weapon != mostExpensivePlayer.weapon) {
                            Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                            playerBuilder.setWeapon(weapon);

                            FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                            if (!seenInstances.contains(nextInstance.toString())) {
                                instancesToCheck.add(nextInstance);
                            }
                        }
                    }
                }

                if (fightInstance.player.armor == mostExpensivePlayer.armor && fightInstance.player.armor != null) {
                    // Iterate armor options
                    for (Item armor : store.armor) {
                        if (armor != mostExpensivePlayer.armor) {
                            Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                            playerBuilder.setArmor(armor);

                            FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                            if (!seenInstances.contains(nextInstance.toString())) {
                                instancesToCheck.add(nextInstance);
                            }
                        }
                    }

                    FightInstance nullInstance = FightInstance.getBuilder()
                            .setPlayer(Character.getBuilder().copyCharacter(fightInstance.player).setArmor(null).build())
                            .setEnemy(fightInstance.enemy).build();

                    if (!seenInstances.contains(nullInstance.toString())) {
                        instancesToCheck.add(nullInstance);
                    }
                }

                if (fightInstance.player.ringLeft == mostExpensivePlayer.ringLeft && fightInstance.player.ringLeft != null) {
                    // Iterate ring left options. Not current right ring
                    for (Item ringLeft : store.rings) {
                        if (fightInstance.player.ringRight != null && ringLeft == fightInstance.player.ringRight) {
                            continue;
                        }

                        if (ringLeft != mostExpensivePlayer.ringLeft) {
                            Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                            playerBuilder.setRingLeft(ringLeft);

                            FightInstance nextInstance = FightInstance.getBuilder()
                                    .setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                            if (!seenInstances.contains(nextInstance.toString())) {
                                instancesToCheck.add(nextInstance);
                            }
                        }
                    }

                    FightInstance nullInstance = FightInstance.getBuilder()
                            .setPlayer(Character.getBuilder().copyCharacter(fightInstance.player).setRingLeft(null).build())
                            .setEnemy(fightInstance.enemy).build();

                    if (!seenInstances.contains(nullInstance.toString())) {
                        instancesToCheck.add(nullInstance);
                    }
                }

                if (fightInstance.player.ringRight == mostExpensivePlayer.ringRight && fightInstance.player.ringRight != null) {
                    // Iterate ring right options. Not current left ring
                    for (Item ringRight : store.rings) {
                        if (fightInstance.player.ringLeft != null && ringRight == fightInstance.player.ringLeft) {
                            continue;
                        }

                        if (ringRight != mostExpensivePlayer.ringRight) {
                            Character.Builder playerBuilder = Character.getBuilder().copyCharacter(fightInstance.player);
                            playerBuilder.setRingRight(ringRight);

                            FightInstance nextInstance = FightInstance.getBuilder().setPlayer(playerBuilder.build()).setEnemy(fightInstance.enemy).build();

                            if (!seenInstances.contains(nextInstance.toString())) {
                                instancesToCheck.add(nextInstance);
                            }
                        }
                    }

                    FightInstance nullInstance = FightInstance.getBuilder()
                            .setPlayer(Character.getBuilder().copyCharacter(fightInstance.player).setRingRight(null).build())
                            .setEnemy(fightInstance.enemy).build();

                    if (!seenInstances.contains(nullInstance.toString())) {
                        instancesToCheck.add(nullInstance);
                    }
                }
            }

            seenInstances.add(fightInstance.toString());
        }

        return -1;
    }
}

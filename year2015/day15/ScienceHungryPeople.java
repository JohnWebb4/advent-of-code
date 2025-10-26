/* Licensed under Apache-2.0 */
package advent.year2015.day15;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScienceHungryPeople {
    static final Pattern ingredientPattern = Pattern.compile("^(\\w+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)$");

    public static class Ingredient {
        public final int calories;
        public final int capacity;
        public final int durability;
        public final int flavor;
        public final int texture;

        public Ingredient(int calories, int capacity, int durability, int flavor, int texture) {
            this.calories = calories;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
        }
    }

    public static int getMaximumCookieScore(String input, int quantity) {
        String[] inputLines = input.split("\n");
        Ingredient[] ingredients = new Ingredient[inputLines.length];

        for (int i = 0; i < inputLines.length; i++) {
            Matcher lineMatches = ingredientPattern.matcher(inputLines[i]);

            lineMatches.find();
            String name = lineMatches.group(1);
            int capacity = Integer.parseInt(lineMatches.group(2));
            int durability = Integer.parseInt(lineMatches.group(3));
            int flavor = Integer.parseInt(lineMatches.group(4));
            int texture = Integer.parseInt(lineMatches.group(5));
            int calories = Integer.parseInt(lineMatches.group(6));

            ingredients[i] = new Ingredient(calories, capacity, durability, flavor, texture);
        }

        Stack<String> toCheck = new Stack<>();
        Set<String> hashChecked = new HashSet<>();
        int[] initialCheck = new int[ingredients.length];
        toCheck.add(String.join(",", Arrays.stream(initialCheck).mapToObj((i) -> Integer.toString(i)).toArray(String[]::new)));

        int maxValue = 0;
        while (toCheck.size() > 0) {
            String toCheckKey = toCheck.pop();
            int[] ingredientCounts = Arrays.stream(toCheckKey.split(",")).mapToInt(Integer::parseInt).toArray();

            if (hashChecked.contains(toCheckKey)) {
                continue;
            }

            int capacity = 0;
            int durability = 0;
            int flavor = 0;
            int texture = 0;
            int totalIngredientCount = 0;
            for (int i = 0; i < ingredientCounts.length; i++) {
                Ingredient ingredient = ingredients[i];
                int ingredientCount = ingredientCounts[i];
                totalIngredientCount += ingredientCount;

                capacity += ingredientCount * ingredient.capacity;
                durability += ingredientCount * ingredient.durability;
                flavor += ingredientCount * ingredient.flavor;
                texture += ingredientCount * ingredient.texture;
            }


            capacity = Math.max(capacity, 0);
            durability = Math.max(durability, 0);
            flavor = Math.max(flavor, 0);
            texture = Math.max(texture, 0);

            int value = capacity * durability * flavor * texture;

            if (value > maxValue) {
                maxValue = value;
            }

            if (totalIngredientCount < quantity) {
                for (int i = 0; i < ingredientCounts.length; i++) {
                    int[] nextIngredientCounts = new int[ingredientCounts.length];
                    System.arraycopy(ingredientCounts, 0, nextIngredientCounts, 0, ingredientCounts.length);

                    nextIngredientCounts[i]++;

                    toCheck.add(String.join(",", Arrays.stream(nextIngredientCounts).mapToObj(Integer::toString).toArray(String[]::new)));
                }
            }

            hashChecked.add(toCheckKey);
        }

        return maxValue;
    }

    public static int getMaximumCookieScoreCalories(String input, int quantity, int desiredCalories) {
        String[] inputLines = input.split("\n");
        Ingredient[] ingredients = new Ingredient[inputLines.length];

        for (int i = 0; i < inputLines.length; i++) {
            Matcher lineMatches = ingredientPattern.matcher(inputLines[i]);

            lineMatches.find();
            String name = lineMatches.group(1);
            int capacity = Integer.parseInt(lineMatches.group(2));
            int durability = Integer.parseInt(lineMatches.group(3));
            int flavor = Integer.parseInt(lineMatches.group(4));
            int texture = Integer.parseInt(lineMatches.group(5));
            int calories = Integer.parseInt(lineMatches.group(6));

            ingredients[i] = new Ingredient(calories, capacity, durability, flavor, texture);
        }

        Stack<String> toCheck = new Stack<>();
        Set<String> hashChecked = new HashSet<>();
        int[] initialCheck = new int[ingredients.length];
        toCheck.add(String.join(",", Arrays.stream(initialCheck).mapToObj((i) -> Integer.toString(i)).toArray(String[]::new)));

        int maxValue = 0;
        while (toCheck.size() > 0) {
            String toCheckKey = toCheck.pop();
            int[] ingredientCounts = Arrays.stream(toCheckKey.split(",")).mapToInt(Integer::parseInt).toArray();

            if (hashChecked.contains(toCheckKey)) {
                continue;
            }

            int capacity = 0;
            int durability = 0;
            int flavor = 0;
            int texture = 0;
            int totalIngredientCount = 0;
            int calories = 0;
            for (int i = 0; i < ingredientCounts.length; i++) {
                Ingredient ingredient = ingredients[i];
                int ingredientCount = ingredientCounts[i];
                totalIngredientCount += ingredientCount;

                capacity += ingredientCount * ingredient.capacity;
                durability += ingredientCount * ingredient.durability;
                flavor += ingredientCount * ingredient.flavor;
                texture += ingredientCount * ingredient.texture;
                calories += ingredientCount * ingredient.calories;
            }


            capacity = Math.max(capacity, 0);
            durability = Math.max(durability, 0);
            flavor = Math.max(flavor, 0);
            texture = Math.max(texture, 0);

            int value = capacity * durability * flavor * texture;

            if (value > maxValue && calories == desiredCalories) {
                maxValue = value;
            }

            if (totalIngredientCount < quantity) {
                for (int i = 0; i < ingredientCounts.length; i++) {
                    int[] nextIngredientCounts = new int[ingredientCounts.length];
                    System.arraycopy(ingredientCounts, 0, nextIngredientCounts, 0, ingredientCounts.length);

                    nextIngredientCounts[i]++;

                    toCheck.add(String.join(",", Arrays.stream(nextIngredientCounts).mapToObj(Integer::toString).toArray(String[]::new)));
                }
            }

            hashChecked.add(toCheckKey);
        }

        return maxValue;
    }
}

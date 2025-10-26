/* Licensed under Apache-2.0 */
package advent.year2020.day21;

import java.util.*;

public class AllergenAssessment {
	public static int countIngredientsNoAllergens(String input) {
		String[] rules = input.split("\n");
		Map<String, Set<String>> allergenMap = new HashMap<>();
		Map<String, Integer> ingredientCount = new HashMap<>();
		Set<String> ingredientSet = new HashSet<>();
		Set<String> allergenSet = new HashSet<>();

		for (String rule : rules) {
			String[] ingredients = rule.split("\\(")[0].trim().split(" ");
			String allergenString = rule.split("contains")[1].trim();
			String[] allergens = allergenString.substring(0, allergenString.length() - 1).split(", ");

			ingredientSet.addAll(Arrays.asList(ingredients));
			allergenSet.addAll(Arrays.asList(allergens));

			for (String ingredient : ingredients) {
				if (ingredientCount.containsKey(ingredient)) {
					int count = ingredientCount.get(ingredient);

					ingredientCount.put(ingredient, count + 1);
				} else {
					ingredientCount.put(ingredient, 1);
				}
			}

			for (String allergen : allergens) {
				Set<String> ruleAllergenSet = new HashSet<>();

				if (allergenMap.containsKey(allergen)) {
					Set<String> previousAllergenSet = allergenMap.get(allergen);

					for (String ingredient : ingredients) {
						if (previousAllergenSet.contains(ingredient)) {
							ruleAllergenSet.add(ingredient);
						}
					}
				} else {
					ruleAllergenSet.addAll(Arrays.asList(ingredients));
				}

				allergenMap.put(allergen, ruleAllergenSet);
			}
		}

		// Reduce trivial matches
		boolean didReduce;
		do {
			didReduce = false;
			for (String allergen : allergenMap.keySet()) {
				if (allergenMap.get(allergen).size() == 1) {
					String allergenIngredient = allergenMap.get(allergen).toArray(String[]::new)[0];

					// This has to be a match, remove from other lists
					for (String otherAllergen : allergenMap.keySet()) {
						if (!allergen.equals(otherAllergen)) {
							if (allergenMap.get(otherAllergen).contains(allergenIngredient)) {
								allergenMap.get(otherAllergen).remove(allergenIngredient);
								didReduce = true;
							}
						}
					}

				}
			}
		} while (didReduce);

		Stack<String[]> toCheck = new Stack<>();
		toCheck.add(new String[]{});
		String[] allergens = allergenSet.toArray(String[]::new);

		String[] allergenIngredients = null;
		while (toCheck.size() > 0) {
			if (allergenIngredients != null) {
				break;
			}

			String[] currentAllergenIngredients = toCheck.pop();
			String nextAllergen = allergens[currentAllergenIngredients.length];
			Set<String> possibleAllergenIngredients = allergenMap.get(nextAllergen);

			for (String possibleAllergenIngredient : possibleAllergenIngredients) {
				List<String> nextAllergenList = new LinkedList<>();
				nextAllergenList.addAll(Arrays.asList(currentAllergenIngredients));
				nextAllergenList.add(possibleAllergenIngredient);

				String[] nextAllergensIngredients = nextAllergenList.toArray(String[]::new);

				if (nextAllergensIngredients.length == allergens.length) {
					allergenIngredients = nextAllergensIngredients;
					break;
				} else {
					toCheck.add(nextAllergensIngredients);
				}
			}
		}

		if (allergenIngredients == null) {
			return 0;
		}

		Set<String> allergenIngredientSet = new HashSet<>();
		allergenIngredientSet.addAll(Arrays.asList(allergenIngredients));
		String[] nonAllergenIngredients = ingredientSet.stream().filter((s) -> !allergenIngredientSet.contains(s)).toArray(String[]::new);

		int count = 0;
		for (String nonAllergenIngredient : nonAllergenIngredients) {
			count += ingredientCount.get(nonAllergenIngredient);
		}

		return count;
	}

	public static String getAllergenIngredientPairs(String input) {
		String[] rules = input.split("\n");
		Map<String, Set<String>> allergenMap = new HashMap<>();
		Map<String, Integer> ingredientCount = new HashMap<>();
		Set<String> ingredientSet = new HashSet<>();
		Set<String> allergenSet = new HashSet<>();

		for (String rule : rules) {
			String[] ingredients = rule.split("\\(")[0].trim().split(" ");
			String allergenString = rule.split("contains")[1].trim();
			String[] allergens = allergenString.substring(0, allergenString.length() - 1).split(", ");

			ingredientSet.addAll(Arrays.asList(ingredients));
			allergenSet.addAll(Arrays.asList(allergens));

			for (String ingredient : ingredients) {
				if (ingredientCount.containsKey(ingredient)) {
					int count = ingredientCount.get(ingredient);

					ingredientCount.put(ingredient, count + 1);
				} else {
					ingredientCount.put(ingredient, 1);
				}
			}

			for (String allergen : allergens) {
				Set<String> ruleAllergenSet = new HashSet<>();

				if (allergenMap.containsKey(allergen)) {
					Set<String> previousAllergenSet = allergenMap.get(allergen);

					for (String ingredient : ingredients) {
						if (previousAllergenSet.contains(ingredient)) {
							ruleAllergenSet.add(ingredient);
						}
					}
				} else {
					ruleAllergenSet.addAll(Arrays.asList(ingredients));
				}

				allergenMap.put(allergen, ruleAllergenSet);
			}
		}

		// Reduce trivial matches
		boolean didReduce;
		do {
			didReduce = false;
			for (String allergen : allergenMap.keySet()) {
				if (allergenMap.get(allergen).size() == 1) {
					String allergenIngredient = allergenMap.get(allergen).toArray(String[]::new)[0];

					// This has to be a match, remove from other lists
					for (String otherAllergen : allergenMap.keySet()) {
						if (!allergen.equals(otherAllergen)) {
							if (allergenMap.get(otherAllergen).contains(allergenIngredient)) {
								allergenMap.get(otherAllergen).remove(allergenIngredient);
								didReduce = true;
							}
						}
					}

				}
			}
		} while (didReduce);

		Stack<String[]> toCheck = new Stack<>();
		toCheck.add(new String[]{});
		String[] allergens = allergenSet.stream().sorted(String::compareTo).toArray(String[]::new);

		String[] allergenIngredients = null;
		while (toCheck.size() > 0) {
			if (allergenIngredients != null) {
				break;
			}

			String[] currentAllergenIngredients = toCheck.pop();
			String nextAllergen = allergens[currentAllergenIngredients.length];
			Set<String> possibleAllergenIngredients = allergenMap.get(nextAllergen);

			for (String possibleAllergenIngredient : possibleAllergenIngredients) {
				List<String> nextAllergenList = new LinkedList<>();
				nextAllergenList.addAll(Arrays.asList(currentAllergenIngredients));
				nextAllergenList.add(possibleAllergenIngredient);

				String[] nextAllergensIngredients = nextAllergenList.toArray(String[]::new);

				if (nextAllergensIngredients.length == allergens.length) {
					allergenIngredients = nextAllergensIngredients;
					break;
				} else {
					toCheck.add(nextAllergensIngredients);
				}
			}
		}

		if (allergenIngredients == null) {
			return null;
		}

		return String.join(",", allergenIngredients);
	}
}

/* Licensed under Apache-2.0 */
package advent.year2019.day14;

import java.util.Arrays;
import java.util.HashMap;

public class Reaction {
  public static class ReactionIngredient implements Cloneable {
    String molecule;
    long quantity;

    public ReactionIngredient(String molecule, long quantity) {
      this.molecule = molecule;
      this.quantity = quantity;
    }

    public ReactionIngredient(String ingredientString) {
      String[] ingredientParts = ingredientString.split(" ");
      quantity = Long.parseLong(ingredientParts[0]);
      molecule = ingredientParts[1];
    }

    public ReactionIngredient scaleIngredient(long scale) {
      try {
        ReactionIngredient scaledIngredient = (ReactionIngredient) this.clone();

        scaledIngredient.quantity *= scale;

        return scaledIngredient;
      } catch (CloneNotSupportedException e) {
        System.err.println("Cannot clone ingredient");
      }
      return null;
    }
  }

  HashMap<String, ReactionIngredient> inputs;
  ReactionIngredient output;

  protected Reaction(HashMap<String, ReactionIngredient> inputs, ReactionIngredient output) {
    this.inputs = inputs;
    this.output = output;
  }

  public Reaction(String reactionString) {
    String[] reactionParts = reactionString.split(" => ");
    inputs = new HashMap();
    ReactionIngredient[] inputIngredients =
        Arrays.stream(reactionParts[0].split(", "))
            .map((ingredientString) -> new ReactionIngredient(ingredientString))
            .toArray(ReactionIngredient[]::new);

    for (ReactionIngredient ingredient : inputIngredients) {
      inputs.put(ingredient.molecule, ingredient);
    }

    output = new ReactionIngredient(reactionParts[1].split(", ")[0]);
  }

  public Reaction scaleReaction(long scale) {
    HashMap<String, ReactionIngredient> scaledInputs = new HashMap<>();

    for (String key : this.inputs.keySet()) {
      ReactionIngredient scaledInput =
          (ReactionIngredient) this.inputs.get(key).scaleIngredient(scale);

      scaledInputs.put(key, scaledInput);
    }

    ReactionIngredient scaledOutput = this.output.scaleIngredient(scale);

    return new Reaction(scaledInputs, scaledOutput);
  }
}

/* Licensed under Apache-2.0 */
package advent.year2019.day14;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// Assuming only one reaction has FUEL as output
// Assuming all reactions has one output
// All reactions can be reduced down to ORE
// No cyclic reactions A => A
public class StoichiometryCalculator {
  public static final String ORE_MOLECULE = "ORE";
  public static final String FUEL_MOLECULE = "FUEL";

  public long calculateOreToMakeFuel(String reactionInput) {
    List<Reaction> reactionList = getReactions(reactionInput);

    int fuelReactionIndex = getFuelReactionIndex(reactionList);

    Reaction fuelReaction = reactionList.get(fuelReactionIndex);

    reactionList.remove(fuelReactionIndex);

    Reaction[] reactions = new Reaction[reactionList.size()];
    reactionList.toArray(reactions);

    long totalOre = calculateTotalOreToMakeFuel(reactions, fuelReaction);
    return (int) (totalOre / fuelReaction.output.quantity);
  }

  public long calculateTotalOreToMakeFuel(Reaction[] reactions, Reaction fuelReaction) {
    boolean hasReduction;
    do {
      hasReduction = false;
      for (int reactionIndex = 0; reactionIndex < reactions.length; reactionIndex++) {
        Reaction reaction = reactions[reactionIndex];
        Reaction.ReactionIngredient fuelInputIngredient =
            fuelReaction.inputs.get(reaction.output.molecule);

        if (fuelInputIngredient != null
            && fuelInputIngredient.quantity > 0
            && !reaction.output.molecule.equals(ORE_MOLECULE)) {
          Iterator reactionInputIterator = reaction.inputs.values().iterator();
          long conversion =
              (long) Math.ceil(((float) fuelInputIngredient.quantity) / reaction.output.quantity);

          while (reactionInputIterator.hasNext()) {
            Reaction.ReactionIngredient inputIngredient =
                (Reaction.ReactionIngredient) reactionInputIterator.next();

            if (fuelReaction.inputs.get(inputIngredient.molecule) != null) {
              fuelReaction.inputs.get(inputIngredient.molecule).quantity +=
                  inputIngredient.quantity * conversion;
            } else {
              fuelReaction.inputs.put(
                  inputIngredient.molecule,
                  new Reaction.ReactionIngredient(
                      inputIngredient.molecule, inputIngredient.quantity * conversion));
            }
          }

          fuelInputIngredient.quantity -= reaction.output.quantity * conversion;
          hasReduction = true;
        }
      }
    } while (hasReduction);

    return fuelReaction.inputs.get(ORE_MOLECULE).quantity;
  }

  public long calculateFuelAtScale(String reactionInput, long oreMax) {
    List<Reaction> reactionList = getReactions(reactionInput);
    int fuelReactionIndex = getFuelReactionIndex(reactionList);

    Reaction fuelReaction = reactionList.get(fuelReactionIndex);

    reactionList.remove(fuelReactionIndex);

    Reaction[] reactions = new Reaction[reactionList.size()];
    reactionList.toArray(reactions);

    return calculateFuelAtScale(reactions, fuelReaction, oreMax);
  }

  public long calculateFuelAtScale(Reaction[] reactions, Reaction fuelReaction, long oreMax) {
    // Bisection to find optimal fuel
    long minScale = 1;
    long maxScale = oreMax;
    long scale = (maxScale - minScale) / 2 + minScale;

    do {
      Reaction scaledFuelReaction = fuelReaction.scaleReaction(scale);

      long oreCount = calculateTotalOreToMakeFuel(reactions, scaledFuelReaction);

      if (oreCount > oreMax) {
        maxScale = scale;
      } else if (oreCount < oreMax && maxScale - minScale == 1) {
        // This is the last iteration. Get max scale and see if it goes over
        Reaction maxScaleFuelReaction = fuelReaction.scaleReaction(maxScale);
        long maxScaleOreCount = calculateTotalOreToMakeFuel(reactions, maxScaleFuelReaction);

        if (maxScaleOreCount >= oreMax) {
          maxScale = minScale;
        } else {
          minScale = maxScale;
        }
      } else {
        minScale = scale;
      }

      scale = (maxScale - minScale) / 2 + minScale;
    } while (maxScale > minScale);

    Reaction scaledFuelReaction = fuelReaction.scaleReaction(scale);

    return scaledFuelReaction.output.quantity;
  }

  public List<Reaction> getReactions(String reactionInput) {
    return Arrays.stream(reactionInput.split("\n"))
        .map((reactionString) -> new Reaction(reactionString))
        .collect(Collectors.toList());
  }

  public int getFuelReactionIndex(List<Reaction> reactionList) {
    int fuelReactionIndex = 0;

    for (int i = 0; i < reactionList.size(); i++) {
      if (reactionList.get(i).output.molecule.equals(FUEL_MOLECULE)) {
        fuelReactionIndex = i;
        break;
      }
    }

    return fuelReactionIndex;
  }
}

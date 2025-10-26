/* Licensed under Apache-2.0 */
package advent.year2015.day19;

import java.util.*;

public class MedicineRudolph {
    public static class FabricationState implements Comparable {
        String state;
        int stepsToState;

        public static class Builder {
            String state;
            int stepsToState;

            public Builder setState(String state) {
                this.state = state;
                return this;
            }

            public Builder setStepsToState(int stepsToState) {
                this.stepsToState = stepsToState;
                return this;
            }

            public FabricationState build() {
                return new FabricationState(this.state, this.stepsToState);
            }
        }

        public FabricationState(String state, int stepsToState) {
            this.state = state;
            this.stepsToState = stepsToState;
        }

        public static Builder getBuilder() {
            return new Builder();
        }

        public String getState() {
            return this.state;
        }

        public int getStepsToState() {
            return this.stepsToState;
        }

        public int getScore() {
            return this.state.length();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FabricationState) {
                FabricationState fabricationState = (FabricationState) obj;

                return this.getState().equals(fabricationState.getState());
            }

            return super.equals(obj);
        }

        @Override
        public int compareTo(Object obj) {
            if (obj instanceof FabricationState) {
                FabricationState fabricationState = (FabricationState) obj;

                return Integer.compare(this.getScore(), fabricationState.getScore());
            }

            return 0;
        }
    }

    public static int getDistinctMoleculeCount(String replacementString, String medicine) {
        String[] replacementStrings = replacementString.split("\n");
        Map<String, List<String>> replacementMap = new HashMap<>();

        int maxModifiedPartLength = 0;
        for (String replacement : replacementStrings) {
            String[] replacementParts = replacement.split("=>");
            String originalPart = replacementParts[0].trim();
            String modifiedPart = replacementParts[1].trim();

            maxModifiedPartLength = Math.max(maxModifiedPartLength, modifiedPart.length());

            if (replacementMap.containsKey(originalPart)) {
                replacementMap.get(originalPart).add(modifiedPart);
            } else {
                List<String> modifiedPartList = new LinkedList<>();
                modifiedPartList.add(modifiedPart);

                replacementMap.put(originalPart, modifiedPartList);
            }
        }

        Set<String> replacementSet = new HashSet<>();

        for (int i = 0; i < medicine.length(); i++) {
            for (int j = 1; j <= maxModifiedPartLength && i + j <= medicine.length(); j++) {
                String element = medicine.substring(i, i + j);

                if (replacementMap.containsKey(element)) {
                    for (String replacement : replacementMap.get(element)) {
                        String newMedicine = medicine.substring(0, i) + replacement + medicine.substring(i + j);

                        replacementSet.add(newMedicine);
                    }
                }
            }

        }

        return replacementSet.size();
    }

    public static int getFewestStepsToFabricate(String replacementString, String medicine) {
        String[] replacementStrings = replacementString.split("\n");
        Map<String, List<String>> reverseReplacementMap = new HashMap<>();

        int maxModifiedPartLength = 0;
        for (String replacement : replacementStrings) {
            String[] replacementParts = replacement.split("=>");
            String originalPart = replacementParts[0].trim();
            String modifiedPart = replacementParts[1].trim();

            maxModifiedPartLength = Math.max(maxModifiedPartLength, modifiedPart.length());

            if (reverseReplacementMap.containsKey(modifiedPart)) {
                reverseReplacementMap.get(modifiedPart).add(originalPart);
            } else {
                List<String> originalPartList = new LinkedList<>();
                originalPartList.add(originalPart);

                reverseReplacementMap.put(modifiedPart, originalPartList);
            }
        }

        int minStepsToFabricate = Integer.MAX_VALUE;

        PriorityQueue<FabricationState> fabricationStatesToCheck = new PriorityQueue<>();
        Map<String, Integer> seenStatesStepMap = new HashMap<>();
        fabricationStatesToCheck.add(FabricationState.getBuilder().setState(medicine).setStepsToState(0).build());

        while (fabricationStatesToCheck.size() > 0) {
            FabricationState fabricationState = fabricationStatesToCheck.poll();
            String state = fabricationState.getState();
            int stepsToState = fabricationState.getStepsToState();

            if (stepsToState + 1 < minStepsToFabricate) {
                if (seenStatesStepMap.containsKey(state) && stepsToState >= seenStatesStepMap.get(state)) {
                    continue;
                }

                for (int i = 0; i < state.length(); i++) {
                    for (int j = 1; j <= maxModifiedPartLength && i + j <= state.length(); j++) {
                        String element = state.substring(i, i + j);

                        if (reverseReplacementMap.containsKey(element)) {
                            for (String replacement : reverseReplacementMap.get(element)) {
                                String newState = state.substring(0, i) + replacement + state.substring(i + j);
                                FabricationState newFabricationState = FabricationState.getBuilder().setState(newState).setStepsToState(stepsToState + 1).build();

                                if (newState.equals("e")) {
                                    minStepsToFabricate = Math.min(minStepsToFabricate, newFabricationState.getStepsToState());

                                    return minStepsToFabricate;
                                } else if (newFabricationState.getScore() <= fabricationState.getScore()) {
                                    // Assumption: It will always expand from start to end. Reverse is true. Always reduces from end to start
                                    if (seenStatesStepMap.containsKey(newState)) {
                                        if (stepsToState + 1 < seenStatesStepMap.get(newState)) {
                                            // Can get to same state in fewer moves
                                            fabricationStatesToCheck.add(newFabricationState);
                                        }
                                    } else {
                                        fabricationStatesToCheck.add(newFabricationState);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (seenStatesStepMap.containsKey(state)) {
                if (stepsToState < seenStatesStepMap.get(state)) {
                    seenStatesStepMap.put(state, stepsToState);
                }
            } else {
                seenStatesStepMap.put(state, stepsToState);
            }
        }

        return minStepsToFabricate;
    }
}

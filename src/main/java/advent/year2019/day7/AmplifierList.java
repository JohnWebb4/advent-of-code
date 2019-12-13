/* Licensed under Apache-2.0 */
package advent.year2019.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AmplifierList {
  public static long runAmplifiers(AmplifierListInput input) {
    ArrayList<Amplifier> amplifiers = new ArrayList<>();

    for (long phase : input.phases) {
      amplifiers.add(new Amplifier(input.codes, phase));
    }

    long signal = 0;

    for (Amplifier amplifier : amplifiers) {
      signal = amplifier.nextElement(signal);
    }

    return signal;
  }

  public static long runAmplifiersWithFeedback(AmplifierListInput input) {
    ArrayList<Amplifier> amplifiers = new ArrayList<>();

    for (long phase : input.phases) {
      amplifiers.add(new Amplifier(input.codes, phase));
    }

    long signal = 0;

    while (true) {
      for (int amplifierIndex = 0; amplifierIndex < amplifiers.size(); amplifierIndex++) {
        if (amplifiers.get(amplifierIndex).hasMoreElements()) {
          signal = amplifiers.get(amplifierIndex).nextElement(signal);
        }
      }

      if (!amplifiers.get(amplifiers.size() - 1).hasMoreElements()) {
        break;
      }
    }

    return signal;
  }

  public static long findMaxThruster(long[] codes, long[] possiblePhases, Function<AmplifierListInput, Long> amplifierFunction) {
    long maxThruster = 0;

    if (possiblePhases.length == 0) {
      return amplifierFunction.apply(new AmplifierListInput(codes, new long[] {}, 0));
    }

    ArrayList<Long> possiblePhaseList = Arrays.stream(possiblePhases).boxed().collect(Collectors.toCollection(ArrayList::new));

    for (int phaseIndex = 0; phaseIndex < possiblePhaseList.size(); phaseIndex++) {
      long phase = possiblePhaseList.remove(phaseIndex);

      Function<AmplifierListInput, Long> partialAmp = new Function<AmplifierListInput, Long>() {
        @Override
        public Long apply(AmplifierListInput input) {
          long[] phases = new long[input.phases.length + 1];
          phases[0] = phase;

          if (input.phases.length > 0) {
            System.arraycopy(input.phases, 0, phases, 1, phases.length - 1);
          }

          return amplifierFunction.apply(new AmplifierListInput(input.codes, phases, input.inputSignal));
        }
      };

      long thruster = findMaxThruster(codes, possiblePhaseList.stream().mapToLong(i -> i).toArray(), partialAmp);

      if (thruster > maxThruster) {
        maxThruster = thruster;
      }

      possiblePhaseList.add(phaseIndex, phase);
    }


    return maxThruster;
  }
}

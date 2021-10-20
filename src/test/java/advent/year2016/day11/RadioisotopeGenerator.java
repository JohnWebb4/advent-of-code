package advent.year2016.day11;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RadioisotopeGenerator {
    public static final Pattern INITIAL_FLOOR_PATTERN = Pattern.compile("The (\\w+) floor contains a ([\\w-]+) (generator|microchip)(?:, a ([\\w-]+) (generator|microchip))?(?:, a ([\\w-]+) (generator|microchip))?(?:,? and a ([\\w-]+) (generator|microchip))?.");

    public static class Microchip {
        public final String element;
        public final int floor;

        public Microchip(String element, int floor) {
            this.element = element;
            this.floor = floor;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Microchip) {
                Microchip microchip = (Microchip) obj;

                return this.floor == microchip.floor && Objects.equals(this.element, microchip.element);
            }

            return super.equals(obj);
        }

        @Override
        public String toString() {
            return this.element + this.floor;
        }
    }

    public static class Generator {
        public final String element;
        public final int floor;

        public Generator(String element, int floor) {
            this.element = element;
            this.floor = floor;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Generator) {
                Generator generator = (Generator) obj;

                return this.floor == generator.floor && Objects.equals(this.element, generator.element);
            }

            return super.equals(obj);
        }

        @Override
        public String toString() {
            return this.element + this.floor;
        }
    }

    public static class FactoryState implements Comparable<FactoryState> {
        public final Microchip[] microchips;
        public final Generator[] generators;
        public final int steps;
        public final String path;

        public FactoryState(int steps, Microchip[] microchips, Generator[] generators, String path) {
            this.steps = steps;
            this.microchips = microchips;
            this.generators = generators;
            this.path = path;
        }

        public boolean isValid() {
            boolean isValid = true;

            for (Microchip microchip : this.microchips) {
                boolean hasSameGenerator = false;
                boolean otherGeneratorSameFloor = false;

                for (Generator generator : this.generators) {
                    if (microchip.floor == generator.floor) {
                        otherGeneratorSameFloor = true;

                        if (microchip.element.equals(generator.element)) {
                            hasSameGenerator = true;
                        }
                    }
                }

                if (!hasSameGenerator && otherGeneratorSameFloor) {
                    isValid = false;
                    break;
                }
            }

            return isValid;
        }

        public boolean canAssemble(int targetFloor) {
            for (Microchip microchip : microchips) {
                if (microchip.floor != targetFloor) {
                    return false;
                }
            }

            for (Generator generator : generators) {
                if (generator.floor != targetFloor) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public int compareTo(FactoryState o) {
            return this.steps - o.steps;
        }

        @Override
        public String toString() {
            String generatorString = String.join(",", Arrays.stream(this.generators).map(Object::toString).toArray(String[]::new));
            String microchipString = String.join(",", Arrays.stream(this.microchips).map(Object::toString).toArray(String[]::new));

            return String.format("%s;%s;%s", this.steps, generatorString, microchipString);
        }
    }

    public static int getMinStepsToMoveChips(String initialState, int elevatorMaxSize) {
        String[] initialFloorStates = initialState.split("\n");
        List<Microchip> microchipList = new LinkedList<>();
        List<Generator> generatorList = new LinkedList<>();

        for (String initialFloorState : initialFloorStates) {
            Matcher initialFloorMatch = INITIAL_FLOOR_PATTERN.matcher(initialFloorState);
            if (initialFloorMatch.find()) {
                try {
                    String floorString = initialFloorMatch.group(1);
                    int floorNumber = getFloorNumber(floorString);

                    String element1 = initialFloorMatch.group(2);
                    String type1 = initialFloorMatch.group(3);

                    if (element1 != null) {
                        String element = element1.split("-")[0];

                        switch (type1) {
                            case "microchip":
                                microchipList.add(new Microchip(element, floorNumber));
                                break;
                            case "generator":
                                generatorList.add(new Generator(element, floorNumber));
                                break;
                            default:
                                break;
                        }
                    }

                    String element2 = initialFloorMatch.group(4);
                    String type2 = initialFloorMatch.group(5);

                    if (element2 != null) {
                        String element = element2.split("-")[0];

                        switch (type2) {
                            case "microchip":
                                microchipList.add(new Microchip(element, floorNumber));
                                break;
                            case "generator":
                                generatorList.add(new Generator(element, floorNumber));
                                break;
                            default:
                                break;
                        }
                    }

                    String element3 = initialFloorMatch.group(6);
                    String type3 = initialFloorMatch.group(7);

                    if (element3 != null) {
                        String element = element3.split("-")[0];

                        switch (type3) {
                            case "microchip":
                                microchipList.add(new Microchip(element, floorNumber));
                                break;
                            case "generator":
                                generatorList.add(new Generator(element, floorNumber));
                                break;
                            default:
                                break;
                        }
                    }

                    String element4 = initialFloorMatch.group(8);
                    String type4 = initialFloorMatch.group(9);

                    if (element4 != null) {
                        String element = element4.split("-")[0];

                        switch (type4) {
                            case "microchip":
                                microchipList.add(new Microchip(element, floorNumber));
                                break;
                            case "generator":
                                generatorList.add(new Generator(element, floorNumber));
                                break;
                            default:
                                break;
                        }
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        PriorityQueue<FactoryState> factoryStates = new PriorityQueue<>();
        factoryStates.add(new FactoryState(0, microchipList.toArray(Microchip[]::new), generatorList.toArray(Generator[]::new), ""));
        Set<String> factorySet = new HashSet<>();

        while (factoryStates.size() > 0) {
            FactoryState factoryState = factoryStates.poll();

            if (factoryState.isValid()) {
                if (factoryState.canAssemble(3)) {
                    return factoryState.steps;
                }

                System.out.println("HI");

                for (Microchip microchip : factoryState.microchips) {
                    Generator[] generators = Arrays.copyOf(factoryState.generators, factoryState.generators.length);
                    List<Microchip> microchips = Arrays.stream(factoryState.microchips).filter(Predicate.not(microchip::equals)).collect(Collectors.toList());
                    microchips.add(new Microchip(microchip.element, microchip.floor + 1));

                    FactoryState nextState = new FactoryState(factoryState.steps + 1, microchips.toArray(Microchip[]::new), generators, factoryState.path + "\n" + factoryState);
                    if (!factorySet.contains(nextState.toString()) && nextState.isValid()) {
                        factoryStates.add(nextState);
                        factorySet.add(nextState.toString());
                    }
                }

                for (Generator generator : factoryState.generators) {
                    Microchip[] microchips = Arrays.copyOf(factoryState.microchips, factoryState.microchips.length);
                    List<Generator> generators = Arrays.stream(factoryState.generators).filter(Predicate.not(generator::equals)).collect(Collectors.toList());
                    generators.add(new Generator(generator.element, generator.floor + 1));

                    FactoryState nextState = new FactoryState(factoryState.steps + 1, microchips, generators.toArray(Generator[]::new), factoryState.path + "\n" + factoryState);

                    if (!factorySet.contains(nextState.toString()) && nextState.isValid()) {
                        factoryStates.add(nextState);
                        factorySet.add(nextState.toString());
                    }
                }
            }

            factorySet.add(factoryState.toString());
        }

        return 0;
    }

    public static int getFloorNumber(String floorName) {
        switch (floorName) {
            case "first":
                return 0;
            case "second":
                return 1;
            case "third":
                return 2;
            case "fourth":
                return 3;
            default:
                return -1;
        }
    }
}

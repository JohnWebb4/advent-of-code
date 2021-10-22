package advent.year2016.day11;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RadioisotopeGenerator {
    public static final Pattern INITIAL_FLOOR_PATTERN = Pattern.compile("The (\\w+) floor contains a ([\\w-]+) (generator|microchip)(?:, a ([\\w-]+) (generator|microchip))?(?:, a ([\\w-]+) (generator|microchip))?(?:,? and a ([\\w-]+) (generator|microchip))?.");
    public static final int ASSEMBLY_FLOOR_NUM = 3;

    public static class Item {
        public enum ItemType {
            GENERATOR,
            MICROCHIP,
        }

        public final ItemType itemType;
        public final String element;
        public final int floor;

        public Item(ItemType itemType, String element, int floor) {
            this.itemType = itemType;
            this.element = element;
            this.floor = floor;
        }

        @Override
        public String toString() {
            return "" + this.itemType + this.element + this.floor;
        }
    }

    public static class FactoryState implements Comparable<FactoryState> {
        public final int elevatorFloor;
        public final Item[][] floorItems;
        public final int steps;
        public final String path;

        public FactoryState(int steps, int elevatorFloor, Item[][] floorItems, String path) {
            this.steps = steps;
            this.elevatorFloor = elevatorFloor;
            this.path = path;
            this.floorItems = floorItems;
        }

        public static Item[][] getFloorItems(Item[] items, int numFloors) {
            Map<Integer, List<Item>> mapFloorItems = new HashMap<>();

            for (Item item : items) {
                mapFloorItems.putIfAbsent(item.floor, new LinkedList<>());

                mapFloorItems.get(item.floor).add(item);
            }

            Item[][] floorItems = new Item[numFloors][];
            for (int i = 0; i < numFloors; i++) {
                floorItems[i] = mapFloorItems.getOrDefault(i, new LinkedList<>()).toArray(Item[]::new);
            }

            return floorItems;
        }

        public boolean isValid() {
            for (Item[] floor : this.floorItems) {
                if (floor != null) {
                    Set<String> generators = new HashSet<>();
                    Set<String> microchips = new HashSet<>();

                    for (Item item : floor) {
                        switch (item.itemType) {
                            case GENERATOR:
                                generators.add(item.element);
                                break;
                            case MICROCHIP:
                                microchips.add(item.element);
                                break;
                            default:
                                break;
                        }
                    }

                    // Get unprotected microchips
                    microchips.removeAll(generators);

                    if (microchips.size() > 0 && generators.size() > 0) {
                        return false;
                    }
                }
            }

            return true;
        }

        public boolean canAssemble(int targetFloor) {
            for (int i = 0; i < this.floorItems.length - 1; i++) {
                if (this.floorItems[i].length != 0) {
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
            String itemString = String.join(",",
                    Arrays.stream(this.floorItems).map((floor) ->
                                    String.join(",", Arrays.stream(floor).map(Object::toString).toArray(String[]::new)))
                            .toArray(String[]::new));

            return String.format("step%s;elev%s;items%s", this.steps, this.elevatorFloor, itemString);
        }
    }

    public static int getMinStepsToMoveChips(String initialState, int elevatorMaxSize) {
        String[] initialFloorStates = initialState.split("\n");
        List<Item> itemList = new LinkedList<>();

        for (String initialFloorState : initialFloorStates) {
            Matcher initialFloorMatch = INITIAL_FLOOR_PATTERN.matcher(initialFloorState);
            if (initialFloorMatch.find()) {
                try {
                    String floorString = initialFloorMatch.group(1);
                    int floorNumber = getFloorNumber(floorString);

                    String[][] itemMatches = new String[][]{
                            new String[]{initialFloorMatch.group(2), initialFloorMatch.group(3)},
                            new String[]{initialFloorMatch.group(4), initialFloorMatch.group(5)},
                            new String[]{initialFloorMatch.group(6), initialFloorMatch.group(7)},
                            new String[]{initialFloorMatch.group(8), initialFloorMatch.group(9)},
                    };

                    List<Item> mappedItems = Arrays.stream(itemMatches).map((itemMatch) -> {
                        String element = itemMatch[0];
                        String type = itemMatch[1];

                        if (element != null) {
                            element = element.split("-")[0];

                            switch (type) {
                                case "microchip":
                                    return new Item(Item.ItemType.MICROCHIP, element, floorNumber);
                                case "generator":
                                    return new Item(Item.ItemType.GENERATOR, element, floorNumber);
                                default:
                                    break;
                            }
                        }
                        return null;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

                    itemList.addAll(mappedItems);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        PriorityQueue<FactoryState> factoryStates = new PriorityQueue<>();
        factoryStates.add(new FactoryState(0, 0, FactoryState.getFloorItems(itemList.toArray(Item[]::new), ASSEMBLY_FLOOR_NUM + 1), ""));
        Set<String> factorySet = new HashSet<>();

        while (factoryStates.size() > 0) {
            FactoryState factoryState = factoryStates.poll();

            if (factoryState.steps > 11) {
                continue;
            }

            if (factoryState.isValid()) {
                if (factoryState.canAssemble(ASSEMBLY_FLOOR_NUM)) {
                    return factoryState.steps;
                }

                Item[] currentFloor = factoryState.floorItems[factoryState.elevatorFloor];

                for (Item item1 : currentFloor) { // O(n)
                    if (item1.floor == factoryState.elevatorFloor) {
                        for (Item item2 : currentFloor) { // O(n^2)
                            if (item2.floor == factoryState.elevatorFloor) {
                                List<Item> trimCurrentFloor = Arrays.stream(currentFloor).filter(Predicate.not(item1::equals)).filter(Predicate.not(item2::equals)).collect(Collectors.toList());

                                if (factoryState.elevatorFloor < ASSEMBLY_FLOOR_NUM) {
                                    List<Item> upItems = new LinkedList<>(Arrays.asList(factoryState.floorItems[factoryState.elevatorFloor + 1]));

                                    upItems.add(new Item(item1.itemType, item1.element, item1.floor + 1));
                                    if (item2 != item1) {
                                        upItems.add(new Item(item2.itemType, item2.element, item2.floor + 1));
                                    }
                                    Item[][] upFloorItems = Arrays.copyOf(factoryState.floorItems, factoryState.floorItems.length);
                                    upFloorItems[factoryState.elevatorFloor] = trimCurrentFloor.toArray(Item[]::new);
                                    upFloorItems[factoryState.elevatorFloor + 1] = upItems.toArray(Item[]::new);

                                    FactoryState nextState = new FactoryState(factoryState.steps + 1, factoryState.elevatorFloor + 1, upFloorItems, factoryState.path + factoryState);

                                    if (nextState.isValid() && !factorySet.contains(nextState.toString())) {
                                        factoryStates.add(nextState);
                                        factorySet.add(nextState.toString());
                                    }
                                }

                                if (factoryState.elevatorFloor > 0) {
                                    List<Item> downItems = new LinkedList<>(Arrays.asList(factoryState.floorItems[factoryState.elevatorFloor - 1]));

                                    downItems.add(new Item(item1.itemType, item1.element, item1.floor - 1));
                                    if (item2 != item1) {
                                        downItems.add(new Item(item2.itemType, item2.element, item2.floor - 1));
                                    }
                                    Item[][] downFloorItems = Arrays.copyOf(factoryState.floorItems, factoryState.floorItems.length);
                                    downFloorItems[factoryState.elevatorFloor] = trimCurrentFloor.toArray(Item[]::new);
                                    downFloorItems[factoryState.elevatorFloor - 1] = downItems.toArray(Item[]::new);

                                    FactoryState nextState = new FactoryState(factoryState.steps + 1, factoryState.elevatorFloor - 1, downFloorItems, factoryState.path + factoryState);

                                    if (nextState.isValid() && !factorySet.contains(nextState.toString())) {
                                        factoryStates.add(nextState);
                                        factorySet.add(nextState.toString());
                                    }
                                }
                            }
                        }
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
